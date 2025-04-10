module com.example.lab8javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.lab8javafx to javafx.fxml;
    exports com.example.lab8javafx;
}