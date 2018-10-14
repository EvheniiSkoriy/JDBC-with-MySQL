package source;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class AddPicture {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        String userName = "root";
        String password = "1";
        String connectionURL = "jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password)) {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS BOOKS");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS BOOKS (id MEDIUMINT Not null Auto_increment,name CHAR (30),img BLOB,dt DATE ,PRIMARY KEY (id))");

            // insert Image
            BufferedImage bufferedImage= ImageIO.read(new File("1.jpg"));
            Blob blob=connection.createBlob();
            try(OutputStream out=blob.setBinaryStream(1);){
                ImageIO.write(bufferedImage,"jpg",out);
            }
            PreparedStatement preparedStatement1=connection.prepareStatement("insert into BOOKS (name, img) values (?,?)");
            preparedStatement1.setString(1,"Inferno");
            preparedStatement1.setBlob(2,blob);
            preparedStatement1.execute();

            ResultSet resultSet3=preparedStatement1.executeQuery("SELECT *FROM BOOKS");
            while(resultSet3.next()){
                if(!resultSet3.getBlob("img").equals(null)){
                    Blob blob1=resultSet3.getBlob("img");
                    BufferedImage bf=ImageIO.read(blob1.getBinaryStream());
                    File outPut=new File("outputImage.png");
                    ImageIO.write(bf,"png",outPut);
                }
            }
        }
    }
}