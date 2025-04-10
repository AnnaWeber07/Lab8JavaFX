import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    private ObservableList<Automobil> automobile = FXCollections.observableArrayList();
    private TableView<Automobil> tableView = new TableView<>();
    private final String FILE_NAME = "automobile.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadFromFile(); // Load data from file at startup

        // Table setup
        TableColumn<Automobil, Integer> nrCol = new TableColumn<>("Nr");
        nrCol.setCellValueFactory(new PropertyValueFactory<>("nr"));

        TableColumn<Automobil, String> marcaCol = new TableColumn<>("Marcă");
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Automobil, String> modelCol = new TableColumn<>("Model");
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Automobil, String> culoareCol = new TableColumn<>("Culoare");
        culoareCol.setCellValueFactory(new PropertyValueFactory<>("culoare"));

        TableColumn<Automobil, String> taraCol = new TableColumn<>("Țară");
        taraCol.setCellValueFactory(new PropertyValueFactory<>("tara"));

        TableColumn<Automobil, Double> pretCol = new TableColumn<>("Preț");
        pretCol.setCellValueFactory(new PropertyValueFactory<>("pret"));

        tableView.getColumns().addAll(nrCol, marcaCol, modelCol, culoareCol, taraCol, pretCol);
        tableView.setItems(automobile);

        // Form setup
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setVgap(10);
        form.setHgap(10);

        TextField nrField = new TextField();
        TextField marcaField = new TextField();
        TextField modelField = new TextField();
        TextField culoareField = new TextField();
        TextField taraField = new TextField();
        TextField pretField = new TextField();

        form.add(new Label("Nr:"), 0, 0);
        form.add(nrField, 1, 0);
        form.add(new Label("Marcă:"), 0, 1);
        form.add(marcaField, 1, 1);
        form.add(new Label("Model:"), 0, 2);
        form.add(modelField, 1, 2);
        form.add(new Label("Culoare:"), 0, 3);
        form.add(culoareField, 1, 3);
        form.add(new Label("Țară:"), 0, 4);
        form.add(taraField, 1, 4);
        form.add(new Label("Preț:"), 0, 5);
        form.add(pretField, 1, 5);

        // Buttons
        Button addButton = new Button("Adaugă");
        Button deleteButton = new Button("Șterge");
        Button editButton = new Button("Editează");

        addButton.setOnAction(e -> {
            try {
                Automobil auto = new Automobil(
                        Integer.parseInt(nrField.getText()),
                        marcaField.getText(),
                        modelField.getText(),
                        culoareField.getText(),
                        taraField.getText(),
                        Double.parseDouble(pretField.getText())
                );
                automobile.add(auto);
                saveToFile();
                clearFields(nrField, marcaField, modelField, culoareField, taraField, pretField);
            } catch (NumberFormatException ex) {
                showAlert("Eroare", "Introduceți valori valide pentru Nr și Preț!");
            }
        });

        deleteButton.setOnAction(e -> {
            Automobil selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                automobile.remove(selected);
                saveToFile();
            } else {
                showAlert("Eroare", "Selectați un automobil pentru a șterge!");
            }
        });

        editButton.setOnAction(e -> {
            Automobil selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    selected.setNr(Integer.parseInt(nrField.getText()));
                    selected.setMarca(marcaField.getText());
                    selected.setModel(modelField.getText());
                    selected.setCuloare(culoareField.getText());
                    selected.setTara(taraField.getText());
                    selected.setPret(Double.parseDouble(pretField.getText()));
                    tableView.refresh();
                    saveToFile();
                    clearFields(nrField, marcaField, modelField, culoareField, taraField, pretField);
                } catch (NumberFormatException ex) {
                    showAlert("Eroare", "Introduceți valori valide pentru Nr și Preț!");
                }
            } else {
                showAlert("Eroare", "Selectați un automobil pentru a edita!");
            }
        });

        // Layout
        VBox root = new VBox(10, tableView, form, new HBox(10, addButton, deleteButton, editButton));
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Gestionare Automobile - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Automobil auto : automobile) {
                writer.write(auto.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            showAlert("Eroare", "Nu s-a putut salva fișierul!");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    Automobil auto = new Automobil(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            Double.parseDouble(parts[5])
                    );
                    automobile.add(auto);
                }
            } catch (IOException e) {
                showAlert("Eroare", "Nu s-a putut încărca fișierul!");
            }
        }
    }
}