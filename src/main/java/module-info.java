module org.example.lr4_tech_roz_sys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.lr4_tech_roz_sys to javafx.fxml;
    exports org.example.lr4_tech_roz_sys;
}