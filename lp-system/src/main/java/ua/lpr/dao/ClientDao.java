package ua.lpr.dao;

import ua.lpr.model.Client;

import java.util.List;

public interface ClientDao {

    List<Client> getAll();

    List<Client> getByNameLike(String[] partsOfName);

    List<Client> getLimited(int limit, int offset);

    Client getById(long id);

    Client getByName(String name);
}
