package com.sistema.aluno;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ALUNO_MATERIA")

public class AlunoMateria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int matricula;
	private String materia;
	private double notaFinal;

	public AlunoMateria() {
	}

	public AlunoMateria(int matricula, String materia, double notaFinal) {
		this.matricula = matricula;
		this.materia = materia;
		this.notaFinal = notaFinal;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public double getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(double notaFinal) {
		this.notaFinal = notaFinal;
	}

}
