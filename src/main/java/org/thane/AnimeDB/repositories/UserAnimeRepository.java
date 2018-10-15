package org.thane.AnimeDB.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thane.AnimeDB.entities.UserAnime;

import java.util.Set;

public interface UserAnimeRepository extends CrudRepository<UserAnime, Integer> {

    Set<UserAnime> findByUserId(int id);
}
