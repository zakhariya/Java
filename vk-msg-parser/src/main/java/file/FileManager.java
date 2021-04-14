package file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FileManager {
    private final AtomicInteger filesCount = new AtomicInteger(0);
    private final AtomicInteger handledFilesCount = new AtomicInteger(0);
    private BlockingQueue queue = new BlockingQueue();

    public FileManager() {

        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Runnable task = queue.get();
                    task.run();
                }
            }
        });

        worker.setDaemon(true);
        worker.start();
    }

    public void findAndHandleFiles(File dir) {
        filesCount.set(0);
        handledFilesCount.set(0);

        findHtmlAndAddTasks(dir);
        System.out.printf("Файлов найдено %d%n", filesCount.incrementAndGet());
    }

    private void findHtmlAndAddTasks(File file){
        if(file.isDirectory()){
            File[] fileList = file.listFiles();
            int objCount = fileList.length;

            for(int i = 0; i < objCount; i++) {
                findHtmlAndAddTasks(fileList[i]);
            }
        } else if (isHtmlFile(file)) {
            try {
                System.out.printf("Файлов найдено %d", filesCount.incrementAndGet());
                Thread.sleep(5);
                System.out.print("\r");

                queue.put(getTask(file));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isHtmlFile(File file) {
        String extension = "";

        int idx = file.getName().lastIndexOf('.');

        if (idx > 0) {
            extension = file.getName().substring(idx + 1);
        }

        return file.isFile() && extension.equalsIgnoreCase("html");
    }

    private Runnable getTask(final File file) {
        return new Runnable() {
            @Override
            public void run() {
                // create new html doc, get group list & write to file
//                System.out.println(this);
                System.out.println(handledFilesCount.incrementAndGet());
            }
        };
    }

    private class BlockingQueue {
        List<Runnable> tasks = new ArrayList<>();

        public synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }

            return tasks.remove(0);
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notifyAll();
        }
    }
}
