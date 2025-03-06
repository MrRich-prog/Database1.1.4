package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Сергей","Петров", (byte) 27);
        userService.saveUser("Максим","Кашков", (byte) 15);
        userService.saveUser("Александра","Максимова", (byte) 19);
        userService.saveUser("Алекса","Яндекс", (byte) 5);
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
