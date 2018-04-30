package controllers;

import java.sql.*;

public class DBConnection {

    private static Connection myConnection = null;
    private static final String url = "jdbc:mysql://localhost:3306/AwesomeBankDB_simple?autoReconnect=true&useSSL=false";

    public DBConnection() throws SQLException {
        myConnection = DriverManager.getConnection(url, "student", "student");
    }


    public int getPassword(String first_name, String last_name) throws SQLException {
        int password=0;
        PreparedStatement sql = myConnection.prepareStatement(
                "SELECT Card.pin, Customer.first_name, Customer.last_name " +
                        "FROM Customer INNER JOIN Card " +
                        "ON Customer.card_id = Card.card_id " +
                        "WHERE Customer.first_name = ?" + // first name
                        " AND Customer.last_name = ?;");  // second name

        sql.setString(1, first_name);
        sql.setString(2, last_name);

        ResultSet myResult = sql.executeQuery();
        if (myResult.next()) {
            password = myResult.getInt("Card.pin");
        }
        return password;
    }

    public void close() throws SQLException {
        myConnection.close();
    }

    public void changePin(String first_name, String second_name, int newPin) throws SQLException {
        PreparedStatement sql = myConnection.prepareStatement(
                "UPDATE Card INNER JOIN Customer ON Customer.card_id = Card.card_id " +
                        " SET pin = ? WHERE Customer.first_name = ? AND Customer.last_name = ?;");

        sql.setInt(1,newPin);
        sql.setString(2,first_name);
        sql.setString(3,second_name);

        sql.executeUpdate();
    }


    public double getBalance(String first_name, String second_name) throws SQLException {
        double balance;
        PreparedStatement sql = myConnection.prepareStatement(
                "SELECT Account.balance " +
                        "FROM Account " +
                        "INNER JOIN Customer " +
                        "ON Customer.card_id = Account.card_id " +
                        "WHERE Customer.first_name = ? " +
                        "AND Customer.last_name = ?;");

        sql.setString(1,first_name);
        sql.setString(2,second_name);

        ResultSet myResult = sql.executeQuery();
        if (myResult.next()) {
            balance = myResult.getDouble("Account.balance");
            return balance;
        }
        return -1;
    }
    public void updateBalance(String first_name, String second_name, double amount) throws SQLException {
        PreparedStatement sql = myConnection.prepareStatement(
            "UPDATE Account INNER JOIN Customer " +
                    " ON Customer.card_id = Account.card_id " +
                    " SET balance = balance + ? " +
                    " WHERE Customer.first_name = ? " +
                    " AND Customer.last_name = ?;");

        sql.setDouble(1,amount);
        sql.setString(2,first_name);
        sql.setString(3,second_name);

        sql.executeUpdate();
    }
    public void updateBalance(int accountId, double amount) throws SQLException {
        PreparedStatement sql = myConnection.prepareStatement(
            "UPDATE Account  " +
                "SET balance = balance + ? " +
                "WHERE account_id = ?;");

        sql.setDouble(1,amount);
        sql.setInt(2,accountId);

        sql.executeUpdate();
    }




    private int insertCard(int newPin) throws SQLException {
        int cardId;
        PreparedStatement query = myConnection.prepareStatement(
                "INSERT INTO Card(card_id, creation_date, expiration_date, pin) " +
                        "VALUES(NULL, curdate(), date_add(curdate(), interval 10 year), ?);"
                , Statement.RETURN_GENERATED_KEYS);
        query.setInt(1,newPin);
        query.executeUpdate();

        ResultSet generatedKeys = query.getGeneratedKeys();

        if (generatedKeys.next()) {
            cardId = generatedKeys.getInt(1);
            return cardId;
        }
        return -1;
    }
    private void deleteCardById(int cardId) throws SQLException {
        PreparedStatement query = myConnection.prepareStatement(
                "DELETE FROM Card " +
                        "WHERE card_id = ? ");
        query.setInt(1,cardId);
        query.execute();
    }




