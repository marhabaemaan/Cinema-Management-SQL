module com.example.moviesys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens com.example.moviesys to javafx.fxml;
    exports com.example.moviesys;
}