package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = getConnection();
        PreparedStatement ps = null;
        String sql = "CREATE TABLE IF NOT EXISTS UsersTable (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`name` VARCHAR(45) NULL," +
                "`lastname` VARCHAR(45) NULL," +
                "`age` INT(100) NULL," +
                "PRIMARY KEY (`id`))";
        try {
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
            closeConnection(connection);
        }
    }

    public void dropUsersTable() {
        Connection connection = getConnection();
        PreparedStatement ps = null;
        String sql = "DROP TABLE IF EXISTS UsersTable";
        try {
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
            closeConnection(connection);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO USERSTABLE(`name`,`lastname`,`age`) VALUES(?,?,?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
            closeConnection(connection);
        }
    }

    public void removeUserById(long id) {
        Connection connection = getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM USERSTABLE WHERE `id` = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
            closeConnection(connection);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = getConnection();
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERSTABLE";
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeStatement(stmt);
            closeConnection(connection);
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM USERSTABLE";
        try {
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closePrepareStatement(ps);
            closeConnection(connection);
        }
    }
}
