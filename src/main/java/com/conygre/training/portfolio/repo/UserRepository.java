package com.conygre.training.portfolio.repo;

import com.conygre.training.portfolio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {

}
