package com.mapr.springframework.data.maprdb.examples.derived;

import com.mapr.springframework.data.maprdb.examples.derived.model.User;
import com.mapr.springframework.data.maprdb.examples.derived.repository.UserRepository;
import com.mapr.springframework.data.maprdb.repository.config.EnableMapRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMapRRepository
public class DerivedQueries implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DerivedQueries.class, args);
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

        System.out.println("\n==== Searching for users with name 'John' ===");
        printUsers(repository.findByName("John"));

        System.out.println("\n==== Searching for users with: name 'John' or age between '19' and '20' and enabled 'true' ===");
        printUsers(repository.findByNameOrEnabledTrueAndAgeBetween("John", 19, 20));

        System.out.println("\n==== Searching for first 2 users with age between '19' and '21' and ordered by name ===");
        printUsers(repository.findFirst2ByAgeBetweenOrderByNameAsc(19, 21));

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
