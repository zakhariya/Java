package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.dao.StageDao;
import ua.lpr.model.Stage;
import ua.lpr.service.StageService;

import java.util.List;

@Service
public class StageServiceImpl implements StageService {

    @Autowired
    private StageDao stageDao;


    @Override
    public List<Stage> getAll() {
        return stageDao.getAll();
    }

    @Override
    public List<Stage> getAllInProgress() {
        return stageDao.getAllInProgress();
    }

    @Override
    public List<Stage> getAllByUserName(String userName) {
        return stageDao.getAllByUserName(userName);
    }

    @Override
    public List<Stage> getActualByUserName(String userName) {
        return stageDao.getActualByUserName(userName);
    }

    @Override
    public Stage getById(long id) {
        return stageDao.getById(id);
    }

    @Override
    public void complete(long id) {
        stageDao.complete(id);
    }

    @Override
    public void resume(long id) {
        stageDao.resume(id);
    }
}
