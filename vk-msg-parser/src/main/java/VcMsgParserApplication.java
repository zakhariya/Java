import file.FileManager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VcMsgParserApplication {

    public static void main(String[] args) {

//        File f = new File(System.getProperty("user.dir"), "results");
//        Files.createDirectory(f.toPath());

        VcMsgParserApplication app = new VcMsgParserApplication();
        app.start();

        String filePath = "C:\\Users\\User\\Desktop\\DownloadDialogs\\792524548 egre\\id297526756_Бодя Мерцалов\\Бодя Мерцалов.html";
        String dirPath = "C:\\Users\\User\\Desktop\\DownloadDialogs";


//        try {
//            HtmlDocument htmlDocument = new HtmlDocument(new File(filePath));
//
//            List<String> groupList = htmlDocument.getGroupList();
//
//            for (String group : groupList) {
//                System.out.println(group);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void start() {
//        try {
            System.out.println("Введите путь к папке (напр. C:\\DownloadDialogs), или exit для выхода:");
            //TODO: remove
            String line = "C:\\Users\\User\\Desktop\\DownloadDialogs";
//            String line = new BufferedReader(new InputStreamReader(System.in, "windows-1251")).readLine();
            File dir = new File(line);

            if (line.equalsIgnoreCase("exit")) {
                System.exit(0);
            } else if (isDirectoryCorrect(dir)) {
                FileManager fileManager = new FileManager();
                fileManager.findAndHandleFiles(dir);
            }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        start();
    }

    private boolean isDirectoryCorrect(File dir) {
        if (!dir.isDirectory() || dir.listFiles().length < 1) {
            System.out.println("Указанный каталог пуст, или не существует");

            return false;
        }

        return true;
    }
}