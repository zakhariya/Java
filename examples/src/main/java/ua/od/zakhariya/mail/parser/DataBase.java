package ua.od.zakhariya.mail.parser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String url = "jdbc:mysql://localhost:3306/mail?useUnicode=true&serverTimezone=UTC";
    private String user = "mail";
    private String password = "S86Ad$";

    public void updateRecipients(List<String> emails) {
        for (String email : emails) {
            updateRecipient(email);
        }
    }

    public void removeRecipients(List<String> emails) {
        for (String email : emails) {
            deleteRecipient(email);
        }
    }

    public List<String> getFileNames(String dateTimeString) {
        List<String> fileNames = new ArrayList<>();
        String sql =
                "SELECT `messagefilename` FROM `mail`.`hm_messages` WHERE `messagecreatetime` > '" + dateTimeString + "'";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                fileNames.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    public void updateRecipient(String email) {
        String sql = "UPDATE `api.lpr.ua`.`polygraphy_email_list` SET sent=? WHERE email=?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, 0);
            statement.setString(2, email);

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Updated: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteRecipient(String email) {
        String sql = "DELETE FROM `api.lpr.ua`.`polygraphy_email_list` WHERE email=?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, email);

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Deleted: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}