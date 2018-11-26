package com.mapr.springframework.data.maprdb.examples.crud;

import com.mapr.springframework.data.maprdb.examples.crud.model.User;
import com.mapr.springframework.data.maprdb.examples.crud.repository.UserRepository;
import com.mapr.springframework.data.maprdb.repository.config.EnableMapRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMapRRepository
public class SimpleCrudApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SimpleCrudApplication.class, args);
	}

	@Autowired
	private UserRepository repository;

	@Override
	public void run(String... strings) {

		System.out.println("\n==== Start Application ===");

		System.out.println("\n==== Prepare DB for example ===");
		repository.deleteAll();

		System.out.println("\n==== Saving user ===");
		User user = new User();
		user.setName("Andrew");
		user = repository.save(user);

		System.out.println("\n==== Reading user ===");
		System.out.println(String.format("Found user %s", repository.findAll().get(0).toString()));

		System.out.println("\n==== Modifying user 'Andrew' to 'Andrew Temp' ===");
		user.setName("Andrew Temp");
		repository.save(user);

		System.out.println("\n==== Reading user ===");
		System.out.println(String.format("Found user %s", repository.findAll().get(0).toString()));

		System.out.println("\n==== Saving multiple users ===");
		List<User> users = getUsers();
		repository.saveAll(users);

		System.out.println("\n==== Reading users ===");
		printUsers(repository.findAll());

		System.out.println("\n==== Delete user 'Andrew Temp' ===");
		repository.delete(user);

		System.out.println("\n==== Reading users ===");
		printUsers(repository.findAll());

		System.out.println("\n==== Deleting all users ===");
		repository.deleteAll();

		System.out.println("\n==== Reading users ===");
		printUsers(repository.findAll());

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
