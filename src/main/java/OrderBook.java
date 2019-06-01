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

public class OrderBook extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField sno = new TextField();
        TextField bno = new TextField();
        Button button = new Button("借阅");
        Label label_sno = new Label("借书证号码: ");
        Label label_bno = new Label("书籍编号: ");
        Label waring = new Label("");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(label_bno,0,0);
        gridPane.add(label_sno,0,1);
        gridPane.add(waring,1,2);
        gridPane.add(button,1,3);
        gridPane.add(bno,1,0);
        gridPane.add(sno,1,1);
        GridPane.setHalignment(button, HPos.LEFT);
        primaryStage.setScene(new Scene(gridPane));
        primaryStage.setTitle("借书");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(350);
        primaryStage.show();
        //按钮事件
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=library;"
                        + "user=IITII;"
                        + "password=Nchu172041;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        button.setOnAction(event -> {
            if (!sno.getText().equals("") && !bno.getText().equals("")) {
                try (Connection connection = DriverManager.getConnection(connectionUrl);
                     CallableStatement callableStatement = connection.prepareCall("{?= call dbo.order_book(?,?)}");) {
                    //'?' 的作用的作为一个占位符，1，2意为第几个问号。
                /*String order = null;
                if (!sno.getText().equals("")||!bno.getText().equals("")){
                    order = "execute order_book @sno=\'"+sno.getText()+"\'@bno=\'"+bno.getText()+"\';";
                    //ResultSet resultSet = statement.executeQuery(order);
                }*/
                    callableStatement.registerOutParameter(1, Types.INTEGER);
                    callableStatement.setString(2, sno.getText());
                    callableStatement.setString(3, bno.getText());
                    callableStatement.execute();
                    switch (callableStatement.getInt(1)) {
                        case 0:waring.setTextFill(Color.GREEN);
                            waring.setText("借阅成功！");
                            break;
                        case -1:waring.setTextFill(Color.RED);
                            waring.setText("有书超期未还，无法借阅！");
                            break;
                        case -2:waring.setTextFill(Color.RED);
                            waring.setText("当前借书数量已达上限，无法借阅！");
                            break;
                        case -3:waring.setTextFill(Color.RED);
                            waring.setText("图书库存不足，无法借阅！");
                            break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                waring.setTextFill(Color.RED);
                waring.setText("请输入借书证号或图书编号！");
            }
        });

    }
}
