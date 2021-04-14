package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.dao.WorkplaceDao;
import ua.lpr.model.Workplace;
import ua.lpr.service.WorkplaceService;

import java.util.List;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    @Autowired
    private WorkplaceDao workplaceDao;

    @Override
    public Workplace getById(int id) {
        return workplaceDao.getById(id);
    }

    @Override
    public List<Workplace> listByPartition(String partition) {
        return workplaceDao.listByPartition(partition);
    }

    @Override
    public List<Workplace> listByPost(String post) {
        return workplaceDao.listByPost(post);
    }

    @Override
    public List<Workplace> getAll() {
        return workplaceDao.getAll();
    }

    @Override
    public boolean isPostExists(String post) {
        if(workplaceDao.getByPost(post) != null)
            return true;

        return false;
    }

}
