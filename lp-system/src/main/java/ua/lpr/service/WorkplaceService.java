package ua.lpr.service;


import ua.lpr.model.Workplace;

import java.util.List;

public interface WorkplaceService {

    Workplace getById(int id);

    List<Workplace> listByPartition(String paritition);

    List<Workplace> listByPost(String post);

    List<Workplace> getAll();

    boolean isPostExists(String post);
}
