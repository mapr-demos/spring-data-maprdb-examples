package com.mapr.springframework.data.maprdb.examples.query;

import com.mapr.springframework.data.maprdb.examples.query.model.User;
import com.mapr.springframework.data.maprdb.examples.query.repository.UserRepository;
import com.mapr.springframework.data.maprdb.repository.config.EnableMapRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableMapRRepository
public class StringQuery implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(StringQuery.class, args);
    }

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... strings) {

        System.out.println("\n==== Start Application ===");

        List<User> users = getUsers();

        System.out.println("\n==== Prepare DB for example ===");
        repository.deleteAll();

        System.out.println("\n==== Saving test users ===");
        users = repository.saveAll(users);

        System.out.println("\n==== Reading users ===");
        printUsers(repository.findAll());

        System.out.println("\n==== Searching for users with enabled 'true' ===");
        printUsers(repository.findEnabledUsers());

        System.out.println("\n==== Deleting all users ===");
        repository.deleteAll();

        System.out.println("\n==== End Application ===");

    }

    private void printUsers(List<User> users) {
        System.out.println("=====================");
        System.out.println("Found " + users.size() + " users:");
        users.forEach(System.out::println);
        System.out.println("=====================");
    }

    private List<User> getUsers() {
        return Arrays.asList(
                new User("John", false,18),
                new User("Steve", true, 21),
                new User("Bill", true, 19),
                new User("Alex",true, 20)
        );
    }

}
