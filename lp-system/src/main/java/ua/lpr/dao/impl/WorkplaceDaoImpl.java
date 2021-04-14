package ua.lpr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.lpr.dao.WorkplaceDao;
import ua.lpr.mapper.WorkplaceMapper;
import ua.lpr.model.Workplace;

import java.util.List;

@Repository
public class WorkplaceDaoImpl implements WorkplaceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Workplace getById(int id) {
        String sql = "SELECT * FROM tblWorkplaces WHERE ID=?";
        return jdbcTemplate.queryForObject(sql, new WorkplaceMapper(), id);
    }

    @Override
    public Workplace getByPost(String post) {
        String sql = "SELECT * FROM tblWorkplaces WHERE WorkPost=?";
        try {
            return jdbcTemplate.queryForObject(sql, new WorkplaceMapper(), post);
        }catch (IncorrectResultSizeDataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Workplace> listByPartition(String partition) {
        String sql = "SELECT * FROM tblWorkplaces WHERE WorkPartition=?";
        return jdbcTemplate.query(sql, new WorkplaceMapper(), partition);
    }

    @Override
    public List<Workplace> listByPost(String post) {
        String sql = "SELECT * FROM tblWorkplaces WHERE WorkPost=?";
        return jdbcTemplate.query(sql, new WorkplaceMapper(), post);
    }

    @Override
    public List<Workplace> getAll() {
        String sql = "SELECT * FROM tblWorkplaces ORDER BY WorkPartition";
        return jdbcTemplate.query(sql, new WorkplaceMapper());
    }
}
