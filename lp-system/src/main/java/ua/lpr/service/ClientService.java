package ua.lpr.service;

import ua.lpr.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    List<Client> getByNameLike(String[] partsOfName);

    List<Client> getLimited(int limit, int offset);

    Client getById(long id);

    Client getByName(String name);

}
