package appka;

import java.sql.*;

public class DBconnection {

    private static Connection myConnection = null;
    private static final String url = "jdbc:mysql://localhost:3306/AwesomeBankDB_simple?autoReconnect=true&useSSL=false";
    private String account = "student";
    private String password = "student";


     public DBconnection() throws SQLException {
         myConnection = DriverManager.getConnection(url,account,password);
     }

     public void close() throws SQLException {
         myConnection.close();
     }

     public String getAccount(String first_name) throws SQLException {
         String jmeno = "";
         String prijmeni = "";
         PreparedStatement query = myConnection.prepareStatement(
                 "SELECT first_name, last_name, customer_id " +
                     "FROM Customer " +
                     "WHERE first_name = ?;");
         query.setString(1,first_name);

         ResultSet rs = query.executeQuery();
         while (rs.next()) {
             jmeno = rs.getString("first_name");
             prijmeni = rs.getString("last_name");
             String customer = rs.getString("customer_id");
             System.out.println(jmeno+" "+prijmeni);
         }
         return jmeno;
     }
}
