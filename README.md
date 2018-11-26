# Spring Data MapR DB Examples

In this demo we explain the basic usage of Spring Data MapR DB with all its key features. We used Java 8, Gradle and Spring Boot 2.1.0 for this demo.

* [Part 1 - Getting Started](#part-1---getting-started)
* [Part 2 - CRUD operations](#part-2---crud-operations)
* [Part 3 - Derived queries](#part-3---derived-queries)
* [Part 4 - Query methods](#part-4---query-methods)

## Part 1 - Getting Started

1) Generate and download project with necessary modules from https://start.spring.io/

2) Add maven repository for MapR and [JitPack](https://jitpack.io/) in build.gradle

```java
repositories {
    mavenCentral()
    maven {
        url 'http://repository.mapr.com/maven'
    }
    maven {
            url 'https://jitpack.io'
    }
}
```

3) Add [Spring Data MapR DB](https://github.com/mapr-demos/spring-data-maprdb) dependence in build.gradle

```java
compile("com.github.mapr-demos:spring-data-maprdb:1.0.0.M2")
```
4) Create modify Application class and  enable MapR repository
* `@EnableMapRRepository` enables repositories
 
```java 
@SpringBootApplication
@EnableMapRRepository
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... strings) {
	}
}
```

5) Create a Configuration class
* `getDatabaseName()` Method returns the database name, which is path in MapR FS
```java
@Configuration
class DatabaseConfig extends AbstractMapRConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected String getHost() {
        return "";
    }

    @Override
    protected String getUsername() {
        return "";
    }

    @Override
    protected String getPassword() {
        return "";
    }

}
```
6) Create model

Let’s create model for users. Class should be annotated as `@Document`, you can also specify name for it’s table, for example ‘users’, otherwise name will be generated from class name. You should also annotate your id as `@Id`.
```java
@Document("users")
public class User {
    @Id
    private String id;
    private String name;
    private Boolean enabled = false;
    private Integer age = 25;
    public User() {
        this("user", false, 18);
    }
    public User(String name, Boolean enabled, Integer age) {
        this.name = name;
        this.enabled = enabled;
        this.age = age;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", age=" + age +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(enabled, user.enabled) &&
                Objects.equals(age, user.age);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, enabled, age);
    }
}
```
## Part 2 - CRUD operations

1) Create repository
```java
public interface UserRepository extends MapRRepository <User, String> {
}
```
2) Inject repository in your main class
```java
@Autowired 
UserRepository repository;
```
3) Save and read entity
```java
// Saving user
User user = new User();
user.setName("Andrew"); 
user = repository.save(user);

// Reading user
System.out.println(String.format("Found user %s", repository.findAll().get(0).toString()));
```
Result:
```
==== Saving user ===

==== Reading user ===
Found user User{id='dd906a05-dfe5-4920-87ea-e98b03beb006', name='Andrew', enabled=false, age=18}
```
4) Update entity
```java
// Modifying user 'Andrew' to 'Andrew Temp' 
user.setName("Andrew Temp"); 
repository.save(user); 

// Reading user 
System.out.println(String.format("Found user %s", repository.findAll().get(0).toString()));
 ```
Result:
```
==== Modifying user 'Andrew' to 'Andrew Temp' ===

==== Reading user ===
Found user User{id='dd906a05-dfe5-4920-87ea-e98b03beb006', name='Andrew Temp', enabled=false, age=18}
```
5) Save multiple users
```java
// Saving multiple users
List<User> users = getUsers(); 
users = repository.saveAll(users);

// Reading users
printUsers(repository.findAll());
```
We also need the method getUsers() which looks as follow:
```java
List<User> getUsers() {
   return Arrays.asList(
         new User("John", false,18),
         new User("Steve", true, 21),
         new User("Bill", true, 19),
         new User("Alex",true, 20)
   );
}
```
And the method printUsers() which looks as follow:
```java
void printUsers(List<User> users {
System.out.println("Found " + users.size() + " users:");
users.forEach(System.out::println);
}
```

