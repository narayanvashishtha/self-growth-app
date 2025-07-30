package com.system_mastery.ascension.repository;

import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RitualRepository extends JpaRepository<Ritual, Long> {

    List<Ritual> findByUserId(Long userId);

}
