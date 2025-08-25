package ua.od.zakhariya.files;

import java.io.File;

public class CompareTwoFoldersByFilesInclude {

    private static final String FOLDER_1 = "\\\\Server\\e$\\dir1";
    private static final String FOLDER_2 = "\\\\Server\\e$\\dir2";
    private static final String FILE_EXTENSION = "php";

    private static int count = 0;

//	private String file1 = "";
//	private String file2 = "";

    private void inspectFolder(File file, String parentDir){

        if(file.isDirectory()){
            String dir = "";
            File[] fileList = file.listFiles();
            long objCount = fileList.length;

            if (file.getAbsolutePath().equalsIgnoreCase(FOLDER_1))
                dir = "";
            else
                dir = parentDir + "\\" + file.getName();

//            System.out.println(FOLDER_1 + dir + ": " + objCount + " объектов");
//            System.out.println(file.getAbsolutePath() + ": " + objCount + " объектов");
            //System.out.println(dir);

            for(int i=0; i<objCount; i++)
                inspectFolder(fileList[i], dir);
        }else{
            if (file != null && file.exists() && file.isFile()
                    && getFileExtension(file).equalsIgnoreCase(FILE_EXTENSION)) {

                File compFile = null;

                if (file.getAbsolutePath().equalsIgnoreCase(FOLDER_1))
                    compFile = new File(FOLDER_2 + "\\" + parentDir + "\\" + file.getName());
                else
                    compFile = new File(FOLDER_2 + parentDir + "\\" + file.getName());

                if (compFile.exists() && !compareFileSize(file, compFile)) {
                    count++;
                    System.out.println(file.getAbsolutePath());
                }
            }

        }
    }

    private boolean compareFileSize(File file1, File file2) {
//		double bytes = file.length();
//		double kilobytes = (bytes / 1024);
//		double megabytes = (kilobytes / 1024);
//		double gigabytes = (megabytes / 1024);
//		double terabytes = (gigabytes / 1024);
//		double petabytes = (terabytes / 1024);
//		double exabytes = (petabytes / 1024);
//		double zettabytes = (exabytes / 1024);
//		double yottabytes = (zettabytes / 1024);

        if (file1.length() == file2.length())
            return true;
        else
            return false;
    }

    private static String getFileExtension(File file) {
        String extension = "";

        try {
            String name = file.getName();
            extension = name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            extension = "";
        }

        return extension;
    }

    public static void main(String[] args) {
        new CompareTwoFoldersByFilesInclude().inspectFolder(new File(FOLDER_1), "");
        System.out.println("Total: " + count);
    }

}