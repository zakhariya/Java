package ua.lpr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.lpr.dao.StageDao;
import ua.lpr.mapper.StageMapper;
import ua.lpr.model.Stage;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Repository
public class StageDaoImpl implements StageDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String table = "tblEtaps", joinTable = "tblOrdersMK";

    private String columns = "st.ID, st.EtapName, st.Etap_Plane_date, st.Etap_fact_date, " +
            "st.OrderID, st.Komment, st.UserName, st.DeleteMark, " +
            "ord.Client, ord.Product";


    @Override
    public List<Stage> getAll() {
        String sql = "SELECT " + columns + " FROM " + table + " st " +
                "LEFT JOIN " + joinTable + " ord  ON st.OrderID=ord.ID " +
                "ORDER BY st.Etap_Plane_date, st.Etap_fact_date";

        return jdbcTemplate.query(sql, new StageMapper());
    }

    @Override
    public List<Stage> getAllInProgress() {
        String sql = "SELECT " + columns + " FROM " + table + " st " +
                "LEFT JOIN " + joinTable + " ord  ON st.OrderID=ord.ID " +
                "WHERE st.Etap_fact_date IS NULL " +
                "ORDER BY st.Etap_Plane_date, st.Etap_fact_date";

        return jdbcTemplate.query(sql, new StageMapper());
    }

    @Override
    public List<Stage> getAllByUserName(String userName) {
        String sql = "SELECT " + columns + " FROM " + table + " st " +
                "LEFT JOIN " + joinTable + " ord  ON st.OrderID=ord.ID " +
                "WHERE st.UserName LIKE ? " +
                "ORDER BY st.Etap_Plane_date, st.Etap_fact_date";

        return jdbcTemplate.query(sql, new StageMapper(), "%" + userName + "%");
    }

    @Override
    public List<Stage> getActualByUserName(String userName) {
        String sql = "SELECT " + columns + " FROM " + table + " st " +
                "LEFT JOIN " + joinTable + " ord  ON st.OrderID=ord.ID " +
                "WHERE st.UserName LIKE ? " +
                "AND st.Etap_fact_date IS NULL " +
                "AND st.DeleteMark = 0 " +
                "OR st.UserName LIKE ? " +
                "AND Etap_fact_date BETWEEN ? AND ? " +
                "AND st.DeleteMark = 0 " +
                "ORDER BY st.Etap_fact_date, st.Etap_Plane_date";

        String beforeDay =  LocalDate.now().plusDays(-1).toString();
        String now =  LocalDate.now().toString();
        String afterDay = LocalDate.now().plusDays(1).toString();

//        System.out.print("\n\n" + sql + "\n\n" + beforeDay + " - " + now + " - " + afterDay +
//                "\n" + Date.valueOf(beforeDay) + " - " + Date.valueOf(now) + " - " + Date.valueOf(afterDay) +
//                "\nep " + Instant.now().toEpochMilli() + " == " + Calendar.getInstance().getTimeInMillis() + "\n\n"); //true

        return jdbcTemplate.query(sql, new StageMapper(), "%" + userName + "%", "%" + userName + "%", Date.valueOf(beforeDay), Date.valueOf(afterDay));
        //new Date(1533589200000L), new Date(Instant.now().toEpochMilli()));
    }

    @Override
    public Stage getById(long id) {
        String sql = "SELECT " + columns + " FROM " +table + " st " +
                "LEFT JOIN " + joinTable + " ord ON st.OrderID=ord.ID " +
                "WHERE st.ID=?";

        try{
            return jdbcTemplate.queryForObject(sql, new StageMapper(), id);
        }catch (IncorrectResultSizeDataAccessException ex){
            return null;
        }
    }

    @Override
    public void complete(long id) {
        String sql = "UPDATE " + table + " SET Etap_fact_date=? WHERE id=?";

        jdbcTemplate.update(sql, new Timestamp(Calendar.getInstance().getTimeInMillis()), id);
    }

    @Override
    public void resume(long id) {
        String sql = "UPDATE " + table + " SET Etap_fact_date=? WHERE id=?";

        jdbcTemplate.update(sql, null, id);
    }
}
