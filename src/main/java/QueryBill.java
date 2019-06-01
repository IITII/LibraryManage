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

public class QueryBill extends Application {
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
        Button button = new Button("点我查询");
        Label label = new Label("请输入要查询的借书证号码：");
        Label waring = new Label("");
        GridPane gridPane = new GridPane();
        gridPane.add(label,1,0);
        gridPane.add(textField,1,1);
        gridPane.add(waring,1,2);
        gridPane.add(button,1,3);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setHalignment(button, HPos.LEFT);
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.setTitle("查看余额");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(350);
        primaryStage.show();
        button.setOnAction(event -> {
            if (!textField.getText().equals("")) {
                try  {
                    Connection connection = DriverManager.getConnection(connectionUrl);
                    Statement statement = connection.createStatement();
                    String select = "select * from card where sno = \'"+textField.getText()+"\';";
                    ResultSet resultSet = statement.executeQuery(select);
                    while (resultSet.next()) {
                        waring.setTextFill(Color.GREEN);
                        //System.out.println(resultSet.getInt(1));
                        System.out.println(textField.getText());
                        waring.setText("当前余额：￥"+resultSet.getInt(5));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                waring.setTextFill(Color.RED);
                waring.setText("请输入要查询的借书证号码！");
            }

        });

    }
}
