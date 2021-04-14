package ua.lpr.htmldoublesfinder.entity.site.elements.impl;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import ua.lpr.htmldoublesfinder.entity.site.elements.Image;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageImpl extends Element implements Image {

    public ImageImpl(Tag tag, String baseUri, Attributes attributes, String html) {
        super(tag, baseUri, attributes);
        this.html(html);
    }

    @Override
    public String getSource() {
        return this.attr("src");
    }

    @Override
    public String getTitle() {
        return this.attr("title");
    }

    @Override
    public String getAlternativeText() {
        return this.attr("alt");
    }

    @Override
    public byte[] getBytes() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            URL url = new URL(this.attr("src"));
            InputStream inputStream = url.openStream();
            int n = 0;
            byte [] buffer = new byte[ 1024 ];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toByteArray();
    }
}
