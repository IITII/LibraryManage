import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class ReturnBook extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=library;"
                        + "user=IITII;"
                        + "password=Nchu172041;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
        TextField textField = new TextField();
        Button button = new Button("立即还书");
        Label label = new Label("借阅记录ID:");
        Label waring = new Label("");
        GridPane gridPane = new GridPane();
        gridPane.add(label,1,0);
        gridPane.add(textField,1,1);
        gridPane.add(waring,1,2);
        gridPane.add(button,1,3);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(button, HPos.LEFT);
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.setTitle("还书");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(350);
        primaryStage.show();
        button.setOnAction(event -> {
            if (!textField.getText().equals("")) {
                try (Connection connection = DriverManager.getConnection(connectionUrl);
                     CallableStatement callableStatement = connection.prepareCall("{?= call dbo.return_book(?)}");) {
                    //'?' 的作用的作为一个占位符，1，2意为第几个问号。
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    callableStatement.setString(2, textField.getText());
                    callableStatement.execute();
                    switch (callableStatement.getInt(1)) {
                        case 0:waring.setTextFill(Color.GREEN);
                            waring.setText("还书成功！");
                            break;
                        case 1:waring.setTextFill(Color.RED);
                            waring.setText("还书成功，但已超期！");
                            break;
                        case -1:waring.setTextFill(Color.RED);
                            waring.setText("借阅记录不存在！");
                            break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                waring.setTextFill(Color.RED);
                waring.setText("请输入借书记录ID！");
            }

        });

    }
}
