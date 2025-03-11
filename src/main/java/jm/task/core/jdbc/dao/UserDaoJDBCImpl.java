package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS UsersTable (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`lastname` VARCHAR(45) NULL," +
                "`age` INT(100) NULL," +
                "PRIMARY KEY (`id`))";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS UsersTable";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERSTABLE(`name`,`lastname`,`age`) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);

            connection.setAutoCommit(false);
            try {
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
            connection.setAutoCommit(true);

            System.out.println("Пользователь - " + name + " " + lastName + " " + age + " -  добавлен");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM USERSTABLE WHERE `id` = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            try {
                ps.setLong(1, id);
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERSTABLE";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM USERSTABLE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);
            try {
                ps.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
