package com.mapr.springframework.data.maprdb.examples.crud.repository;

import com.mapr.springframework.data.maprdb.examples.crud.model.User;
import com.mapr.springframework.data.maprdb.repository.MapRRepository;

public interface UserRepository extends MapRRepository <User, String> {
}
