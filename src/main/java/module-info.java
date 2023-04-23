module com.example.javafx_train {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires javafx.graphics;
    //requires com.jfoenix;

    opens com.example.javafx_train to javafx.fxml;
    exports com.example.javafx_train;
}