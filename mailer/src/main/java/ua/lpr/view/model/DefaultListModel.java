package ua.lpr.view.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DefaultListModel<T> extends AbstractListModel {

    protected final List<T> entities = new ArrayList<>();

    @Override
    public int getSize() {
        return entities.size();
    }

    public void addEntity(T entity) {
        entities.add(0, entity);
    }

    public void addEntities(List<T> entities) {
        this.entities.addAll(entities);
    }

    public T getEntityByRow(int row) {
        return entities.get(row);
    }

    public List<T> getEntities(){
        return entities;
    }

    public List<T> getEntities(int[] indices) {
        List<T> entities = new ArrayList<>();

        for (int idx : indices) {
            entities.add(getEntityByRow(idx));
        }

        return entities;
    }

    public void removeRow(int row) {
        entities.remove(row);
    }

    public void clear() {
        entities.clear();
    }
}