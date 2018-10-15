package org.thane.AnimeDB.repositories;

import org.springframework.data.repository.CrudRepository;
import org.thane.AnimeDB.entities.Anime;

import java.util.Set;

public interface AnimeRepository extends CrudRepository<Anime, Integer> {

    Set<Anime> findByName(String name);
}
