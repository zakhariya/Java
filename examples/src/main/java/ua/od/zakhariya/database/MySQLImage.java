package ua.od.zakhariya.database;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class MySQLImage {

    private static int userId = 27;

    public static void main(String[] args) throws IOException, SQLException {
        new MySQLImage();

        //saveToDB(new File("E://viber image.jpg")); // пишем в БД setBinaryStream передавая FileInputStream

        //saveToFileFromBlob(getBlobData(), new File("E://file.jpg")); // пишем в файл получив данные в виде Blob из БД (работает только с родными данными)

        //saveToFileFromBytesArrays(getImageBytesArrays()); // пишем в файл получив данные в виде byte[] из БД (работает только с родными данными)

        //saveToFileFromInputStream(getImgInpitStream()); // пишем в файл получив InputStream (только для MySQL)

        saveToFileFromBytes(getBytesFromHex(getHexString())); // работает  //.substring(2)));  // обрезаем 0x

        //	saveToFileFromHex();  // работает

        System.out.println("Done!");

        System.exit(0);
    }

    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }



    /**
     * записывает фото в БД в понятном для мускула формате
     *
     * метод работает с СУБД MsSQL и MySQL
     *
     * @param f - файл для чтения
     */
    private static void saveToDB(File f) {
        try (FileInputStream fis = new FileInputStream(f)) {
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://192.168.0.55:3306/lp-system", "root", "root");

            PreparedStatement ps = conn.prepareStatement("UPDATE tblUsers SET PhotoPic =? WHERE ID=?");

            ps.setBinaryStream(1, fis); //ps.setAsciiStream(1, fis);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * получаем данные в виде Blob (понятные для мускула) из БД
     *
     * @return Blob value from DB
     */
    private static Blob getBlobData() {

        Blob blobImage = null;

        try {
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://192.168.0.55:3306/lp-system", "root", "root");

            PreparedStatement ps = conn.prepareStatement("SELECT PhotoPic FROM tblUsers WHERE ID=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                blobImage = rs.getBlob("PhotoPic");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return blobImage;
    }

    /**
     * пишем в файл полученные данные (понятные для мускула) из БД
     *
     * @param blob
     * @param file
     */
    private static void saveToFileFromBlob(Blob blob, File file) {

        try (FileOutputStream fos = new FileOutputStream(file)) {
            InputStream is = blob.getBinaryStream();
            byte[] buffer = new byte[1024 * 4];
            while (is.read(buffer) > 0) {
                fos.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * получаем данные в виде массива данных byte[] (понятные для мускула) из БД
     *
     * метод может работать с СУБД MsSQL и MySQL, если данные занесены в понятном для СУБД формате
     *
     * @return ArrayList<byte[]>
     * @throws SQLException
     * @throws IOException
     */
    private static ArrayList<byte[]> getImageBytesArrays() throws SQLException, IOException {

        ArrayList<byte[]> imagesInBytes = new ArrayList<>();

        String query = "select PhotoPic from tblUsers WHERE ID=?";

        Connection conn =
                DriverManager.getConnection("jdbc:mysql://192.168.0.55:3306/lp-system", "root", "root");
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            imagesInBytes.add(rs.getBytes("PhotoPic"));
        }

        return imagesInBytes;
    }

    /**
     * записываем в файлы полученные byte[]-массивы
     *
     * метод может работать с СУБД MsSQL и MySQL, если данные занесены в понятном для СУБД формате
     *
     * @param imagesInBytes - ArrayList<byte[]>
     * @throws IOException
     */
    private static void saveToFileFromBytesArrays(ArrayList<byte[]> imagesInBytes) throws IOException {
        int i = 1;

        for(byte[] bytes : imagesInBytes) {

            ArrayList<Object> ы = new ArrayList<>();

            for(byte b: bytes) {
                ы.add(b);
            }

            System.out.println(ы.size());
            //System.out.println(ы.toString());

            OutputStream targetFile=new FileOutputStream("E://file_"+i+".jpg");
            targetFile.write(bytes);
            targetFile.close();

            i++;
        }
    }

    /**
     * получаем данные в виде массива данных InputStream (понятные для мускула) из БД
     *
     * @return InputStream
     */
    private static InputStream getImgInpitStream() {

        InputStream inputStreamImage = null;

        try {
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://192.168.0.55:3306/lp-system", "root", "root");

            PreparedStatement ps = conn.prepareStatement("SELECT PhotoPic FROM tblUsers WHERE ID=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                inputStreamImage = rs.getBinaryStream("PhotoPic");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inputStreamImage;
    }

    /**
     * записываем в файлы полученные данные изи InputStream
     *
     * @param is
     * @throws IOException
     */
    private static void saveToFileFromInputStream(InputStream is) throws IOException {
        System.out.println(is);
        OutputStream targetFile = new FileOutputStream("E://file.jpg");

        byte[] buf = new byte[1024];
        int len;
        while((len = is.read(buf))>0){
            targetFile.write(buf, 0, len);
            System.out.println(len);
        }

        targetFile.close();
    }



    // ******************* НЕ ПРОВЕРЕНО ******************************** \\


    private static String getHexString() {
        String hex = null;

        try {
            Connection conn =
                    DriverManager.getConnection("jdbc:mysql://192.168.0.55:3306/lp-system", "root", "root");

            PreparedStatement ps = conn.prepareStatement("SELECT PhotoPic FROM tblUsers WHERE ID=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                hex = rs.getString("PhotoPic");
            }

            System.out.println(hex);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hex;
    }

    private static byte[] getBytesFromHex(String hexString) {
        int length = hexString.length();

        byte[] data = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }

        return data;
    }

    private static void saveToFileFromBytes(byte[] bytes) throws IOException {

        ArrayList<Object> ы = new ArrayList<>();

        for(byte b: bytes) {
            ы.add(b);
        }

        System.out.println(ы.size());
        //System.out.println(ы.toString());

        OutputStream targetFile=new FileOutputStream("E://file.jpg");
        targetFile.write(bytes);
        targetFile.close();
    }


    private static void saveToFileFromHex() {

        String hexData = getHexString(); //.substring(2);
        //hexData = hexData.substring(0, hexData.length() - 5);

        System.out.println(hexData);

        String photo = hexData;

        int l = photo.length();

        int i = 0;

        try (FileOutputStream fos = new FileOutputStream("E:/file.jpg")) {
            ArrayList<Object> ы = new ArrayList<>();

            while(i < l) {
                int bytee = 0;

                if( (i+1) >= l ) {
                    break; //bytee = Character.digit(photo.charAt(i), 16) * 16;
                }else {
                    bytee = Character.digit(photo.charAt(i), 16) * 16 +
                            Character.digit(photo.charAt(i + 1), 16);

                    fos.write(bytee);
                    ы.add(bytee);
                }

                i += 2;
            }

            System.out.println(ы.size());
            //System.out.println(ы.toString());

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

/*
		char[] hex = hexData.toCharArray();

        String line = "";
        String line_final = "";
        try {
            String sCurrentLine;
            BufferedReader br = new BufferedReader(new FileReader("E:\\file.txt"));//test.txt hex code string
            DataOutputStream os = new DataOutputStream(new FileOutputStream("E:\\file.jpg"));
            while ((sCurrentLine = br.readLine()) != null) {
                line = StringUtils.deleteWhitespace(sCurrentLine);
                byte[] temp = convertHexadecimal2Binary(line.getBytes(), hexData);
                os.write(temp);
            }
            os.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
 */

/*
		BigInteger hex = new BigInteger(hexData.substring(2), 16);
		byte[] byte_data = hex.toByteArray();

		ByteArrayInputStream input = new ByteArrayInputStream(byte_data);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(new File("E://file.jpg"));

			byte[] buffer = new byte[1024 * 4];
			int n = 0;

			while (-1 != (n = input.read(buffer))) {
			    output.write(buffer, 0, n);
			}
			output.close();
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
/*
		byte[] b = hex2bin(hex.substring(2)); //Trim the "0x" first.

		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E://file.jpg")));
			bos.write(b);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
    }

    private static byte[] convertHexadecimal2Binary(byte[] hex, String hexData) {
        int block = 0;
        byte[] bytes = new byte[hex.length / 2];
        int index = 0;
        boolean next = false;
        for (int i = 0; i < hex.length; i++) {
            block <<= 4;
            int pos = hexData.indexOf(Character.toUpperCase((char) hex[i]));
            if (pos > -1) {
                block += pos;
            }
            if (next) {
                bytes[index] = (byte) (block & 0xff);
                index++;
                next = false;
            } else {
                next = true;
            }
        }

        return bytes;
    }

    public static byte[] hex2bin(String hex) {

        if ((hex.length() % 2) != 0) throw new IllegalArgumentException();
        int len = hex.length() / 2;
        byte[] out = new byte[len];
        for (int i = 0; i < len; i++) {
            out[i] = (byte)(
                    (Character.digit(hex.charAt(i * 2 + 0), 16) << 4) |
                            (Character.digit(hex.charAt(i * 2 + 1), 16))
            );
        }
        return out;
    }
}