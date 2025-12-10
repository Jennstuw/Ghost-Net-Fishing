package com.ghostnet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "ghost_nets")
public class GhostNet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false)
    private Double longitude;

    @NotNull(message = "Estimated size is required")
    @Column(name = "estimated_size", nullable = false)
    private Double estimatedSize; // in square meters

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NetStatus status = NetStatus.REPORTED;

    @Column(name = "reported_date", nullable = false)
    private LocalDateTime reportedDate;

    @Column(name = "recovery_date")
    private LocalDateTime recoveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_id")
    private Person reportedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salvager_id")
    private Person salvager;

    // Constructors
    public GhostNet() {
        this.reportedDate = LocalDateTime.now();
    }

    public GhostNet(Double latitude, Double longitude, Double estimatedSize) {
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.estimatedSize = estimatedSize;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(Double estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public NetStatus getStatus() {
        return status;
    }

    public void setStatus(NetStatus status) {
        this.status = status;
    }

    public LocalDateTime getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(LocalDateTime reportedDate) {
        this.reportedDate = reportedDate;
    }

    public LocalDateTime getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(LocalDateTime recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public Person getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Person reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Person getSalvager() {
        return salvager;
    }

    public void setSalvager(Person salvager) {
        this.salvager = salvager;
    }

    public String getLocationString() {
        return String.format("%.6f, %.6f", latitude, longitude);
    }
}