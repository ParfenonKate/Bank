package sample.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.util.StringConverter;
import sample.Logic.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public TableView mainTable;


    Model model = new Model();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model.addDataChangedListener(dep -> { mainTable.setItems(FXCollections.observableArrayList(dep)); });


        TableColumn<Deposit, String> idColumn = new TableColumn<>("№ вклада");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Deposit, String> nameColumn = new TableColumn<>("ФИО клиента");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("client"));

        TableColumn<Deposit, String> amountColumn = new TableColumn<>("Сумма вклада");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Deposit, String> timeconvertedColumn = new TableColumn<>("Срок");
        timeconvertedColumn.setCellValueFactory(cellData -> {
            String result;
            switch (cellData.getValue().time) {
                case month1:
                    result =  "1 месяц";
                    break;
                case month3:
                    result =  "3 месяца";
                    break;
                case month6:
                    result =  "6 месяцев";
                    break;
                case year1:
                    result =  "1 год";
                    break;
                case year2:
                    result =  "2 года";
                    break;
                case year3:
                    result =  "3 года";
                    break;
                case year5:
                    result =  "5 лет";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + cellData.getValue().time);
            }
            return new SimpleStringProperty(result);
        });

        TableColumn<Deposit, String> percentColumn = new TableColumn<>("Процентная ставка");
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));

        TableColumn<Deposit, String> capitalColumn = new TableColumn<>("Начисление процентов");
        capitalColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().cap) return new SimpleStringProperty("Добавлять ко вкладу");
            else  return new SimpleStringProperty("Начислять на отдельный счет");
        });

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        TableColumn<Deposit, String> incomeFinalColumn = new TableColumn<>("Доход по вкладу");
        incomeFinalColumn.setCellValueFactory(cellData -> { return new SimpleStringProperty( decimalFormat.format(cellData.getValue().income)); });


        mainTable.getColumns().addAll(idColumn, nameColumn,amountColumn,timeconvertedColumn,percentColumn,capitalColumn,incomeFinalColumn);


    }


    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Calc.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(this.mainTable.getScene().getWindow());

        CalcController controller = loader.getController();

        controller.model = model;

        stage.showAndWait();
    }

    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));


        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            model.saveToFile(file.getPath());
        }

    }

    public void onLoadFromFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));


        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            model.loadFromFile(file.getPath());
        }
    }
}
