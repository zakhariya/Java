package ua.od.zakhariya.fx.fxml_gui.sub_views;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class S5Controller extends SubControllerSuper {

    @FXML
    private WebView webView;

    @FXML
    private TextField tfUrl;

    @FXML
    private Label lblStatus;

    private WebEngine webEngine;
    private WebHistory webHistory;

    @FXML
    void initialize() {
        webEngine = webView.getEngine();
        webEngine.javaScriptEnabledProperty().setValue(true);
        webEngine.setJavaScriptEnabled(true);

        webHistory = webEngine.getHistory();
//        webView.setZoom(1.25);

        loadPage("google.com");
    }

    public void loadPage() {
        loadPage(tfUrl.getText());
    }

    public void loadPage(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        webEngine.load(url);

//        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
//        int idx = webHistory.getCurrentIndex();
//        String u = entries.get(idx).getUrl();

        lblStatus.setText(webEngine.getLocation());
    }

    public void reloadPage() {
        webEngine.reload();
    }

    public void displayHistory() {
        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();

        for (WebHistory.Entry entry : entries) {
            System.out.println(entry);
        }
    }

    public void back() {
        webHistory.go(-1);
    }

    public void forward() {
        webHistory.go(1);
    }

    public void executeJS() {
        webEngine.executeScript("window.location = \"https://youtu.be\";");
    }
}
