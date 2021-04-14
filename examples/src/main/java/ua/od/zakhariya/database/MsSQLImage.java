package ua.od.zakhariya.database;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

public class MsSQLImage {

    public static void main(String[] args) {
        new MsSQLImage();

        DB db = new DB();

        try {
            Connection conn = db.getConnection("jdbc:sqlserver://192.168.0.55:1433; databaseName=База_МК; integratedSecurity=true;", "", "");

            //db.insertImage(conn,"D://Windows 7 SP1 x86-x64 by g0dl1ke 16.10.15.iso");

            saveToFile(db.getImageData(conn));

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    private static void saveToFile(ArrayList<byte[]> imagesInBytes) throws IOException {

        int i = 1;

        for(byte[] bytes : imagesInBytes) {

            ArrayList<Object> ы = new ArrayList<>();

            for(byte b: bytes) {
                ы.add(b);
            }

            System.out.println(ы.size());
            //System.out.println(ы.toString());

            //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("E://file_"+i+".txt"));
            //bos.

            Files.write(Paths.get("E://file_"+i+".txt"), toHexString(bytes).getBytes());

            OutputStream targetFile=new FileOutputStream("E://file_"+i+".jpg");
            targetFile.write(bytes);
            targetFile.close();

            i++;
        }

        System.out.println("Saved to file");
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

}

class DB{

    private static int userId = 26;

    public Connection getConnection(String url, String user, String password)
            throws ClassNotFoundException, SQLException {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println("connected");

        return conn;
    }

    public void insertImage(Connection conn, String img) {
        int len;
        String query;
        PreparedStatement pstmt;

        try {
            File file = new File(img);
            FileInputStream fis = new FileInputStream(file);
            len = (int)file.length();

            query = ("INSERT INTO TableImage VALUES(?,?,?)");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,file.getName());
            pstmt.setInt(2, len);

            // Method used to insert a stream of bytes
            pstmt.setBinaryStream(3, fis, len);
            pstmt.executeUpdate();

            System.out.println("image insrted");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<byte[]> getImageData(Connection conn) throws SQLException, IOException {

        ArrayList<byte[]> imagesInBytes = new ArrayList<>();

        String query = "select PhotoPic from tblUsers WHERE ID=?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
            imagesInBytes.add(rs.getBytes("PhotoPic"));

        return imagesInBytes;
    }
}