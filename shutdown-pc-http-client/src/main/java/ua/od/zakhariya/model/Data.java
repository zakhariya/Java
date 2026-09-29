package ua.od.zakhariya.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ua.od.zakhariya.controller.ComputerController;
import ua.od.zakhariya.entity.Computer;

import java.io.*;
import java.util.*;

public class Data {

    private static Data instance;

    private final ComputerContainer container = new ComputerContainer();
    private final ObjectMapper mapper = new ObjectMapper();
//    private final File file = new File("src/main/resources/computers.json");
//    private final URL url = Data.class.getResource("/computers.json");
//    private final File file = new File(url.getPath());
    private final String computersFilePath = "/computers.json";
    private final InputStream computersInputStream = getClass().getResourceAsStream(computersFilePath);
    private final String containerViewPath = "/ua/od/zakhariya/view/ComputerContainer.fxml";
//    private final InputStream containerInputStream = getClass().getResourceAsStream(containerViewPath);
    private Map<String, Computer> computerMap;

    private Data() {
        try {
            computerMap = mapper.readValue(computersInputStream, new TypeReference<Map<String, Computer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Data getInstance() {

        Data localInstance = instance;

        if (localInstance == null) {
            synchronized (Data.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Data();
                }
            }
        }

        return localInstance;
    }

    public Map<String, Computer> getComputerMap() {
        return computerMap;
    }

    //TODO: remove
    public List<Computer> getComputerList() {
        try {
            return mapper.readValue(computersInputStream, new TypeReference<List<Computer>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    //TODO: remove
    private void loadDefaultValues() {
        computerMap = new HashMap<>();

        computerMap.put("PC1", new Computer("PC1", "192.168.0.10", 3291, "shutdown?token=495"));
        computerMap.put("PC2", new Computer("PC2", "192.168.0.55", 2391, "shutdown?token=578"));
        computerMap.put("PC3", new Computer("PC3", "192.168.0.6", 9132, "shutdown?token=22"));
        computerMap.put("PC4", new Computer("PC4", "192.168.0.1", 3192, "shutdown?token=11"));

        saveData();
        container.update(computerMap);
    }

    public void save(Computer computer) {
        computerMap.put(computer.getName(), computer);
        container.add(computer);
        saveData();
//        container.update(computerMap);
    }

    public void deleteComputer(Computer computer) {
        computerMap.remove(computer.getName());
        container.remove(computer);
        saveData();
//        container.update(computerMap);
    }

    private void saveData() {
        try {
            OutputStream os = new FileOutputStream(computersFilePath);
//                    getClass().getResourceAsStream(file);

//            IOUtils.copy(is, os);
            mapper.writeValue(os, computerMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init(VBox vBox) {
        if (!container.isContainerInitialized()) {
            container.setContainerBox(vBox);
        }
        container.update(computerMap);
    }

    private class ComputerContainer {

        private VBox containerBox;
        private Timer timer;
        private TimerTask task;
        private final List<Thread> statusThreads = new LinkedList<>();
        private final List<Timeline> timelines = new LinkedList<>();

        private void setContainerBox(VBox containerBox) {
            this.containerBox = containerBox;
        }

        private void update() {
            Map<String, Computer> map  = Data.getInstance().getComputerMap();

            update(map);
        }

        private void update(Map<String, Computer> computerMap) {
            clearList();

            computerMap.forEach((key, computer) ->{
                try {
                    //Use (URL) for IDE
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ComputerContainer.fxml"));
                    loader.load();
                    Parent root = loader.getRoot();

//                    //Use (Stream) for Jar archive
//                    FXMLLoader loader = new FXMLLoader();
//                    InputStream is = getClass().getResourceAsStream(containerViewPath);
//                    Parent root = loader.load(is);

                    ComputerController computerController = loader.getController();
                    initContainer(computerController, computer);

                    containerBox.getChildren().add(root);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        public void initContainer(ComputerController controller, Computer computer) {
            controller.getLblName().setText(computer.getName());
//            controller.getLblHost().setText(computer.getHost());
//            controller.getLblPort().setText(String.valueOf(computer.getPort()));
//            controller.getLblUrl().setText(computer.getUrl());

            controller.getBtnTurnOff().setUserData(computer);
            controller.getBtnDelete().setUserData(computer);

            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(1),
                    event -> updateStatus(controller, computer)
            ));

            // The timeline should run indefinitely
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play(); // Start the clock

            timelines.add(timeline);

            //TODO: remove
            System.out.println("initContainer " + computer.getName() + " timeline: " + timeline.hashCode() + " thread: " + Thread.currentThread().getId());
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            Service service = new Service() {
//                @Override
//                protected Task createTask() {
//                    return new Task() {
//                        @Override
//                        protected Object call() throws Exception {
//                            Platform.runLater(() -> {
//                                while (true) {
//                                    updateStatus(controller, computer);
//                                }
//                            });
//                            return null;
//                        }
//                    };
//                }
//            };
//            service.start();




//            task = new TimerTask() {
//                @Override
//                public void run() {
//                    updateStatus(controller, computer);
//                }
//            };
//
//            timer = new Timer();
//            timer.scheduleAtFixedRate(task, 0, 1000);



//            Platform.runLater(() ->{
//                while (true) {
//                    updateStatus(controller, computer);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });



//            Thread thread = new Thread(() -> {
//
//            });
//
//            thread.start();
        }

        private void updateStatus(ComputerController controller, Computer computer) {
            boolean online = computer.isOnline();

            if (online) {
                controller.getLblStatus().setText("online");
            } else {
                controller.getLblStatus().setText("offline");
            }

            setStatusColor(controller, online);

            //TODO: remove
//            System.out.println(computer.getName() + " online = " + online + " thread: " + Thread.currentThread().getId());
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        private void setStatusColor(ComputerController controller, boolean online) {
            String color = online ? "green" : "red";

            controller.getLblName().setStyle("-fx-text-fill: " + color);
//            controller.getLblHost().setStyle("-fx-text-fill: " + color);
//            controller.getLblPort().setStyle("-fx-text-fill: " + color);
//            controller.getLblUrl().setStyle("-fx-text-fill: " + color);
            controller.getLblStatus().setStyle("-fx-text-fill: " + color);
        }

        private boolean isContainerInitialized() {
            return containerBox != null;
        }

        private void clearList() {
            for (Thread thread : statusThreads) {
                if (thread.isAlive())
                    thread.interrupt();
            }

            for (Timeline timeline : timelines) {
                if(timeline.getStatus() != Animation.Status.STOPPED) {
                    timeline.stop();
                }
            }

            timelines.clear();
            statusThreads.clear();
            containerBox.getChildren().clear();
        }

        public void add(Computer computer) {
        }

        public void remove(Computer computer) {

        }
    }
}
