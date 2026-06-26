package com.sistema.aluno;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "REGISTRO_ALUNO")
public class RegistroAluno {
	@Id
	private int matricula;
	private String nome;
	private String sexo;
	private String dataNasc;

	public RegistroAluno() {
	}

	public RegistroAluno(int matricula, String nome, String sexo, String dataNasc) {
		this.matricula = matricula;
		this.nome = nome;
		this.sexo = sexo;
		this.dataNasc = dataNasc;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

}
