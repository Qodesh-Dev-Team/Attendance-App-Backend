package com.qcc.hallow.repository;

import com.qcc.hallow.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
