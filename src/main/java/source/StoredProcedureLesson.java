package source;

import java.io.IOException;
import java.sql.*;

public class StoredProcedureLesson {
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

            CallableStatement callableStatement=connection.prepareCall("{call BookCounts(?)}");
            callableStatement.registerOutParameter(1,Types.INTEGER);
            callableStatement.execute();
            System.out.println(callableStatement.getInt(1));
            System.out.println("--------------");

            CallableStatement callableStatement1=connection.prepareCall("{call getBooks(?)}");
            callableStatement1.setInt(1,1);
            if (callableStatement1.execute()){
                ResultSet resultSet=callableStatement1.getResultSet();
                while (resultSet.next()){
                    System.out.println(resultSet.getInt(1));
                    System.out.println(resultSet.getString(2));
                }
            }
        }
    }
}
