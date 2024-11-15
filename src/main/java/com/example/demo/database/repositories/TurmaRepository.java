package com.example.demo.database.repositories;

import com.example.demo.database.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}