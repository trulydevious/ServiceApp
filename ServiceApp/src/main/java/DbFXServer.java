import ib.AdminDAO;
import ib.DBUtil;
import ib.Reclamation;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DbFXServer {
    public DBUtil dbAccess;
    private AdminDAO adminDAO;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnFilter;

    @FXML
    private Button btnLogOutAdmin;

    @FXML
    private Button btnShowStats;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Reclamation, Integer> client;

    @FXML
    private TableColumn<Reclamation, String> date;

    @FXML
    private DatePicker dateFilterFrom;

    @FXML
    private DatePicker dateFilterTo;

    @FXML
    private TableColumn<Reclamation, String> deviceId;

    @FXML
    private TableColumn<Reclamation, Integer> id;

    @FXML
    private ImageView imAdminImg;

    @FXML
    private SplitMenuButton listFilterStatus;

    @FXML
    private SplitMenuButton listStatusUpdate;

    @FXML
    private TableColumn<Reclamation, Double> price;

    @FXML
    private TableColumn<Reclamation, String> status;

    @FXML
    private TableView<Reclamation> tableViewDataAdmin;

    @FXML
    private TextField tfIdUpdate;

    /**
     * This class initialize connection to databes by user which has access updateing and showing reclamations table.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert btnFilter != null : "fx:id=\"btnFilter\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert btnLogOutAdmin != null : "fx:id=\"btnLogOutAdmin\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert btnShowStats != null : "fx:id=\"btnShowStats\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert client != null : "fx:id=\"client\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert dateFilterFrom != null : "fx:id=\"dateFilterFrom\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert dateFilterTo != null : "fx:id=\"dateFilterTo\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert deviceId != null : "fx:id=\"deviceId\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert id != null : "fx:id=\"id\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert imAdminImg != null : "fx:id=\"imAdminImg\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert listFilterStatus != null : "fx:id=\"listFilterStatus\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert listStatusUpdate != null : "fx:id=\"listStatusUpdate\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert status != null : "fx:id=\"status\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert tableViewDataAdmin != null : "fx:id=\"tableViewDataAdmin\" was not injected: check your FXML file 'dbFXServer.fxml'.";
        assert tfIdUpdate != null : "fx:id=\"tfIdUpdate\" was not injected: check your FXML file 'dbFXServer.fxml'.";

        dbAccess = new DBUtil("admin", "admin");
        adminDAO = new AdminDAO(dbAccess);
        try {
            dbAccess.dbConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        id.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("id"));
        price.setCellValueFactory(new PropertyValueFactory<Reclamation, Double>("price"));
        date.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("date"));
        client.setCellValueFactory(new PropertyValueFactory<Reclamation, Integer>("clientID"));
        deviceId.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("deviceID"));
        status.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("status"));

        LocalDate now = LocalDate.now();
        dateFilterTo.setValue(now);
    }

    /**
     * This method returns to main window.
     * @param actionEvent
     * @throws IOException
     */
    public void LogoutClicked(javafx.event.ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbFX.fxml"));
        Parent root = loader.load();
        Stage firstStage = new Stage();
        Scene scene = new Scene(root,675,525);
        firstStage.setScene(scene);
        firstStage.show();
        Stage stage = (Stage) btnLogOutAdmin.getScene().getWindow();
        stage.close();
    }

    /**
     * This method executes select table query for chosen filters.
     * @param actionEvent
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void ShowDataClicked(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LocalDate from = dateFilterFrom.getValue();
        LocalDate to = dateFilterTo.getValue();
        String statusFilter = listFilterStatus.getText().toString();
        if (from == null) from = LocalDate.parse("01-01-1900", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (from.isBefore(to) && statusFilter.equals("All")){
            try {
                tableViewDataAdmin.getItems().clear();
                ObservableList<Reclamation> reclamationsData = adminDAO.showAllReclamations(from, to);
                populateTable(reclamationsData);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        else{
            try {

                tableViewDataAdmin.getItems().clear();
                ObservableList<Reclamation> reclamationsData = adminDAO.showAllReclamations(from, to, statusFilter);
                populateTable(reclamationsData);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method populates table.
     * @param reclamationsData
     */
    private void populateTable(ObservableList<Reclamation> reclamationsData) {
        tableViewDataAdmin.setItems(reclamationsData);
    }

    /**
     * This method opens statistics stage.
     * @param actionEvent
     * @throws IOException
     */
    public void ShowStatusClicked(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage thirdStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dbFXStats.fxml"));
        Scene scene = new Scene(root, 675, 525);
        thirdStage.setScene(scene);
        thirdStage.show();
    }

    /**
     * This method executes updates status for provided reclamationID.
     * @param actionEvent
     */
    public void UpdateClicked(javafx.event.ActionEvent actionEvent) {
        String statusFilter = listStatusUpdate.getText().toString();
        String reclamationID = tfIdUpdate.getText().toString();
        if (reclamationID != null && !statusFilter.equals("Select status..."))
            try {
                adminDAO.updateReclamation(reclamationID, statusFilter);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    /**
     * This method is used to make SplitMenuButton list all options and change it's text.
     * @param actionEvent
     */
    public void OnFilterStatusDataClicked(javafx.event.ActionEvent actionEvent) {
        ObservableList<MenuItem> itemList = listFilterStatus.getItems();
        for(MenuItem m: itemList){
            m.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    listFilterStatus.setText(m.getText().toString());
                }
            });
        }
    }

    /**
     * This method is used to make SplitMenuButton list all options and change it's text.
     * @param actionEvent
     */
    public void OnSelectStatusUpdate(javafx.event.ActionEvent actionEvent) {
        ObservableList<MenuItem> itemList = listStatusUpdate.getItems();
        for(MenuItem m: itemList){
            m.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
                @Override
                public void handle(javafx.event.ActionEvent event) {
                    listStatusUpdate.setText(m.getText().toString());
                }
            });
        }
    }
}

