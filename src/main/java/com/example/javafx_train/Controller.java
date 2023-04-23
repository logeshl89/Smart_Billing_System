package com.example.javafx_train;

import com.example.javafx_train.DAO.GetTheConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Label Date_txt;
    @FXML
    public Label Time_txt;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button UpdateButton;
    PreparedStatement preparedStatement;
    @FXML
    private TextField AmontCustomer;
    @FXML
    private ImageView MinusIcon;
    @FXML
    private ImageView CloseIcon;
    @FXML
    private TextField AmountRepay;
    @FXML
    private TableColumn<Billing_Table_Data_Add, String> BillId;
    @FXML
    private TableColumn<Billing_Table_Data_Add, String> BillPrice;
    @FXML
    private Button ProductUpdate;
    @FXML
    private TableColumn<Billing_Table_Data_Add, String> BillProductName;
    @FXML
    private TableColumn<Billing_Table_Data_Add, String> BillQuantity;
    @FXML
    private TextField ProductPrice_No_Total;
    @FXML
    private TextField ProductPrice;
    @FXML
    private Spinner<Integer> ProductQuantity;
    @FXML
    private Button New_bill;
    private final boolean stop = false;
    @FXML
    private TableView<Billing_Table_Data_Add> Table_display;
    double Price_Temp = 0;
    @FXML
    private Button Bill_Button;
    @FXML
    private TextField Amount_to_Pay;
    @FXML
    private TextField ProductName;

    Alert alert;
    @FXML
    private TextField ItemId;
    private SpinnerValueFactory<Integer> ProductQuantity_Spinner;
    int Price = 1;
    @FXML
    private Button AddButton;
    int Price_Qty = 1;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void AddTheData() {
        Billing_Table_Data_Add billing_table_data_add = new Billing_Table_Data_Add();
        billing_table_data_add.setId(ItemId.getText());
        billing_table_data_add.setProduct_name(ProductName.getText());
        billing_table_data_add.setProduct_Quntity(String.valueOf(ProductQuantity.getValue()));
        billing_table_data_add.setProduct_Price(ProductPrice.getText());
        ob.add(billing_table_data_add);
        Table_display.setItems(ob);
        BillId.setCellValueFactory(id -> id.getValue().idProperty());
        BillProductName.setCellValueFactory(e -> e.getValue().product_nameProperty());
        BillPrice.setCellValueFactory(price -> price.getValue().product_PriceProperty());
        BillQuantity.setCellValueFactory(q -> q.getValue().product_QuntityProperty());
        Amount_to_pay_Double += Double.parseDouble(ProductPrice.getText());
        Amount_to_Pay.setText(Double.toString(Amount_to_pay_Double));
        Clear();
        ItemId.setFocusTraversable(true);
    }

    @FXML
    void Id() {

        ProductQuantity_Spinner.setValue(1);
        ItemId.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                if (ItemId.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Not valid");
                    alert.setContentText("Enter the product id");
                    alert.showAndWait();
                } else {
                    try {
                        ResultSet resultSet;
                        preparedStatement = GetTheConnection.getConnect().prepareStatement("select * from Billing_List where id_Product=?;");
                        preparedStatement.setString(1, ItemId.getText());
                        resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            ProductName.setText(resultSet.getString(2));
                            ProductPrice_No_Total.setText(resultSet.getString(3));
                            ProductPrice.setText(String.valueOf(ProductQuantity.getValue() * Double.parseDouble(ProductPrice_No_Total.getText())));
                            Price = resultSet.getInt(3);
                        } else {
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Not Valid");
                            alert.setContentText("The entered id is not valid");
                            alert.showAndWait();
                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }

    double Amount_to_pay_Double = 0;
    ObservableList<Billing_Table_Data_Add> ob = FXCollections.observableArrayList();

    @FXML
    void handle(ActionEvent events) throws IOException {
        if (events.getSource() == UpdateButton) {
            Billing_Table_Data_Add selectedItem = Table_display.getSelectionModel().getSelectedItem();
            Amount_to_Pay.setText(String.valueOf(Double.parseDouble(Amount_to_Pay.getText()) - Double.parseDouble(selectedItem.getProduct_Price())));
            selectedItem.setProduct_Price(ProductPrice.getText());
            selectedItem.setProduct_Quntity(String.valueOf(ProductQuantity.getValue()));
            Amount_to_Pay.setText(String.valueOf(Double.parseDouble(Amount_to_Pay.getText()) + Double.parseDouble(ProductPrice.getText())));
            ProductQuantity_Spinner.setValue(1);
            Clear();
        } else if (events.getSource() == Bill_Button) {
            FXMLLoader fx = new FXMLLoader(getClass().getResource("Billing.fxml"));
            Parent root = fx.load();
            Billing billing=fx.getController();
            billing.Setters(Float.parseFloat(Amount_to_Pay.getText()),Float.parseFloat(AmontCustomer.getText()));
            Stage stage = (Stage) ((Node) events.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.setScene(scene);
            stage.show();
        }
        else if (events.getSource() == AddButton) {
            Billing_Table_Data_Add billing_table_data_add = new Billing_Table_Data_Add();
            billing_table_data_add.setId(ItemId.getText());
            billing_table_data_add.setProduct_name(ProductName.getText());
            billing_table_data_add.setProduct_Quntity(String.valueOf(ProductQuantity.getValue()));
            billing_table_data_add.setProduct_Price(ProductPrice.getText());
            ob.add(billing_table_data_add);
            Table_display.setItems(ob);
            BillId.setCellValueFactory(id -> id.getValue().idProperty());
            BillProductName.setCellValueFactory(e -> e.getValue().product_nameProperty());
            BillPrice.setCellValueFactory(price -> price.getValue().product_PriceProperty());
            BillQuantity.setCellValueFactory(q -> q.getValue().product_QuntityProperty());
            Amount_to_pay_Double += Double.parseDouble(ProductPrice.getText());
            Amount_to_Pay.setText(Double.toString(Amount_to_pay_Double));
            Clear();
            ProductQuantity_Spinner.setValue(1);
        }
        else if (events.getSource() == ProductUpdate) {
            FXMLLoader fx = new FXMLLoader(getClass().getResource("Product_Management.fxml"));
            Parent root = fx.load();
            Stage stage = (Stage) ((Node) events.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.setScene(scene);
            stage.show();
        } else if (events.getSource() == DeleteButton) {
            Billing_Table_Data_Add selectedItem = Table_display.getSelectionModel().getSelectedItem();
            Amount_to_Pay.setText(String.valueOf(Double.parseDouble(Amount_to_Pay.getText()) - Double.parseDouble((selectedItem.getProduct_Price()))));
            Table_display.getItems().remove(selectedItem);
            Clear();
        } else if (events.getSource() == New_bill) {
            Table_display.getItems().removeAll(Table_display.getItems());
            Clear();
            Amount_to_Pay.setText("0.00");
            AmontCustomer.setText("0.00");
            AmountRepay.setText("0.00");
            ProductQuantity_Spinner.setValue(1);
        }
    }

    private void Clear() {
        ItemId.clear();
        ProductName.clear();
        ProductPrice.clear();
        ProductPrice.clear();
        ProductPrice_No_Total.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CloseIcon.setOnMouseClicked(eve -> System.exit(0));
        MinusIcon.setOnMouseClicked(e -> {
            ((Stage) ((ImageView) e.getSource()).getScene().getWindow()).setIconified(true);
        });
        ProductQuantity_Spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        ProductQuantity_Spinner.setValue(1);
        ProductQuantity.setValueFactory(ProductQuantity_Spinner);
        ProductQuantity.valueProperty().addListener((observableValue, integer, t1)
                -> ProductPrice.setText(String.valueOf(ProductQuantity.getValue() * Double.parseDouble(ProductPrice_No_Total.getText()))));
        AmontCustomer.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (Double.parseDouble(AmontCustomer.getText()) - Double.parseDouble(Amount_to_Pay.getText()) >= 0) {
                    AmountRepay.setText(String.valueOf(Double.parseDouble(AmontCustomer.getText()) - Double.parseDouble(Amount_to_Pay.getText())));
                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("The amount is not sufficient");
                    alert.showAndWait();
                }
            }
        });
        ProductName.setEditable(false);

        Table_display.setOnMouseClicked((MouseEvent event) -> {
            Billing_Table_Data_Add b = Table_display.getSelectionModel().getSelectedItem();
            ItemId.setText(b.getId());
            ProductName.setText(b.getProduct_name());
            ProductPrice.setText(b.getProduct_Price());
            ProductQuantity_Spinner.setValue(Integer.parseInt(b.getProduct_Quntity()));
            Price_Temp = Double.parseDouble(b.getProduct_Price());
            Price_Qty = Integer.parseInt(b.getProduct_Quntity());
            try {
                preparedStatement = GetTheConnection.getConnect().prepareStatement("select price from Billing_List where id_Product=?;");
                preparedStatement.setString(1, ItemId.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                ProductPrice_No_Total.setText(resultSet.getString(1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Price_Temp);
            System.out.println(Price_Qty);
            Amount_to_Pay.getText();
        });
        Current_date_time();
    }

    void Current_date_time() {
        Thread time = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                Platform.runLater(() -> {
                    Time_txt.setText(timenow);
                });
            }
        });
        Thread date = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
            String datenow = sdf.format(new Date());
            Date_txt.setText(datenow);
        });
        date.start();
        time.start();
    }
}