package ua.lpr.printservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

public class PrintParams {

    private String printerName;

    @JsonProperty("html")
    private String html;
    @JsonProperty("file")
    private File file;
    @JsonProperty("url")
    private URL url;
    @JsonProperty("bytes")
    private byte[] bytes;

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return "PrintParams{" +
                "printerName='" + printerName + '\'' +
                ", html='" + html + '\'' +
                ", file=" + file +
                ", url=" + url +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
