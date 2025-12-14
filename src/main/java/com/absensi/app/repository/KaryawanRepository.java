package com.absensi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.absensi.app.model.Karyawan;

public interface KaryawanRepository extends JpaRepository<Karyawan, Long> {
    Karyawan findByNip(String nip);
}

