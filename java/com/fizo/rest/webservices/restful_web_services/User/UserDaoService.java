package com.fizo.rest.webservices.restful_web_services.User;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    public static List<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Dan", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Jim", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount, "Jack", LocalDate.now().minusYears(15)));
    }

    public List<User> findAll() {
        return users;
    }

    //using predicate and lambda expression to get user by id
    public User findUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    //creating and saving new User
    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    //deleting user by id
    public void deleteUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