    private int insertAccount(int cardId) throws SQLException {
        PreparedStatement query = myConnection.prepareStatement(
                "INSERT INTO Account(account_id, balance, card_id) " +
                        "VALUES(NULL,0,?);",
                Statement.RETURN_GENERATED_KEYS);
        query.setInt(1,cardId);

        query.executeUpdate();

        ResultSet generatedKeys = query.getGeneratedKeys();

        if (generatedKeys.next()) {
            int accountId = generatedKeys.getInt(1);
            return accountId;
        }
        return -1;
    }
    private void deleteAccountById(int accountId) throws SQLException {
        PreparedStatement query = myConnection.prepareStatement(
                "DELETE FROM Account " +
                        "WHERE account_id = ? ");
        query.setInt(1,accountId);
        query.execute();
    }
    public void deleteAccount(String first_name, String last_name) throws SQLException {
        int cardId = 0;
        int accId = 0;
        int customerId = 0;

        PreparedStatement getIds = myConnection.prepareStatement(
                "SELECT card_id, account_id, customer_id " +
                        "FROM Customer " +
                        "WHERE first_name = ? " +
                        "AND last_name = ?");
        getIds.setString(1, first_name);
        getIds.setString(2, last_name);

        ResultSet myResult = getIds.executeQuery();
        if (myResult.next()) {
            cardId = myResult.getInt("card_id");
            accId = myResult.getInt("account_id");
            customerId = myResult.getInt("customer_id");
        }


        System.out.println(cardId);
        deleteAccountById(accId);
        deleteCardById(cardId);
        deleteCustomerById(customerId);
    }
    public void createNewAccount(String first_name, String second_name, int newPin) throws SQLException {
        int cardId = insertCard(newPin);
        int accountId = insertAccount(cardId);
        insertCustomer(first_name, second_name, cardId, accountId);
    }
    public int getAccountId(String first_name, String last_name) throws SQLException {
        int accountId = 0;
        PreparedStatement query = myConnection.prepareStatement(
                "SELECT account_id " +
                        "FROM Customer " +
                        "WHERE first_name = ?" +
                        "AND last_name = ?"
        );
        query.setString(1, first_name);
        query.setString(2, last_name);
        ResultSet myResult = query.executeQuery();
        if (myResult.next()) {
            accountId = myResult.getInt("account_id");
            return accountId;
        }
        return accountId;
    }
    public String getAccountName(int accountId) throws SQLException {
        String r="";
        PreparedStatement query = myConnection.prepareStatement(
                "SELECT first_name, last_name " +
                        "FROM Customer " +
                        "WHERE account_id = ?"
        );
        query.setInt(1, accountId);
        ResultSet myResult = query.executeQuery();
        if (myResult.next()) {
            r = myResult.getString("first_name");
            r = r + " "+ myResult.getString("last_name");
            return r;
        }
        return r;
    }




    private int insertCustomer(String first_name, String second_name, int cardId, int accountId) throws SQLException {
        PreparedStatement query = myConnection.prepareStatement(
                "INSERT INTO Customer VALUES(NULL,?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS);

        query.setString(1,first_name);
        query.setString(2,second_name);
        query.setInt(3,cardId);
        query.setInt(4,accountId);

        query.executeUpdate();

        ResultSet generatedKeys = query.getGeneratedKeys();

        if (generatedKeys.next()) {
            int customerId = generatedKeys.getInt(1);
            return customerId;
        }
        return -1;
    }
    private void deleteCustomerById(int customerId) throws SQLException {
        PreparedStatement query = myConnection.prepareStatement(
                "DELETE FROM Customer " +
                        "WHERE customer_id = ?");
        query.setInt(1,customerId);
        query.execute();
    }





    public void newTransaction(double amount, int inputAcc, int outputAcc) throws SQLException {
        PreparedStatement query = myConnection.prepareStatement(
                "INSERT INTO Transaction " +
                    "VALUES (NULL,NOW(),?,?,?,NULL);");
        query.setDouble(1, amount);
        query.setInt(2, inputAcc);
        query.setInt(3, outputAcc);
        query.execute();
    }
    public ResultSet getTransaction(String first_name, String last_name) throws SQLException {
        int accountId = getAccountId(first_name, last_name);

        PreparedStatement query = myConnection.prepareStatement(
                "SELECT date, amount, input_acc, output_acc " +
                        "FROM Transaction " +
                        "WHERE output_acc = ? " +
                        "OR input_acc = ?  " +
                        "ORDER BY transaction_id DESC " +
                        "limit 5;");  // second name

        query.setInt(1, accountId);
        query.setInt(2, accountId);

        ResultSet myResult = query.executeQuery();
        return myResult;
    }

}
