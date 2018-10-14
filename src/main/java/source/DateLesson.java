package source;

import java.io.IOException;
import java.sql.*;

public class DateLesson {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String userName="root";
        String password="1";
        String connectionURL="jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= DriverManager.getConnection(connectionURL,userName,password)){
            Statement statement=connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");

            PreparedStatement preparedStatement3=connection.prepareStatement("insert into BOOKS(name,img,dt) values ('someName',null ,?)");
            preparedStatement3.setDate(1,new Date(1533902303123L));
            preparedStatement3.execute();
            System.out.println(preparedStatement3);

            ResultSet resultSet=preparedStatement3.executeQuery("SELECT * FROM BOOKS");
            while (resultSet.next()){
                System.out.println(resultSet.getDate("dt"));
            }
        }
    }
}