Result:
```
==== Reading users ===
=====================
Found 5 users:
User{id='06ce6cc4-3c02-437d-962f-d26b285f90af', name='Bill', enabled=true, age=19}
User{id='7e21ad5a-62cf-48a8-ba42-3c219c89513b', name='John', enabled=false, age=18}
User{id='ccb50ff8-48b9-4f41-afe1-3e16f848bfac', name='Andrew Temp', enabled=false, age=18}
User{id='d634fb0d-f57c-49c0-818a-d4dc11c03139', name='Alex', enabled=true, age=20}
User{id='f71f191c-1288-4427-84f1-490b0a7aa662', name='Steve', enabled=true, age=21}
=====================
```
6) Delete user
```java
// Delete user
repository.delete(user); 

// Reading users
printUsers(repository.findAll());
```
Result:
```
==== Delete user 'Andrew Temp' ===

==== Reading users ===
=====================
Found 4 users:
User{id='06ce6cc4-3c02-437d-962f-d26b285f90af', name='Bill', enabled=true, age=19}
User{id='7e21ad5a-62cf-48a8-ba42-3c219c89513b', name='John', enabled=false, age=18}
User{id='d634fb0d-f57c-49c0-818a-d4dc11c03139', name='Alex', enabled=true, age=20}
User{id='f71f191c-1288-4427-84f1-490b0a7aa662', name='Steve', enabled=true, age=21}
=====================
```
7) Delete all users
```java
// Deleting all users 
repository.deleteAll(); 

// Reading users
printUsers(repository.findAll());
```
Result:
```
==== Deleting all users ===

==== Reading users ===
=====================
Found 0 users:
=====================
```

## Part 3 - Derived queries
Spring Data MapRDB supports Queries derived from methods names by splitting it into its semantic parts and converting into Query Condition. The mechanism strips the prefixes find..By from the method and parses the rest. The “By” acts as a separator to indicate the start of the criteria for the query to be built. You can define conditions on entity properties and concatenate them with And and Or. Only find..By prefix supports at the moment.

### Simple findBy

Let’s start with an easy example. We want to find users based on their name. The only thing we have to do is to add a method findByName(String) to our UserRepository.
```java
public interface UserRepository extends MapRRepository <User, String> {
    List<User> findByName(String name);
}
```
In the run() method we call our new method findByName(String) and try to find all users with the name ‘John’.
```java
System.out.println("\n==== Searching for users with name 'John' ==="); 
printUsers(repository.findByName("John"));
```
Result:
```
==== Searching for users with name 'John' ===
=====================
Found 1 users:
User{id='57be870e-ab71-4577-b559-f51a8006f147', name='John', enabled=false, age=18}
=====================
```
### More complex findBy

Let’s add more complex findBy request:
```java
public interface UserRepository extends MapRRepository <User, String> {
    List<User> findByNameOrEnabledTrueAndAgeBetween(String name, int ageStart, int ageEnd);
}
```
And add the method call:
```java
System.out.println("\n==== Searching for users with: name 'John' or age between '19' and '21' and enabled 'true' ===");
printUsers(repository.findByNameOrEnabledTrueAndAgeBetween("John", 19, 21));
```
Result:
```
==== Searching for users with: name 'John' or age between '19' and '20' and enabled 'true' ===
=====================
Found 3 users:
User{id='0a3df20f-626e-48a6-acb4-2fc75682a610', name='Bill', enabled=true, age=19}
User{id='af9e3609-55f6-4986-9b5d-a2ecea2ee5a7', name='Alex', enabled=true, age=20}
User{id='f7987667-6d5a-4aba-b0ba-68e7cba60bb4', name='John', enabled=false, age=18}
=====================
```
### Sort and limit findBy

Let’s add findBy request with limit and sorting:
```java
public interface UserRepository extends MapRRepository <User, String> {
    List<User> findFirst2ByAgeBetweenOrderByNameAsc(int ageStart, int ageEnd);
}
```

And add the method call:
```java
System.out.println("\n==== Searching for first 2 users with age between '19' and '21' and ordered by name ===");
printUsers(repository.findFirst2ByAgeBetweenOrderByNameAsc(19, 21));
```
Result:
```
==== Searching for first 2 users with age between '19' and '21' and ordered by name ===
=====================
Found 2 users:
User{id='bdc627da-dd9a-405f-823c-b622405a9575', name='Alex', enabled=true, age=20}
User{id='6a0afdc4-4d76-48b8-adc1-1bbd652e6b67', name='Bill', enabled=true, age=19}
=====================
```
## Part 4 - Query methods
When it comes to more complex use cases where a derived method would get way too long and become unreadable, queries can be supplied with the `@Query` annotation on methods in our repositories. For now Spring Data MapR DB supports only queries without parameters.

Let’s add some request with `@Query` annotation to repository:
```java
public interface UserRepository extends MapRRepository <User, String> {
    @Query("{\"$and\":[ {\"$eq\":{\"enabled\":true}}]}")
    List<User> findEnabledUsers();
}
```

And add method call:
```java
System.out.println("\n==== Searching for users with enabled 'true' ==="); 
printUsers(repository.findEnabledUsers());
```
Result:
```
==== Searching for users with enabled 'true' ===
=====================
Found 3 users:
User{id='1ed7cb25-3e0b-4168-a2e3-b157f6dfef4f', name='Steve', enabled=true, age=21}
User{id='90917b09-7f64-4dec-b399-a6702484e7fd', name='Alex', enabled=true, age=20}
User{id='f2a1c0aa-26f7-49ed-8cbf-798cefe2f162', name='Bill', enabled=true, age=19}
=====================
```
