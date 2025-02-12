package com.example;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "degree_score", nullable = false)
    private Integer degreeScore; // Accepts values from 1 to 9

    @Column(name = "required_income", nullable = false)
    private Double requiredIncome; // Accepts numerical values for the income

    @Column(nullable = false, length = 100)
    private String city; // City where the program is located

    @Column(name = "remote", nullable = false)
    private Boolean remote = false; // Indicates if the program can be done remotely

    @Column(length = 255)
    private String description; // Short description of the program
    
    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Application> applications;
    
    @ManyToMany(mappedBy = "programs")
    private List<User> users;

    
    
    // Getters and setters
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
    
    public Integer getDegreeScore() {
        return degreeScore;
    }

    public void setDegreeScore(Integer degreeScore) {
        this.degreeScore = degreeScore;
    }

    public Double getRequiredIncome() {
        return requiredIncome;
    }

    public void setRequiredIncome(Double requiredIncome) {
        this.requiredIncome = requiredIncome;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getRemote() {
        return remote;
    }

    public void setRemote(Boolean remote) {
        this.remote = remote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
    
    
}
