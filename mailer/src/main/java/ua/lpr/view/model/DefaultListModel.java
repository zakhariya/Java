package ua.lpr.view.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DefaultListModel<T> extends AbstractListModel {

    protected List<T> entities = new ArrayList<T>();

    @Override
    public int getSize() {
        return entities.size();
    }

    public void addEntity(T entity) {
        entities.add(0, entity);
        //fireTableDataChanged();
    }

    public void addEntities(List<T> entities) {
        this.entities.addAll(entities);
        //fireContentsChanged();
    }

    public T getEntityByRow(int row) {
        return entities.get(row);
    }

    public List<T> getEntities(){
        return entities;
    }

    public void removeRow(int row) {
        entities.remove(row);
        //fireTableDataChanged();
    }

    public void clear() {
        entities.clear();
    }

}