package com.absensi.app.repository;

import com.absensi.app.model.Absensi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AbsensiRepository extends JpaRepository<Absensi, Long> {
    List<Absensi> findByStatus(String status);
}
