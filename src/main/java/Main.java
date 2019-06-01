import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage mainStage) {
        Button queryBook = new Button("查找图书");
        Button orderBook = new Button("借书");
        Button returnBook = new Button("还书");
        Button queryBill = new Button("查看余额");
        Button queryRecord = new Button("查看借阅记录");
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(5);
        mainVbox.setFillWidth(true);
        mainVbox.getChildren().addAll(queryBook,orderBook,returnBook,queryBill,queryRecord);
        mainVbox.setAlignment(Pos.CENTER);
        mainStage.setMinHeight(450);
        mainStage.setMinWidth(350);
        mainStage.setTitle("图书管理子系统");
        mainStage.setScene(new Scene(mainVbox));
        mainStage.show();
        //设置按钮事件
        queryBook.setOnAction(event -> {
            QueryBook queryBook1 = new QueryBook();
            Stage stage = new Stage();
            try {
                queryBook1.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        orderBook.setOnAction(event -> {
            OrderBook orderBook1 = new OrderBook();
            Stage stage= new Stage();
            try {
                orderBook1.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        returnBook.setOnAction(event -> {
            ReturnBook returnBook1 = new ReturnBook();
            Stage stage= new Stage();
            try {
                returnBook1.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        queryBill.setOnAction(event -> {
            QueryBill queryBill1 = new QueryBill();
            Stage stage= new Stage();
            try {
                queryBill1.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
        queryRecord.setOnAction(event -> {
            QueryRecord queryRecord1 = new QueryRecord();
            Stage stage= new Stage();
            try {
                queryRecord1.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });


        /*String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=library;"
                        + "user=IITII;"
                        + "password=Nchu172041;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("ok\n");
        }
        catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据库连接失败！\n");
        }*/

    }
}
