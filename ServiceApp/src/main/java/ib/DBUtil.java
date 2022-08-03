package ib;
import com.github.vldrus.sql.rowset.CachedRowSetWrapper;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;


public class DBUtil {

    /**
     * This class is used to connect to database with provided login to access privileges assigned to that user.
     * User is created in MySQL Server.
     */

    private String userName;
    private String userPassword;

    private Connection conn = null;

    /**
     * Constructor of dbUtil class.
     * @param userName
     * @param userPassword
     */
    public DBUtil(String userName, String userPassword) {
         this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * This class is used to connect fo database with user data of this class.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void dbConnect() throws SQLException, ClassNotFoundException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        try {
            conn = DriverManager.getConnection(createURL());
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * This method is used to disconnect from database.
     * @throws SQLException
     */
    public void dbDisconnect() throws SQLException {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * This method is used to create String data needed to connect with databese by using JDBC.
     * @return
     */
    private String createURL() {
        StringBuilder urlSB = new StringBuilder("jdbc:mysql://");
        urlSB.append("localhost:3306/");
        urlSB.append("service?");
        urlSB.append("useUnicode=true&characterEncoding=utf-8");
        urlSB.append("&user=");
        urlSB.append(userName);
        urlSB.append("&password=");
        urlSB.append(userPassword);
        urlSB.append("&serverTimezone=CET");
        return urlSB.toString();
    }

    /**
     * This method executes MySQL query provided as param String.
     * @param queryStmt
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {

        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CachedRowSet crs;
        try {
            dbConnect();
            stmt = conn.prepareStatement(queryStmt);
            resultSet = stmt.executeQuery(queryStmt);
            crs = new CachedRowSetWrapper();
            crs.populate(resultSet);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
        return crs;
    }


    /**
     * This method executes MySQL updates data by using String as param.
     * @param queryStmt
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public  void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        Statement stmt = null;
        try {
            dbConnect();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            dbDisconnect();
        }
    }

}
