package com.system_mastery.ascension.repository;


import com.system_mastery.ascension.model.Ritual;
import com.system_mastery.ascension.model.RitualLog;
import com.system_mastery.ascension.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RitualLogRepository extends JpaRepository<RitualLog, Long> {
    List<RitualLog> findByDate(LocalDate date);
    List<RitualLog> findByDateAndUserId(LocalDate date, Long userId);
}
