import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ib.AdminDAO;
import ib.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

public class DbFXStats {
    public DBUtil dbAccess;
    private AdminDAO adminDAO;
    int reported;
    int finished;
    int inProgress;
    int denied;
    int clients;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text tfAllCount;

    @FXML
    private Text tfClientCount;

    @FXML
    private Text tfDeniedCount;

    @FXML
    private Text tfFinishedCount;

    @FXML
    private Text tfInProgressCount;

    @FXML
    private Text tfReportedCount;

    @FXML
    private PieChart chartPie;

    /**
     * This method initialize all of actions preformed before showing stage window - it is:
     * - connects to database by admin user
     * - counts all types of reclamations
     * - creates a PieChart
     */
    @FXML
    void initialize() {
        assert chartPie != null : "fx:id=\"chartPie\" was not injected: check your FXML file 'dbFXStats.fxml'.";
        assert tfAllCount != null : "fx:id=\"tfAllCount\" was not injected: check your FXML file 'dbFXStats.fxml'.";
        assert tfClientCount != null : "fx:id=\"tfClientCount\" was not injected: check your FXML file 'dbFXStats.fxml'.";
        assert tfDeniedCount != null : "fx:id=\"tfDeniedCount\" was not injected: check your FXML file 'dbFXStats.fxml'.";
        assert tfFinishedCount != null : "fx:id=\"tfFinishedCount\" was not injected: check your FXML file 'dbFXStats.fxml'.";
        assert tfInProgressCount != null : "fx:id=\"tfInProgressCount\" was not injected: check your FXML file 'dbFXStats.fxml'.";
        assert tfReportedCount != null : "fx:id=\"tfReportedCount\" was not injected: check your FXML file 'dbFXStats.fxml'.";

        dbAccess = new DBUtil("admin", "admin");
        try {
            dbAccess.dbConnect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //laczna liczba klientow
        ResultSet clientCount = null;
        try {
            clientCount = dbAccess.dbExecuteQuery("select count(id) from Clients;");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<?> data = convertResultSet(clientCount);
            clients = (int) data.get(0);
            tfClientCount.setText(data.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }



        //Liczba reported
        ResultSet reportedCount = null;
        try {
            reportedCount = dbAccess.dbExecuteQuery("select count(id) from Reclamations where reclamation_status like '%reported%';");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<?> data = convertResultSet(reportedCount);
            reported = (int) data.get(0);
            tfReportedCount.setText(data.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Liczba denied
        ResultSet deniedCount = null;
        try {
            deniedCount = dbAccess.dbExecuteQuery("select count(id) from Reclamations where reclamation_status like '%denied%';");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<?> data = convertResultSet(deniedCount);
            denied = (int) data.get(0);
            tfDeniedCount.setText(data.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Liczba In Progress
        ResultSet inProgressCount = null;
        try {
            inProgressCount = dbAccess.dbExecuteQuery("select count(id) from Reclamations where reclamation_status like '%In progress%';");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<?> data = convertResultSet(inProgressCount);
            inProgress = (int) data.get(0);
            tfInProgressCount.setText(data.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Liczba Finished
        ResultSet finishedCount = null;
        try {
            finishedCount = dbAccess.dbExecuteQuery("select count(id) from Reclamations where reclamation_status like '%finished%';");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<?> data = convertResultSet(finishedCount);
            finished = (int) data.get(0);
            tfFinishedCount.setText(data.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Wszystkie
        int all = reported + finished + inProgress + denied;
        tfAllCount.setText(String.valueOf(all));


        PieChart.Data slice1 = new PieChart.Data("Reported", reported);
        PieChart.Data slice2 = new PieChart.Data("Denied"  , denied);
        PieChart.Data slice3 = new PieChart.Data("In progress" , inProgress);
        PieChart.Data slice4 = new PieChart.Data("Finished" , finished);

        chartPie.getData().add(slice1);
        chartPie.getData().add(slice2);
        chartPie.getData().add(slice3);
        chartPie.getData().add(slice4);

    }

    /**
     * This method converts ResultSet to ArrayList.
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static ArrayList<Integer> convertResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        ArrayList<Integer> data = new ArrayList<>();
        int columnCount = rsmd.getColumnCount();
        while (resultSet.next()) {

            for (int i = 1; i <= columnCount; i++) {
                String colVal = resultSet.getString(i);
//                System.out.print(colVal + "\t");
                data.add(Integer.parseInt(colVal));
            }
        }
        return data;
    }

}
