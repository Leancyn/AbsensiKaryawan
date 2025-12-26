package com.absensi.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.absensi.app.model.Absensi;

public interface AbsensiRepository extends JpaRepository<Absensi, Long> {

    List<Absensi> findByUserId(String userId);

    List<Absensi> findByUserIdAndTimestampBetween(
        String userId,
        LocalDateTime start,
        LocalDateTime end
    );

     
    // Statistik berdasarkan status + nip (USER LOGIN)
        @Query("""
        SELECT a.status, COUNT(a)
        FROM Absensi a
        WHERE a.nip = :nip
        GROUP BY a.status
    """)
    List<Object[]> countByStatusByNip(@Param("nip") String nip);


    
}
