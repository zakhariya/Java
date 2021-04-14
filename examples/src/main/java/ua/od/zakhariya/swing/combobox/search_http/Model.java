package ua.od.zakhariya.swing.combobox.search_http;

import javax.swing.*;
import java.util.List;

public class Model<T> extends DefaultComboBoxModel<T> {

    @Override
    public T getSelectedItem() {
        return (T) super.getSelectedItem();
    }

    public void addElements(List<T> elements) {
        elements.forEach(this::addElement);
    }

    public void clear() {
        removeAllElements();
    }
}
