module com.textanalyzer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens com.textanalyzer to javafx.fxml;
    exports com.textanalyzer;
}