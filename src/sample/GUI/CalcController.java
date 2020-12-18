package sample.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.Logic.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CalcController implements Initializable  {

    public Model model;

    public VBox AddPanel;
    public TextField txtName;
    public TextField txtNumber;

    public Button btnAdd;
    public TextField txtAmount;
    public TextField txtPercent;
    public ChoiceBox cmbTime;
    public TextField txtIncome;
    public CheckBox chcCap;
    public Button btnCalc;
    public Label lblError;
    public Label lblError1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Validation(txtAmount);
        Validation(txtPercent);

        cmbTime.getItems().setAll(
                Deposit.Time.month1,
                Deposit.Time.month3,
                Deposit.Time.month6,
                Deposit.Time.year1,
                Deposit.Time.year2,
                Deposit.Time.year3,
                Deposit.Time.year5
        );
        cmbTime.setValue(Deposit.Time.month1);


        cmbTime.setConverter(new StringConverter<Deposit.Time>() {
            @Override
            public String toString(Deposit.Time object) {

                switch (object) {
                    case month1:
                        return "1 месяц";
                    case month3:
                        return "3 месяца";
                    case month6:
                        return "6 месяцев";
                    case year1:
                        return "1 год";
                    case year2:
                        return "2 года";
                    case year3:
                        return "3 года";
                    case year5:
                        return "5 лет";

                }
                return null;
            }

            @Override
            public Deposit.Time fromString(String string) {
                // этот метод не трогаем так как наш комбобкос имеет фиксированный набор элементов
                return null;
            }
        });

    }

    public void Validation(TextField txt){

        txt.textProperty().addListener((observable ,oldvalue,newvalue )-> {

            if ( newvalue == null || txt.getText().trim().equals( "" ))
            {
                lblError.setVisible(true);
            }
            else {
                lblError.setVisible(false);
                if (txt.getText().trim().length() > 0) {
                    Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
                    TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
                        return pattern.matcher(change.getControlNewText()).matches() ? change : null;
                    });

                    txt.setTextFormatter(formatter);
                }
            }
        });

    }

    public void onCancelClick(ActionEvent actionEvent) {
    }

    public void onSaveClick(ActionEvent actionEvent) {

        if (txtName.getText() == null || txtName.getText().trim().equals( "" )) {
            lblError1.setVisible(true);
        }
        else{
            this.model.add(getDeposit());
            ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
        }
    }

    public void onCalculateClick(ActionEvent actionEvent) {
        if ( txtAmount.getText() == null || txtAmount.getText().trim().equals( "" ) || txtPercent.getText() == null || txtPercent.getText().trim().equals( "" )  )
        {
            lblError.setVisible(true);
        }
        else {
            lblError.setVisible(false);
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            int amount = Integer.parseInt(txtAmount.getText());
            Deposit.Time time = (Deposit.Time) this.cmbTime.getValue();
            double percent = Double.parseDouble(txtPercent.getText());
            boolean cap = this.chcCap.isSelected();

            if (!cap) {
                if (time == Deposit.Time.year1) {
                    YearDeposit x = new YearDeposit(amount, time, percent);
                    txtIncome.setText(String.valueOf(decimalFormat.format(x.getIncome())));
                }
                else {

                    NotCapDeposit x = new NotCapDeposit(amount, time, percent);
                    txtIncome.setText(String.valueOf(decimalFormat.format(x.getIncome())));}
            } else {
                CapDeposit x = new CapDeposit(amount, time, percent);
                txtIncome.setText(String.valueOf(decimalFormat.format(x.getIncome())));
            }

            btnAdd.setVisible(true);
        }
    }


    public void onAddClicked(ActionEvent actionEvent) {
        AddPanel.setVisible(true);
        AddPanel.setManaged(true);
    }

    public Deposit getDeposit() {

        Deposit result = null;

        boolean cap = this.chcCap.isSelected();

        int amount = Integer.parseInt(txtAmount.getText());
        Deposit.Time time = (Deposit.Time) this.cmbTime.getValue();
        double percent = Double.parseDouble(txtPercent.getText());

        String Name = txtName.getText();

        double income = Double.parseDouble(txtIncome.getText().replace(",","."));
        System.out.println(income);


        if (!cap) {
            if (time == Deposit.Time.year1) {

                result = new YearDeposit(Name,amount, time, percent,income);
            }
            else {
                result = new NotCapDeposit(Name,amount, time, percent,income);
            }

        } else {
            result = new CapDeposit(Name,amount, time, percent,income);

        }
        return result;
    }
}