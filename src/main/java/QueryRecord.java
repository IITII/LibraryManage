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

public class QueryRecord extends Application {
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
        TableColumn<String, Record> column1 = new TableColumn<>("借阅记录ID号");
        column1.setCellValueFactory(new PropertyValueFactory<>("recordID"));
        TableColumn<String, Record> column2 = new TableColumn<>("借书证号");
        column2.setCellValueFactory(new PropertyValueFactory<>("sno"));
        TableColumn<String, Record> column3 = new TableColumn<>("图书编号");
        column3.setCellValueFactory(new PropertyValueFactory<>("bno"));
        TableColumn<String, Record> column4 = new TableColumn<>("当前状态");
        column4.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<String, Record> column5 = new TableColumn<>("开始借阅时间");
        column5.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<String, Record> column6 = new TableColumn<>("还书时间");
        column6.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableView.getColumns().addAll(column1,column2,column3,column4,column5,column6);

        //查询主界面
        VBox vbox = new VBox(tableView);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hBox);
        borderPane.setCenter(vbox);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("借阅记录");
        primaryStage.setMinWidth(750);
        primaryStage.show();
        button.setOnAction(event -> {
            try{
                Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();
                String select = null;
                if (textField.getText().equals("")) {
                    select = "select * from Record";
                } else {
                    select = "select * from Record where recordID like \'%" + textField.getText() + "%\';";
                }
                ResultSet resultSet = statement.executeQuery(select);
                tableView.getItems().clear();
                System.out.println("clear");
                while (resultSet.next()){
                    System.out.println("Add");
                    tableView.getItems().add(new Record(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)));
                }
            }catch(SQLException e1){
                e1.printStackTrace();
            }
        });


        //System.out.println("ok\n");
    }
}
