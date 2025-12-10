package com.ghostnet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_anonymous")
    private boolean isAnonymous = false;

    @OneToMany(mappedBy = "reportedBy", cascade = CascadeType.ALL)
    private List<GhostNet> reportedNets = new ArrayList<>();

    @OneToMany(mappedBy = "salvager", cascade = CascadeType.ALL)
    private List<GhostNet> salvagingNets = new ArrayList<>();

    // Constructors
    public Person() {
    }

    public Person(String name, String phoneNumber, boolean isAnonymous) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isAnonymous = isAnonymous;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public List<GhostNet> getReportedNets() {
        return reportedNets;
    }

    public void setReportedNets(List<GhostNet> reportedNets) {
        this.reportedNets = reportedNets;
    }

    public List<GhostNet> getSalvagingNets() {
        return salvagingNets;
    }

    public void setSalvagingNets(List<GhostNet> salvagingNets) {
        this.salvagingNets = salvagingNets;
    }
}