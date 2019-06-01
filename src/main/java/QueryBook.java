import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class QueryBook extends Application {
    private static String connectionUrl =
            "jdbc:sqlserver://localhost:1433;"
                    + "database=library;"
                    + "user=IITII;"
                    + "password=Nchu172041;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";

    @Override
    public void start(Stage primaryStage){

        //界面设计
        TextField textField = new TextField();
        Button button = new Button("立即查找");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(textField,button);
        hBox.setAlignment(Pos.TOP_CENTER);
        TableView tableView = new TableView();

        //列表显示界面
        TableColumn<String, Book> column1 = new TableColumn<>("图书编号");
        column1.setCellValueFactory(new PropertyValueFactory<>("bno"));
        TableColumn<String, Book> column2 = new TableColumn<>("图书名称");
        column2.setCellValueFactory(new PropertyValueFactory<>("bna"));
        TableColumn<String, Book> column3 = new TableColumn<>("图书出版日期");
        column3.setCellValueFactory(new PropertyValueFactory<>("bda"));
        TableColumn<String, Book> column4 = new TableColumn<>("图书出版社");
        column4.setCellValueFactory(new PropertyValueFactory<>("bpu"));
        TableColumn<String, Book> column5 = new TableColumn<>("图书存放位置");
        column5.setCellValueFactory(new PropertyValueFactory<>("bpl"));
        TableColumn<String, Book> column6 = new TableColumn<>("图书价格");
        column6.setCellValueFactory(new PropertyValueFactory<>("bpr"));
        TableColumn<String, Book> column7 = new TableColumn<>("书籍总量");
        column7.setCellValueFactory(new PropertyValueFactory<>("MAX_NUMBER"));
        TableColumn<String, Book> column8 = new TableColumn<>("当前剩余");
        column8.setCellValueFactory(new PropertyValueFactory<>("currentNumber"));
        tableView.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8);

        //查询主界面
        VBox vbox = new VBox(tableView);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(vbox);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("图书查询");
        primaryStage.setMinWidth(750);
        primaryStage.show();
        //Book book = new Book("5", "算法设计与分析",  "2012-08-12","清华大学出版社","B-203", 50,10,4);
        //tableView.getItems().add(book);
        //tableView.getItems().add(new Book("5", "算法设计与分析",  "2012-08-12","清华大学出版社","B-203", 50,10,4));

        button.setOnAction(event -> {
            //ResultSet resultSet = getBookInfo(textField.getText());

            try{
                Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();
                String select = null;
                if (textField.getText().equals("")) {
                    select = "execute getBookInfo @bno=null;";
                } else {
                    select = "execute getBookInfo @bno=\'%" + textField.getText() + "%\';";
                }
                ResultSet resultSet = statement.executeQuery(select);
                tableView.getItems().clear();
                System.out.println("clear");
                while (resultSet.next()){
                    System.out.println("Add");
                    tableView.getItems().add(new Book(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getFloat(6),
                            resultSet.getInt(7),
                            resultSet.getInt(8)));
                }
            }catch(SQLException e1){
                e1.printStackTrace();
            }
        });


            //System.out.println("ok\n");
        }
        /*catch (NumberFormatException e){
            Stage stage = new Stage();
            HBox hBox1 = new HBox();
            Label label = new Label("请输入有效数字")
            hBox.getChildren().add()
        }*/

    //未使用
    private ResultSet getBookInfo(String text){
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            String select = null;
            if (text == null) {
                select = "execute getBookInfo @bno=\'\';";
            } else {
                select = "execute getBookInfo @bno=\'%" + text + "%\';";
            }
            return statement.executeQuery(select);
        }catch(SQLException e1){
            e1.printStackTrace();
        }
        return null;
    }
}
