package com.absensi.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.absensi.app.model.Absensi;

@Repository
public interface AbsensiRepository extends JpaRepository<Absensi, Long> {

    java.util.Optional<Absensi> findByToken(String token);

}
