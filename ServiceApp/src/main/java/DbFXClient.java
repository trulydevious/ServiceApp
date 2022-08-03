
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import ib.ClientDAO;
import ib.DBUtil;
import ib.ReclamationStatus;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DbFXClient {

    /**
     * DbFXClient class is used to cooperate with dbFXClient.fxml.
     */

    private String clientId;
    public DBUtil dbAccess;
    private ClientDAO clientDAO;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnShowAll;

    @FXML
    private DatePicker dpBoughtDate;

    @FXML
    private TableColumn<ReclamationStatus, String> deviceName;

    @FXML
    private TableColumn<ReclamationStatus, Integer> clientID;

    @FXML
    private TableColumn<ReclamationStatus, Double> price;

    @FXML
    private TableColumn<ReclamationStatus, String> serialNo;

    @FXML
    private TableColumn<ReclamationStatus, String> reclamationStatus;

    @FXML
    private TableView allReclamations;

    @FXML
    private TextField tfClientID;

    @FXML
    private TextField tfDeviceName;

    @FXML
    private TextField tfSerialNo;

    @FXML
    private TextField tfDevicePrice;

    @FXML
    private TextField tfType;


    /**
     * This method allows client to add new reclamation.
     * @param event
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    @FXML
    void AddCliked(ActionEvent event) throws SQLException, ClassNotFoundException {

        try {
            clientDAO.setZero();
            if (!tfClientID.getText().equals("") && !tfDeviceName.getText().equals("") && !tfSerialNo.getText().equals("") &&
                    !dpBoughtDate.toString().equals("") && !tfDevicePrice.getText().equals("") && !tfType.getText().equals("") && !dpBoughtDate.getValue().isAfter(LocalDate.now())) {
                clientDAO.insertReclamation(tfSerialNo.getText(), tfDeviceName.getText(), tfType.getText(), tfDevicePrice.getText(), dpBoughtDate.getValue().toString(), tfClientID.getText());
                clientDAO.setOne();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }
    }

    /**
     * This method iss used to pass information between two scenes.
     * @param message
     */

    public void transferMessage(String message) {
        this.clientId = message;
        tfClientID.setText(clientId);
    }

    /**
     * This method allows client to log out.
     * @param event
     * @throws IOException
     */

    @FXML
    void LogOutClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbFX.fxml"));
        Parent root = loader.load();
        Stage firstStage = new Stage();
        Scene scene = new Scene(root,675,525);
        firstStage.setScene(scene);
        firstStage.show();

        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to show all client's reclamation.
     * @param event
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    @FXML
    void ShowAllCliked(ActionEvent event) throws SQLException, ClassNotFoundException {

        try {
            allReclamations.getItems().clear();
            ObservableList<ReclamationStatus> data = clientDAO.showReclamations(tfClientID.getText());
            showAllReclamations(data);

        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }
    }

    /**
     * This method initialize connection to databes.
     */

    @FXML
    void initialize() {
        assert allReclamations != null : "fx:id=\"allReclamations\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert btnLogOut != null : "fx:id=\"btnLogOut\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert btnShowAll != null : "fx:id=\"btnShowAll\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert clientID != null : "fx:id=\"clientID\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert deviceName != null : "fx:id=\"deviceName\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert dpBoughtDate != null : "fx:id=\"dpBoughtDate\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert reclamationStatus != null : "fx:id=\"reclamationStatus\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert serialNo != null : "fx:id=\"serialNo\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert tfClientID != null : "fx:id=\"tfClientID\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert tfDeviceName != null : "fx:id=\"tfDeviceName\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert tfDevicePrice != null : "fx:id=\"tfDevicePrice\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert tfSerialNo != null : "fx:id=\"tfSerialNo\" was not injected: check your FXML file 'dbFXClient.fxml'.";
        assert tfType != null : "fx:id=\"tfType\" was not injected: check your FXML file 'dbFXClient.fxml'.";


        dbAccess = new DBUtil("client", "client");
        clientDAO = new ClientDAO(dbAccess);
        try {
            dbAccess.dbConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        clientID.setCellValueFactory(new PropertyValueFactory<ReclamationStatus, Integer>("clientID"));
        deviceName.setCellValueFactory(new PropertyValueFactory<ReclamationStatus, String>("deviceName"));
        serialNo.setCellValueFactory(new PropertyValueFactory<ReclamationStatus, String>("serialNo"));
        price.setCellValueFactory(new PropertyValueFactory<ReclamationStatus, Double>("price"));
        reclamationStatus.setCellValueFactory(new PropertyValueFactory<ReclamationStatus, String>("reclamationStatus"));

    }

    /**
     * This method is used to set data in reclamation table.
     * @param data
     */

    private void showAllReclamations(ObservableList<ReclamationStatus> data) {
        allReclamations.setItems(data);
    }

}
