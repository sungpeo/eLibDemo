package com.elib.demo.domain.user;

import com.elib.demo.infrastructure.persistence.Persistence;

import java.util.LinkedList;
import java.util.List;

public class UserService {

    public UserService (Persistence persistence) {
        this.persistence = persistence;
    }

    private Persistence persistence;

    public void addUser(String name, String addr, String phone) {
        User user = new User(name, addr, phone);

        persistence.saveUser(user);

        System.out.println("Added user: " + user.getCode() + " - " + user.getName());
    }

    public void addStaff(String name, String addr, String phone, String id) {

        User user = new User(name, addr, phone, id);
        persistence.saveUser(user);

        System.out.println("Added staff: " + user.getCode() + " - " + user.getName());

    }

    public void rmUser(int userCode) {
        User user = persistence.findUser(userCode);

        if (user == null || user.numberOfLoans() > 0 || user.getLatePenalty() > 0) {
            return;
        }

        persistence.removeUser(user);

        System.out.println("Removed user: " + user.getCode() + " - " + user.getName());

    }

    public void searchUser(String name) {
        List<User> users = persistence.findAllUsers();

        List<User> usersFound = new LinkedList<>();
        for (User user : users) {
            if (user.getName().indexOf(name) != -1)
                usersFound.add(user);
        }

        for (User user : usersFound) {
            System.out.println("User found: " + user.getCode() + " - " + user.getName());
        }
    }

    public void printUser(int userCode) {
        User user = persistence.findUser(userCode);

        if (user != null)
            user.printInfo();
    }


}
