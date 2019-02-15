package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField ageField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField nameField;

    private Connection connection;

    public void aboutProgram() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText("Наша новая программа");
        alert.setHeaderText(null);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Properties props = new Properties();
        props.setProperty("user", "eugeny");
        props.setProperty("password", "123");
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mainacaddemo1", props);
            System.out.println("connection ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertToDB() {
        String name = nameField.getText();
        if (name.isBlank()) {
            // Alert
            return;
        }
        int age = Integer.parseInt(ageField.getText());
        double salary = Double.parseDouble(salaryField.getText());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into person (name, age, salary) values (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setDouble(3, salary);
            preparedStatement.executeUpdate();
            nameField.clear();
            ageField.clear();
            salaryField.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
