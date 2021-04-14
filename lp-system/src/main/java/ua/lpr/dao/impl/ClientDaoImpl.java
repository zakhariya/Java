package ua.lpr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.lpr.dao.ClientDao;
import ua.lpr.mapper.ClientMapper;
import ua.lpr.model.Client;

import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String table = "tblMain";

    @Override
    public List<Client> getAll() {
        String sql = "SELECT * FROM " + table + " ORDER BY Client";

        return jdbcTemplate.query(sql, new ClientMapper());
    }

    @Override
    public List<Client> getByNameLike(String[] partsOfName) {
        String sql = "SELECT * FROM " + table + " WHERE Client LIKE ? ORDER BY Client";

        String pon = "";

        for(String n : partsOfName)
            pon += "%" + n;

        if(pon.length() > 0)
            pon += "%";

        return jdbcTemplate.query(sql, new ClientMapper(), pon);
    }

    @Override
    public List<Client> getLimited(int limit, int offset) {
        //this is really bad, if will be another DBMS (limit ? offset ?)
        String sql = "SELECT * FROM " + table + " ORDER BY Client OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        return jdbcTemplate.query(sql, new ClientMapper(), offset, limit);
    }

    @Override
    public Client getById(long id) {
        String sql = "SELECT * FROM " + table + " WHERE id=?";

        return jdbcTemplate.queryForObject(sql, new ClientMapper(), id);
    }

    @Override
    public Client getByName(String name) {
        String sql = "SELECT * FROM " + table + " WHERE Client=?";

        try{
            return jdbcTemplate.queryForObject(sql, new ClientMapper(), name);
        }catch (IncorrectResultSizeDataAccessException | NullPointerException ex){
            return null;
        }
    }
}
