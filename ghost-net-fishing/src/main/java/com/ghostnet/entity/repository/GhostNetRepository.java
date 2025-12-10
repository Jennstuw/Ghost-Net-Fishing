package com.ghostnet.repository;

import com.ghostnet.entity.GhostNet;
import com.ghostnet.entity.NetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {
    
    List<GhostNet> findByStatus(NetStatus status);
    
    List<GhostNet> findByStatusIn(List<NetStatus> statuses);
    
    List<GhostNet> findBySalvagerId(Long salvagerId);
    
    List<GhostNet> findByStatusOrderByReportedDateDesc(NetStatus status);
}