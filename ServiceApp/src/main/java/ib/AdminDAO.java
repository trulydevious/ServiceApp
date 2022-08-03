package ib;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class AdminDAO {

    /**
     * AdminDAO is used to call basic operations for service worker.
     */

    private DBUtil dbUtil;

    public AdminDAO(DBUtil dbUtil) {
        this.dbUtil = dbUtil;
    }

    /**
     * This method converts ResultSet class to ObservableList class
     * @param rs
     * @return reclamationsList
     * @throws SQLException
     */
    private ObservableList<Reclamation> getReclamationsList(ResultSet rs) throws SQLException {
        ObservableList<Reclamation> reclamationsList = FXCollections.observableArrayList();
        while (rs.next()) {
            Reclamation r = new Reclamation();
            r.setId(rs.getInt(1));
            r.setPrice(rs.getDouble(2));
            r.setDate(rs.getString(3));
            r.setClientID(rs.getInt(4));
            r.setDeviceID(rs.getString(5));
            r.setStatus(rs.getString(6));
            reclamationsList.add(r);
        }
        return reclamationsList;
    }

    /**
     * This method is used to return ObservableList of all reclamations in Reclamations table in MySQL database.
     * @return reclamations
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ObservableList<Reclamation> showAllReclamations() throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM Reclamations;";
        try {
            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);
            ObservableList<Reclamation> reclamations = getReclamationsList(resultSet);
            return reclamations;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * This method is used to return ObservableList of reclamations from date to date in Reclamations table in MySQL database.
     * @param from
     * @param to
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ObservableList<Reclamation> showAllReclamations(LocalDate from, LocalDate to) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM Reclamations WHERE reclamation_date BETWEEN '" + from.toString() + "' AND '" + to.toString() + "';";
        try {
            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);
            ObservableList<Reclamation> reclamations = getReclamationsList(resultSet);
            return reclamations;
        } catch (SQLException e) {
            throw e;
        }
    }


    /**
     * This method is used to return ObservableList of reclamations from date to date and for provided status in Reclamations table in MySQL database.
     * @param from
     * @param to
     * @param status
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ObservableList<Reclamation> showAllReclamations(LocalDate from, LocalDate to, String status) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM Reclamations WHERE (reclamation_date BETWEEN '" + from.toString() + "' AND '" + to.toString() + "')" +
                " AND reclamation_status = '" + status + "';";
        try {
            ResultSet resultSet = dbUtil.dbExecuteQuery(selectStmt);
            ObservableList<Reclamation> reclamations = getReclamationsList(resultSet);
            return reclamations;
        } catch (SQLException e) {
            throw e;
        }
    }



    /**
     * This method is used to update status for reclamationID in Reclamations table in MySQL database.
     * @param reclamationID
     * @param status
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void updateReclamation(String reclamationID, String status) throws SQLException, ClassNotFoundException {
        String selectStmt = "UPDATE Reclamations SET reclamation_status = '" + status + "' WHERE id = " + reclamationID.toString() +  ";";
        try {
            dbUtil.dbExecuteUpdate(selectStmt);
        } catch (SQLException e) {
            throw e;
        }
    }
}

