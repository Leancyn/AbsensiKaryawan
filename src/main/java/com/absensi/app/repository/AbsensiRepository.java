package com.absensi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.absensi.app.model.Absensi;

@Repository
public interface AbsensiRepository extends JpaRepository<Absensi, Long> {
    List<Absensi> findByStatus(String status);
    List<Absensi> findByUserId(String userId);

}
