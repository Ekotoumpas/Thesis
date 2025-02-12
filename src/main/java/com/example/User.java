package com.example;

import jakarta.persistence.*;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique=true, length=45)
	private String email;
	
	@Column(nullable = false, length=60)
	private String password;
	
	@Column(nullable = false, length=20)
	private String firstname;
	
	@Column(nullable = false, length=20)
	private String lastname;
	
	@Column(nullable = false, length=20)
	private String role = "ROLE_USER"; // Νέο πεδίο για το ρόλο του χρήστη
	
	 @Column(name = "degree_score", nullable = true)
	    private Integer degreeScore; // Accepts values from 1 to 9

	    @Column(name = "income", nullable = true)
	    private Double income; // Accepts numerical values for income

	    @Column(name = "city", nullable = true, length = 100)
	    private String city; // City where the user is located

	    @Column(name = "remote", nullable = true)
	    private Boolean remote; // Indicates if the user wants remote programs
	    
	
	  @ManyToMany
	 @JoinTable(
	        name = "user_programs",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "program_id")
	    )
	   
	    private List<Program> programs;
	  
	  @OneToMany(mappedBy = "user")
	    private List<Application> applications;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	
	 public List<Program> getPrograms() {
	        return programs;
	    }

	    public void setPrograms(List<Program> programs) {
	        this.programs = programs;
	    }
	    
	    public String getRole() {
		    return role;
		}

		public void setRole(String role) {
		    this.role = role;
		}
		public List<Application> getApplications() {
			return applications;
		}
		public void setApplications(List<Application> applications) {
			this.applications = applications;
		}
	
		
	
}

