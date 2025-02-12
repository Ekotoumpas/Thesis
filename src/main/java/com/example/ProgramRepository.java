package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProgramRepository extends JpaRepository<Program, Long> {
	
	@Query("SELECT u FROM Program u WHERE u.id = ?1")
	Program findByID(String Id);
}

