package com.mapr.springframework.data.maprdb.examples.query.repository;

import com.mapr.springframework.data.maprdb.examples.query.model.User;
import com.mapr.springframework.data.maprdb.repository.MapRRepository;
import com.mapr.springframework.data.maprdb.repository.Query;

import java.util.List;

public interface UserRepository extends MapRRepository <User, String> {

    @Query("{\"$and\":[ {\"$eq\":{\"enabled\":true}}]}")
    List<User> findEnabledUsers();

}
