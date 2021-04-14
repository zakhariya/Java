package ua.od.zakhariya.files;


import java.io.File;

public class FilesAndDirsToLower {

    private static File dest = new File("C:\\Users\\Саня Z\\Desktop\\lpr.ua\\wp-content\\uploads");
    private static long total;

    public static void main(String[] args) {

        if (!dest.isDirectory()) {
            System.out.println("Not a directory");

            return;
        }

        inspectFolder(dest);

        System.out.println("Done " + total);
    }

    private static void inspectFolder(File file){
        if(file.isDirectory()){
            File[] fileList = file.listFiles();
            long objCount = fileList.length;

            for(int i = 0; i < objCount; i++) {
                inspectFolder(fileList[i]);
            }
        }

        if (hasUpperChars(file.getName())) {
            renameToLower(file);
        }
    }

    private static boolean hasUpperChars(String name) {
        return !name.equals(name.toLowerCase());
    }

    private static void renameToLower(File file) {
        String absolute = file.getAbsolutePath();
        String name = file.getName();
        String path = absolute.substring(0, absolute.indexOf(name));

        System.out.println(absolute);
        file.renameTo(new File(path + name.toLowerCase()));
        total++;
    }

}

