
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DbFXNewClient {

    /**
     * DbFXClient class is used to cooperate with dbFXNewClient.fxml.
     */

    public DBUtil dbAccess;
    private ClientDAO clientDAO;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnSignIn;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfNewID;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfNewPass;

    /**
     * This method allows new client to go back to main menu.
     * @param event
     * @throws IOException
     */

    @FXML
    void btnBackClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbFX.fxml"));
        Parent root = loader.load();

        Stage firstStage = new Stage();
        Scene scene = new Scene(root,675,525);
        firstStage.setScene(scene);
        firstStage.show();


        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    /**
     * This method allows to log in service as new client.
     * @param event
     * @throws IOException
     */

    @FXML
    void btnLogInClicked(ActionEvent event) throws IOException {

        if (!tfNewID.getText().equals("Incomplete data.")) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbFXClient.fxml"));
            Parent root = loader.load();
            DbFXClient scene2Controller = loader.getController();
            scene2Controller.transferMessage(tfNewID.getText());

            Stage thirdStage = new Stage();
            Scene scene = new Scene(root, 675, 525);
            thirdStage.setScene(scene);
            thirdStage.show();

            Stage stage = (Stage) btnLogIn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * This method allows to register in service.
     * @param event
     * @throws IOException
     */

    @FXML
    void btnSignInClicked(ActionEvent event) throws SQLException, ClassNotFoundException {

        try {
            if (!tfFirstName.getText().equals("") && !tfLastName.getText().equals("") && !tfEmail.getText().equals("") && !tfPhone.getText().equals("") && !tfNewPass.getText().equals("")) {
                clientDAO.insertNewClient(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText(), tfPhone.getText(), tfNewPass.getText());
                tfNewID.setText(String.valueOf(clientDAO.getNewID()));
            } else tfNewID.setText("Incomplete data.");
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }
    }

    /**
     * This method initialize connection to databes.
     */

    @FXML
    void initialize() {
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert btnLogIn != null : "fx:id=\"btnLogIn\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert btnSignIn != null : "fx:id=\"btnSignIn\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert tfFirstName != null : "fx:id=\"tfFirstName\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert tfLastName != null : "fx:id=\"tfLastName\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert tfNewID != null : "fx:id=\"tfNewID\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert tfPhone != null : "fx:id=\"tfPhone\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";
        assert tfNewPass != null : "fx:id=\"tfNewPass\" was not injected: check your FXML file 'dbFXNewClient.fxml'.";

        dbAccess = new DBUtil("client", "client");
        clientDAO = new ClientDAO(dbAccess);
        try {
            dbAccess.dbConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
