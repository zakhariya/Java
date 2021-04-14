package ua.lpr.dao;

import ua.lpr.entity.Recipient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipientDaoImpl  implements RecipientDao {
    private String url;
    private String user;
    private String password;

    public RecipientDaoImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public List<Recipient> findAll() {
        List<Recipient> recipients = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM polygraphy_email_list";
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
    public Recipient save(Recipient recipient) {
        String sql = "INSERT INTO polygraphy_email_list (name, company, city, email, sent, subscribed, md5) VALUES (?, ?, ?, ?, ?, ?, ?)";

        if (findById(recipient.getId()) != null) {
            sql = "UPDATE polygraphy_email_list SET name=?, company=?, city=?, email=?, sent=?, subscribed=?, md5=?";
        }

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, recipient.getName());
            statement.setString(2, recipient.getCompany());
            statement.setString(3, recipient.getCity());
            statement.setString(4, recipient.getEmail());
            statement.setBoolean(5, recipient.isSent());
            statement.setBoolean(6, recipient.isSubscribed());
            statement.setString(7, recipient.getMd5());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipient;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}

class RecipientMapper {
    private static RecipientMapper instance;

    private RecipientMapper() {
    }

    public static RecipientMapper getInstance() {
        if (instance == null) {
            instance = new RecipientMapper();
        }

        return instance;
    }

    public Recipient mapTo(ResultSet set) throws SQLException {
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
