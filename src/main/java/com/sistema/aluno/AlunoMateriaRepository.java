package com.sistema.aluno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoMateriaRepository extends JpaRepository<AlunoMateria, Integer> {

	List<AlunoMateria> findByMatricula(int matricula);

}
