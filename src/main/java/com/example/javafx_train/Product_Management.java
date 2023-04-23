package com.example.javafx_train;

import com.example.javafx_train.DAO.GetTheConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class Product_Management implements Initializable {
    Alert alert;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    @FXML
    private Button Add_Button;
    @FXML
    private ImageView MinimizeImage;
    @FXML
    private ImageView CloseImage;
    @FXML
    private Button Delete_Button;

    @FXML
    private TableView<Product> Product_Table;

    @FXML
    private TableColumn<Product, String> Table_Product_Id;

    @FXML
    private TableColumn<Product, String> Table_Product_Name;
    @FXML
    private Button backToBill;
    @FXML
    private TableColumn<Product, String> Table_Product_Price;
    @FXML
    private TextField txt_Id;

    @FXML
    private TextField txt_name;

    @FXML
    private Button Update_Button;

    @FXML
    private TextField txt_Price;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    void EventHandler(ActionEvent event) throws SQLException, IOException {
        if (event.getSource() == Add_Button) {
            if (txt_name.getText().isEmpty() || txt_Id.getText().isEmpty() || txt_name.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Enter the required data");
                alert.setTitle("Error");
                alert.showAndWait();
            } else {
                if (txt_name.getText().isEmpty() || txt_Id.getText().isEmpty() || txt_name.getText().isEmpty()) {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Enter the required data");
                    alert.setTitle("Error");
                    alert.showAndWait();
                } else {
                    try {
                        preparedStatement = GetTheConnection.getConnect().prepareStatement("INSERT INTO Billing_List VALUES(?,?,?);");
                        preparedStatement.setString(1, txt_Id.getText());
                        preparedStatement.setString(2, txt_name.getText());
                        preparedStatement.setString(3, txt_Price.getText());
                        preparedStatement.executeUpdate();
                        Table_Display();
                        clear();
                    } catch (Exception InvocationTargetException) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("The element is already present");
                        alert.setTitle("Error");
                        alert.showAndWait();
                        clear();
                    }
                }
            }
        }
        else  if(event.getSource()==backToBill)
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            root.setOnMousePressed(event1 -> {
                xOffset = event1.getSceneX();
                yOffset = event1.getSceneY();
            });

            root.setOnMouseDragged(event12 -> {
                stage.setX(event12.getScreenX() - xOffset);
                stage.setY(event12.getScreenY() - yOffset);
            });
            stage.setScene(scene);
            stage.show();
        }
        else if (event.getSource() == Update_Button) {
            if (txt_name.getText().isEmpty() || txt_Id.getText().isEmpty() || txt_name.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Enter the required data");
                alert.setTitle("Error");
                alert.showAndWait();
            } else {
                preparedStatement = GetTheConnection.getConnect().prepareStatement("update BILLING_LIST SET product_name=?,price=? where id_product=?;");
                preparedStatement.setString(3, txt_Id.getText());
                preparedStatement.setString(1, txt_name.getText());
                preparedStatement.setString(2, txt_Price.getText());
                preparedStatement.executeUpdate();
                Table_Display();
                clear();
            }
        } else if (event.getSource() == Delete_Button) {
            if (txt_Id.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("select the data to be deleted");
                alert.setTitle("Error");
                alert.showAndWait();
            } else {
                preparedStatement = GetTheConnection.getConnect().prepareStatement("delete from BILLING_LIST where id_product=?;");
                preparedStatement.setString(1, txt_Id.getText());
                preparedStatement.executeUpdate();
                Table_Display();
                clear();
            }
        }

    }

    void clear() {
        txt_Price.clear();
        txt_Id.clear();
        txt_name.clear();
    }

    protected void Table_Display() throws SQLException {
        ObservableList<Product> ob = FXCollections.observableArrayList();
        statement = GetTheConnection.getConnect().createStatement();
        resultSet = statement.executeQuery("select * from Billing_List;");
        while (resultSet.next()) {
            Product product = new Product();
            product.setProduct_id(resultSet.getString(1));
            product.setProduct_name(resultSet.getString(2));
            product.setProduct_price(resultSet.getString(3));
            ob.add(product);
        }
        Product_Table.setItems(ob);
        Table_Product_Id.setCellValueFactory(id -> id.getValue().product_idProperty());
        Table_Product_Name.setCellValueFactory(name -> name.getValue().product_nameProperty());
        Table_Product_Price.setCellValueFactory(price -> price.getValue().product_priceProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CloseImage.setOnMouseClicked(eve->System.exit(0));
        MinimizeImage.setOnMouseClicked(e -> ((Stage)((ImageView)e.getSource()).getScene().getWindow()).setIconified(true));
        try {
            Table_Display();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Product_Table.setOnMouseClicked(event -> {
            Product selectedItem = Product_Table.getSelectionModel().getSelectedItem();
            txt_Id.setText(selectedItem.getProduct_id());
            txt_name.setText(selectedItem.getProduct_name());
            txt_Price.setText(selectedItem.getProduct_price());
        });
    }

    static class Product {
        private final StringProperty Product_id;
        private final StringProperty Product_name;
        private final StringProperty Product_price;

        public Product() {
            Product_id = new SimpleStringProperty(this, "product_id");
            Product_name = new SimpleStringProperty(this, "product_name");
            Product_price = new SimpleStringProperty(this, "product_price");
        }

        public String getProduct_id() {
            return Product_id.get();
        }

        public StringProperty product_idProperty() {
            return Product_id;
        }

        public void setProduct_id(String product_id) {
            this.Product_id.set(product_id);
        }

        public String getProduct_name() {
            return Product_name.get();
        }

        public StringProperty product_nameProperty() {
            return Product_name;
        }

        public void setProduct_name(String product_name) {
            this.Product_name.set(product_name);
        }

        public String getProduct_price() {
            return Product_price.get();
        }

        public StringProperty product_priceProperty() {
            return Product_price;
        }

        public void setProduct_price(String product_price) {
            this.Product_price.set(product_price);
        }
    }
}