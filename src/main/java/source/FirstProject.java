package source;


import java.io.IOException;
import java.sql.*;

public class FirstProject {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String userName="root";
        String password="1";
        String connectionURL="jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= DriverManager.getConnection(connectionURL,userName,password)){
            Statement statement=connection.createStatement();

            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");
            //   statement.executeUpdate("DELETE FROM BOOKS where name='Inferno' or name='Solomon key'");

            //Get Data
            ResultSet resultSet=statement.executeQuery("select * from BOOKS ");
            while(resultSet.next()){
                System.out.print(resultSet.getInt(1)+" ");
                System.out.println(resultSet.getString(2));
                System.out.println("-------");
            }
            System.out.println("For lines");
            while(resultSet.next()){
                System.out.print(resultSet.getInt("id")+" ");
                System.out.println(resultSet.getString("name"));
                System.out.println("-------");
            }

            ResultSet resultSet1=statement.executeQuery("select name from BOOKS where id=1");
            while(resultSet1.next()){
                System.out.println(resultSet1.getString("name"));
            }
        }
    }
}
