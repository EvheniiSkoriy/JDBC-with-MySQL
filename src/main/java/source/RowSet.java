package source;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class RowSet {

    private static String userName="root";
    private static String password="1";
    private static String connectionURL="jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true&useUnicode=true";

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, ParserConfigurationException {
        ResultSet resultSet=getData();
        while(resultSet.next()){
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
        }
        System.out.println("--------------------");
        CachedRowSet cachedRowSet=(CachedRowSet) resultSet;
        cachedRowSet.setUrl(connectionURL);
        cachedRowSet.setPassword(password);
        cachedRowSet.setUsername(userName);
        cachedRowSet.setCommand("select * from BOOKS where name = ?");
        cachedRowSet.setString(1,"Inferno");
        cachedRowSet.execute();
        while(cachedRowSet.next()){
            System.out.println(cachedRowSet.getInt(1));
            System.out.println(cachedRowSet.getString(2));
        }

        //Change data

        System.out.println("-----------------------");
        CachedRowSet cachedRowSet1=(CachedRowSet) resultSet;
        cachedRowSet1.setTableName("BOOKS");
        cachedRowSet1.last();
        cachedRowSet1.deleteRow();
        cachedRowSet1.beforeFirst();
        while(cachedRowSet1.next()) {
            System.out.println(cachedRowSet1.getInt(1));
            System.out.println(cachedRowSet1.getString(2));
        }
        //  cachedRowSet1.acceptChanges(DriverManager.getConnection(connectionURL,userName,password));
        updateData(cachedRowSet1,DriverManager.getConnection(connectionURL,userName,password));
    }
    public static void updateData(CachedRowSet crs,Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        crs.acceptChanges(connection);
    }

    public static ResultSet getData() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection= DriverManager.getConnection(connectionURL,userName,password)){
            Statement statement=connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");
            statement.executeUpdate("insert into BOOKS (name) values ('Inferno')");
            statement.executeUpdate("insert into BOOKS (name) values ('Solomon key')");
            statement.executeUpdate("insert into BOOKS (name) values ('Davinchi code')");

            RowSetFactory factory= RowSetProvider.newFactory();
            CachedRowSet cachedRowSet=factory.createCachedRowSet();
            Statement statement1=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet=statement1.executeQuery("SELECT * FROM BOOKS");
            cachedRowSet.populate(resultSet);
            return cachedRowSet;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

