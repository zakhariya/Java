package ua.od.zakhariya.files;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.nio.file.Files;

public class FileToBytes {

    public static void main(String[] args) throws IOException {
        File file = new File("d:\\test.jpg");

//		byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
        byte[] bytes = Files.readAllBytes(file.toPath());

        String base64Bytes = Base64.encodeBase64String(bytes);

        BufferedWriter writer = new BufferedWriter(new FileWriter("d://file_to_write_2.txt"));

        writer.write(base64Bytes);
        writer.close();

        System.out.println(base64Bytes);

        char pointLatin = '.';
        char pointCyr = '.';

        System.out.println((int) pointLatin);

        System.out.println((int) pointCyr);

        System.out.println(pointLatin == pointCyr);

//		for(byte b : bytes) {
//		    System.out.print(b);
//        }
    }
}