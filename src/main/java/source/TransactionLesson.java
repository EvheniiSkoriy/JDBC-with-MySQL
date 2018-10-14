package source;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class TransactionLesson {
    private static String userName="root";
    private static String password="1";
    private static String connectionURL="jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true&useUnicode=true";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParserConfigurationException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");

            statement.executeUpdate("insert into BOOKS (name) values ('Inferno')");
            Savepoint savepoint=connection.setSavepoint();
            statement.executeUpdate("insert into BOOKS (name) values ('Solomon key')");
            statement.executeUpdate("insert into BOOKS (name) values ('Davinchi code')");

            connection.rollback(savepoint); //to cancel all operations to save point
            connection.commit(); //execute all operation before save point
            connection.releaseSavepoint(savepoint); //delete save point
        }
    }
}