package ua.lpr.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.lpr.dao.StageDao;
import ua.lpr.model.Stage;

import java.util.List;

public interface StageService {

    List<Stage> getAll();

    List<Stage> getAllInProgress();

    List<Stage> getAllByUserName(String userName);

    List<Stage> getActualByUserName(String userName);

    Stage getById(long id);

    void complete(long id);

    void resume(long id);
}
