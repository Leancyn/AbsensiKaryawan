package com.absensi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.absensi.app.model.Cuti;

public interface CutiRepository extends JpaRepository<Cuti, Long> {

    List<Cuti> findByUserId(String userId);

    List<Cuti> findByStatus(String status);
}
