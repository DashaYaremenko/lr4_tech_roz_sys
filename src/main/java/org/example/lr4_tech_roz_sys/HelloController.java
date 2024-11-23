package org.example.lr4_tech_roz_sys;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class HelloController {
    private static final String URL = "jdbc:mysql://localhost:3306/railwaystat";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1111";
    @FXML
    public TextField IDTrain, NameTrain;
    @FXML
    public TextField TypeTrain;
    @FXML
    public Button AddTrainButton, DeleteTrainButton, UpdateTrainButton, LookTrainButton;
    @FXML
    private TableView<TrainClass> TrainTable;
    @FXML
    private TableColumn<TrainClass, Integer> IDTrainCol;
    @FXML
    private TableColumn<TrainClass, String> NameTrainCol, TypeTrainCol;

    @FXML
    private void AddTrainAction(ActionEvent event) {
        String Id = IDTrain.getText();
        String NameT = NameTrain.getText();
        String TypeT = TypeTrain.getText();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO trains (ID, NameM, TypeTrain) VALUES (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Id);
                preparedStatement.setString(2, NameT);
                preparedStatement.setString(3, TypeT);
                int rowsAdd = preparedStatement.executeUpdate();
                if (rowsAdd > 0) {
                    System.out.println("Запис добавлено успішно");
                    ShowButtonAction(event);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void ShowButtonAction(ActionEvent event) {
        ObservableList<TrainClass> dataList = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM trains";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("ID");
                    String nameT = resultSet.getString("NameM");
                    String typeT = resultSet.getString("TypeTrain");
                    dataList.add(new TrainClass(id, nameT, typeT));
                }
            }
            IDTrainCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            NameTrainCol.setCellValueFactory(new PropertyValueFactory<>("NameM"));
            TypeTrainCol.setCellValueFactory(new PropertyValueFactory<>("TypeTrain"));
            TrainTable.setItems(dataList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void DeleteButtonAction(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String id = IDTrain.getText();
            String sql = "DELETE FROM trains WHERE ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, id);
                int rowsDeleteT = preparedStatement.executeUpdate();
                if (rowsDeleteT > 0) {
                    System.out.println("Запис видалено успішно");
                    TrainTable.getItems().clear();
                    ShowButtonAction(event);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void UpdateButtonAction(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String Id = IDTrain.getText();
            String fieldToUpdate = deterFieldToUpdate();
            String newValue = getFieldText(fieldToUpdate);

            String sql = "UPDATE trains SET " + fieldToUpdate + " = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newValue);
                preparedStatement.setString(2, Id);
                int rowsUpdate = preparedStatement.executeUpdate();
                if (rowsUpdate > 0) {
                    System.out.println("Запис оновлено успішно");
                    ShowButtonAction(event);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String deterFieldToUpdate() {
        if (!NameTrain.getText().isEmpty()) {
            return "NameM";
        } else if (!TypeTrain.getText().isEmpty()) {
            return "TypeTrain";
        } else {
            throw new IllegalArgumentException("Invalid field to update");
        }
    }
    private String getFieldText(String fieldToUpdate) {
        switch (fieldToUpdate) {
            case "NameM":
                return NameTrain.getText();
            case "TypeTrain":
                return TypeTrain.getText();
            default:
                throw new IllegalArgumentException("Invalid field to update: " + fieldToUpdate);
        }
    }
}