package com.absensi.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.absensi.app.model.Absensi;

public interface AbsensiRepository extends JpaRepository<Absensi, Long> {

    List<Absensi> findByUserId(String userId);

    List<Absensi> findByUserIdAndTimestampBetween(
        String userId,
        LocalDateTime start,
        LocalDateTime end
    );
}
