package com.example.javafx_train;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Billing {

    @FXML
    protected Label Amount_By_Customer;
    public void Setters(float input1,float input2)
    {
        Amount_By_Customer.setText(String.valueOf(input2));
        The_To_Pay.setText(String.valueOf(input1));
    }
     float PayAmount=0;
    float CustomerAmount=0;
    @FXML
    protected Label The_To_Pay;
}
