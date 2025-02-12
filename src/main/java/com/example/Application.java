package com.example;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = true)
    private Program program;

    @Column(name = "degree_score", nullable = false)
    private Integer degreeScore;

    @Column(name = "income", nullable = false)
    private Double income;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(name = "remote", nullable = false)
    private Boolean remote;
    
    @Column(name = "accepted",nullable = false)
    private Boolean accepted = false;
    
    @Column(name = "application_date_time", nullable = false)
    private LocalDateTime applicationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Integer getDegreeScore() {
        return degreeScore;
    }

    public void setDegreeScore(Integer degreeScore) {
        this.degreeScore = degreeScore;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
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

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public LocalDateTime getApplicationDateTime() {
		return applicationDateTime;
	}

	public void setApplicationDateTime(LocalDateTime applicationDateTime) {
		this.applicationDateTime = applicationDateTime;
	}
    
	
    
}