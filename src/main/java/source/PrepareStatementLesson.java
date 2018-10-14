package source;

import java.io.IOException;
import java.sql.*;

public class PrepareStatementLesson {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String userName = "root";
        String password = "1";
        String connectionURL = "jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");

            //preparedStatement   For correct input ID
            String userIdCorrect = "1";
            String userIdIncorrect = "1.w.4w";
            PreparedStatement preparedStatement = connection.prepareStatement("select * from BOOKS where id= ? ");
            preparedStatement.setString(1, userIdIncorrect);
            ResultSet resultSet2 = preparedStatement.executeQuery();
            while (resultSet2.next()) {
                System.out.print(resultSet2.getInt("id") + " ");
                System.out.println(resultSet2.getString("name"));
            }
        }
    }
}
