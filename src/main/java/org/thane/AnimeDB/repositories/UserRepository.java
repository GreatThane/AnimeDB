package org.thane.AnimeDB.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thane.AnimeDB.entities.MalUser;

import java.util.Set;

public interface UserRepository extends CrudRepository<MalUser, Integer> {

    void deleteByUsername(String username);
    Set<MalUser> findByUsername(String username);
}
