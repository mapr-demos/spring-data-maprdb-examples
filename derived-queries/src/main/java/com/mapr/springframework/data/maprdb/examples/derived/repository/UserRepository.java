package com.mapr.springframework.data.maprdb.examples.derived.repository;

import com.mapr.springframework.data.maprdb.examples.derived.model.User;
import com.mapr.springframework.data.maprdb.repository.MapRRepository;

import java.util.List;

public interface UserRepository extends MapRRepository <User, String> {

    List<User> findByName(String name);

    List<User> findByNameOrEnabledTrueAndAgeBetween(String name, int ageStart, int ageEnd);

    List<User> findFirst2ByAgeBetweenOrderByNameAsc(int ageStart, int ageEnd);

}
