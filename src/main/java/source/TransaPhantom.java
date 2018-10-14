package source;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class TransaPhantom {
    private static String userName="root";
    private static String password="1";
    private static String connectionURL="jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true&useUnicode=true";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParserConfigurationException, InterruptedException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            Statement statement = connection.createStatement();
            // statement.execute("DROP TABLE IF EXISTS BOOKS");
            //statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");
            ResultSet resultSet=statement.executeQuery("select count(*) from BOOKS");
            while(resultSet.next()){
                System.out.println(resultSet.getInt(1));
            }

            new OtherTransaction().start();
            Thread.sleep(2000);

            System.out.println("---------");
            ResultSet resultSet1=statement.executeQuery("select count(*) from BOOKS");
            while(resultSet1.next()){
                System.out.println(resultSet1.getInt(1));
            }
        }
    }

    static class OtherTransaction extends Thread{

        public void run(){
            try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                Statement statement = connection.createStatement();
                statement.execute("insert into BOOKS (name) values ('Wrong book')");

                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}