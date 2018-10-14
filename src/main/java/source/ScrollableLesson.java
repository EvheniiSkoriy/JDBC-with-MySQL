package source;

import java.io.IOException;
import java.sql.*;

public class ScrollableLesson {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String userName="root";
        String password="1";
        String connectionURL="jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= DriverManager.getConnection(connectionURL,userName,password)){
            Statement statement=connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");
            statement.executeUpdate("insert into BOOKS (name) values ('Inferno')");
            statement.executeUpdate("insert into BOOKS (name) values ('Solomon key')");

            Statement statement1=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet=statement1.executeQuery("select * from BOOKS");
            if(resultSet.next())
                System.out.println(resultSet.getString("name"));
            if(resultSet.next())//next element
                System.out.println(resultSet.getString("name"));
            if(resultSet.previous())// previous element
                System.out.println(resultSet.getString("name"));
            if(resultSet.relative(2))// jump in 2 elements
                System.out.println(resultSet.getString("name"));
            if(resultSet.relative(-2))// jump back 2 elements
                System.out.println(resultSet.getString("name"));
            if(resultSet.first())// first element
                System.out.println(resultSet.getString("name"));
            if(resultSet.last())// last element
                System.out.println(resultSet.getString("name"));
        }
    }
}
