package ua.lpr.dao;

import ua.lpr.model.Stage;

import java.util.List;

public interface StageDao {

    List<Stage> getAll();

    List<Stage> getAllInProgress();

    List<Stage> getAllByUserName(String userName);

    List<Stage> getActualByUserName(String userName);

    Stage getById(long id);

    void complete(long id);

    void resume(long id);
}
