import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import ib.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DbFX {

    public DBUtil dbAccess;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnClient;

    @FXML
    private Button btnServiceUser;

    @FXML
    private Button btnRegister;

    @FXML
    private PasswordField edtPassword;

    @FXML
    private TextField edtUser;

    @FXML
    private TextField edtClient;

    @FXML
    private PasswordField tfPassClient;

    @FXML
    void edtClientID(ActionEvent event) {

    }

    @FXML
    void edtPassword(ActionEvent event) {

    }

    /**
     * This method opens registration stage.
     * @param event
     * @throws IOException
     */
    @FXML
    void btnRegisterClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbFXNewClient.fxml"));
        Parent root = loader.load();

        Stage fourthStage = new Stage();
        Scene scene = new Scene(root,675,525);
        fourthStage.setScene(scene);
        fourthStage.show();

        Stage stage = (Stage) btnClient.getScene().getWindow();
        stage.close();
    }

    /**
     * This method opens Client stage where he can add new reclamation or show his active ones.
     * Client needs to have an account otherwise he won't log in and popUp stage will be opened.
     * @param event
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    void LoginClient(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (logInClient(edtClient.getText(), tfPassClient.getText()) == 1) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dbFXClient.fxml"));
            Parent root = loader.load();
            DbFXClient scene2Controller = loader.getController();
            scene2Controller.transferMessage(edtClient.getText());

            Stage thirdStage = new Stage();
            Scene scene = new Scene(root, 675, 525);
            thirdStage.setScene(scene);
            thirdStage.show();

            Stage stage = (Stage) btnClient.getScene().getWindow();
            stage.close();
        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popOut.fxml"));
            Parent root = loader.load();

            Stage popOutStage = new Stage();
            Scene scene = new Scene(root, 255, 100);
            popOutStage.setScene(scene);
            popOutStage.show();

        }



    }

    /**
     * This method opens Server stage where service worker can update reclamation or show all of them or open stats.
     * Employee needs to have an account otherwise he won't log in and popUp stage will be opened.
     * @param event
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    void LoginService(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (logInAdmin(edtUser.getText(), edtPassword.getText()) == 1) {
            Stage secondStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/dbFXServer.fxml"));
            Scene scene = new Scene(root, 675, 525);
            secondStage.setScene(scene);
            secondStage.show();

            Stage stage = (Stage) btnServiceUser.getScene().getWindow();
            stage.close();
        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/popOut.fxml"));
            Parent root = loader.load();

            Stage popOutStage = new Stage();
            Scene scene = new Scene(root, 255, 100);
            popOutStage.setScene(scene);
            popOutStage.show();

        }


    }

    /**
     * This class initialize connection to databes to user which has access to only checking if login and password is correct.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        assert btnClient != null : "fx:id=\"btnClient\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert btnServiceUser != null : "fx:id=\"btnServiceUser\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert btnRegister != null : "fx:id=\"btnRegister\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert edtPassword != null : "fx:id=\"edtPassword\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert edtUser != null : "fx:id=\"edtUser\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert edtClient != null : "fx:id=\"edtClient\" was not injected: check your FXML file 'dbFX.fxml'.";
        assert tfPassClient != null : "fx:id=\"tfPassClient\" was not injected: check your FXML file 'dbFX.fxml'.";

        dbAccess = new DBUtil("access", "access");
        try {
            dbAccess.dbConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if provided login and password is in admin database.
     * @param login
     * @param password
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int logInAdmin(String login, String password) throws SQLException, ClassNotFoundException {

        int result = 0;

        StringBuilder call = new StringBuilder("call check_admin_user('");
        call.append(login);
        call.append("','");
        call.append(password);
        call.append("');");
        String callProcedure = call.toString();

        try {
            ResultSet resultSet = dbAccess.dbExecuteQuery(callProcedure);
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }
    }

    /**
     * Checks if provided login and password is in client database.
     * @param login
     * @param password
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int logInClient(String login, String password) throws SQLException, ClassNotFoundException {

        int result = 0;

        StringBuilder call = new StringBuilder("call check_client_user('");
        call.append(login);
        call.append("','");
        call.append(password);
        call.append("');");
        String callProcedure = call.toString();

        try {
            ResultSet resultSet = dbAccess.dbExecuteQuery(callProcedure);
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        }
    }

}
