package ua.lpr.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lpr.entity.Recipient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipientDaoImpl extends AbstractDao  implements RecipientDao {

    private static final Logger logger = LoggerFactory.getLogger(RecipientDao.class);


    public RecipientDaoImpl(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    public List<Recipient> findAll() {
        List<Recipient> recipients = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM polygraphy_email_list WHERE sent=0 AND subscribed=1 ORDER BY id DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                recipients.add(RecipientMapper.getInstance().mapTo(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipients;
    }

    @Override
    public Recipient findById(int id) {
        Recipient recipient = null;

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM polygraphy_email_list WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            recipient = RecipientMapper.getInstance().mapTo(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipient;
    }

    @Override
    public Recipient save(Recipient recipient) throws SQLException {
        int id = recipient.getId();
        String sql = "INSERT INTO polygraphy_email_list (name, company, city, email, sent, subscribed, md5) VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (id > 0) {
            logger.info("Update: " + recipient);
            sql = "UPDATE polygraphy_email_list SET name=?, company=?, city=?, email=?, sent=?, subscribed=?, md5=? WHERE id=?";
        } else {
            logger.info("Create: " + recipient);
        }

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, recipient.getName());
            statement.setString(2, recipient.getCompany());
            statement.setString(3, recipient.getCity());
            statement.setString(4, recipient.getEmail());
            statement.setBoolean(5, recipient.isSent());
            statement.setBoolean(6, recipient.isSubscribed());
            statement.setString(7, recipient.getMd5());

            if (id > 0) {
                statement.setInt(8, id);
            }

            id = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);

            throw e;
        }

        return findById(id);
    }

    @Override
    public void executeSql(String sql) throws SQLException {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
    }
}

class RecipientMapper {
    private static RecipientMapper instance;

    private RecipientMapper() {
    }

    static RecipientMapper getInstance() {
        if (instance == null) {
            instance = new RecipientMapper();
        }

        return instance;
    }

    Recipient mapTo(ResultSet set) throws SQLException {
        int id = set.getInt("id");
        String name = set.getString("name");
        String company = set.getString("company");
        String city = set.getString("city");
        String email = set.getString("email");
        boolean sent = set.getBoolean("sent");
        boolean subscribed = set.getBoolean("subscribed");

        return new Recipient(id, name, company, city, email, sent, subscribed);
    }
}
