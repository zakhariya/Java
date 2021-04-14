package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.dao.ClientDao;
import ua.lpr.model.Client;
import ua.lpr.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientDao clientDao;


    @Override
    public List<Client> getAll() {
        return clientDao.getAll();
    }

    @Override
    public List<Client> getByNameLike(String[] partsOfName) {
        return clientDao.getByNameLike(partsOfName);
    }

    @Override
    public List<Client> getLimited(int limit, int offset) {
        return clientDao.getLimited(limit, offset);
    }

    @Override
    public Client getById(long id) {
        return clientDao.getById(id);
    }

    @Override
    public Client getByName(String name) {
        return clientDao.getByName(name);
    }
}
