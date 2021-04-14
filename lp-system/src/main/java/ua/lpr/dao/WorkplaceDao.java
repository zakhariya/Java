package ua.lpr.dao;


import ua.lpr.model.Workplace;

import java.util.List;

public interface WorkplaceDao {

    Workplace getById(int id);

    Workplace getByPost(String post);

    List<Workplace> listByPartition(String parftition);

    List<Workplace> listByPost(String post);
    
    List<Workplace> getAll();


}
