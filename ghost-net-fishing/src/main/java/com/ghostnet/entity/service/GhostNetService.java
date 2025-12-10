package com.ghostnet.service;

import com.ghostnet.entity.GhostNet;
import com.ghostnet.entity.NetStatus;
import com.ghostnet.entity.Person;
import com.ghostnet.repository.GhostNetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GhostNetService {

    @Autowired
    private GhostNetRepository ghostNetRepository;

    public GhostNet reportGhostNet(Double latitude, Double longitude, Double estimatedSize, Person reportedBy) {
        GhostNet ghostNet = new GhostNet(latitude, longitude, estimatedSize);
        ghostNet.setReportedBy(reportedBy);
        ghostNet.setStatus(NetStatus.REPORTED);
        return ghostNetRepository.save(ghostNet);
    }

    public List<GhostNet> getAllGhostNets() {
        return ghostNetRepository.findAll();
    }

    public List<GhostNet> getGhostNetsByStatus(NetStatus status) {
        return ghostNetRepository.findByStatus(status);
    }

    public List<GhostNet> getNetsToBeRecovered() {
        return ghostNetRepository.findByStatusIn(
            Arrays.asList(NetStatus.REPORTED, NetStatus.RECOVERY_PENDING)
        );
    }

    public Optional<GhostNet> getGhostNetById(Long id) {
        return ghostNetRepository.findById(id);
    }

    public GhostNet registerForRecovery(Long netId, Person salvager) {
        Optional<GhostNet> optionalNet = ghostNetRepository.findById(netId);
        if (optionalNet.isPresent()) {
            GhostNet net = optionalNet.get();
            if (net.getStatus() == NetStatus.REPORTED) {
                net.setSalvager(salvager);
                net.setStatus(NetStatus.RECOVERY_PENDING);
                return ghostNetRepository.save(net);
            }
        }
        return null;
    }

    public GhostNet markAsRecovered(Long netId) {
        Optional<GhostNet> optionalNet = ghostNetRepository.findById(netId);
        if (optionalNet.isPresent()) {
            GhostNet net = optionalNet.get();
            net.setStatus(NetStatus.RECOVERED);
            net.setRecoveryDate(LocalDateTime.now());
            return ghostNetRepository.save(net);
        }
        return null;
    }

    public GhostNet markAsLost(Long netId, Person reportingPerson) {
        Optional<GhostNet> optionalNet = ghostNetRepository.findById(netId);
        if (optionalNet.isPresent()) {
            GhostNet net = optionalNet.get();
            // Cannot mark as lost anonymously
            if (reportingPerson != null && !reportingPerson.isAnonymous()) {
                net.setStatus(NetStatus.LOST);
                return ghostNetRepository.save(net);
            }
        }
        return null;
    }

    public List<GhostNet> getNetsBySalvager(Long salvagerId) {
        return ghostNetRepository.findBySalvagerId(salvagerId);
    }

    public void deleteGhostNet(Long id) {
        ghostNetRepository.deleteById(id);
    }
}