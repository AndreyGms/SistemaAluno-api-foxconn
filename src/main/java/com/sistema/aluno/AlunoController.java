package com.sistema.aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/escolinha-foxconn")
public class AlunoController {

	@Autowired
	private RegistroAlunoRepository alunoRepository;

	@Autowired
	private AlunoMateriaRepository materiaRepository;

	@GetMapping("/alunos")
	public List<DadosAluno> getAlunos() {
		List<RegistroAluno> alunos = alunoRepository.findAll();
		List<DadosAluno> relatorio = new ArrayList<>();

		for (RegistroAluno aluno : alunos) {
			List<AlunoMateria> materiasDoAluno = materiaRepository.findByMatricula(aluno.getMatricula());
			relatorio.add(new DadosAluno(aluno, materiasDoAluno));
		}
		return relatorio;
	}

	@PostMapping("/alunos")
	public DadosAluno addAluno(@RequestBody DadosAluno dados) {
		RegistroAluno alunoSalvo = alunoRepository.save(dados.getAluno());
		List<AlunoMateria> materiasSalvas = new ArrayList<>();

		if (dados.getMaterias() != null) {
			for (AlunoMateria materia : dados.getMaterias()) {
				materia.setMatricula(alunoSalvo.getMatricula());
				materiasSalvas.add(materiaRepository.save(materia));
			}
		}

		return new DadosAluno(alunoSalvo, materiasSalvas);
	}

	@GetMapping("/notas")
	public List<AlunoMateria> listarNotas() {
		return materiaRepository.findAll();
	}

	@PostMapping("/notas")
	public AlunoMateria cadastrarNota(@RequestBody AlunoMateria novaNota) {
		return materiaRepository.save(novaNota);
	}

	@PutMapping("/alunos/{matricula}")
	public DadosAluno editAluno(@PathVariable int matricula, @RequestBody DadosAluno dados) {
		RegistroAluno alunoAtualizado = alunoRepository.findById(matricula).map(aluno -> {
			aluno.setNome(dados.getAluno().getNome());
			aluno.setSexo(dados.getAluno().getSexo());
			aluno.setDataNasc(dados.getAluno().getDataNasc());
			return alunoRepository.save(aluno);
		}).orElse(null);

		if (alunoAtualizado != null && dados.getMaterias() != null) {
			for (AlunoMateria materiaDados : dados.getMaterias()) {
				List<AlunoMateria> materiasDoBanco = materiaRepository.findByMatricula(matricula);
				AlunoMateria materiaExistente = null;
				
				for (AlunoMateria m : materiasDoBanco) {
					if (m.getMateria().equalsIgnoreCase(materiaDados.getMateria())) {
						materiaExistente = m;
						break;
					}
				}

				if (materiaExistente != null) {
					materiaExistente.setNotaFinal(materiaDados.getNotaFinal());
					materiaRepository.save(materiaExistente);
				} else {
					materiaDados.setMatricula(matricula);
					materiaRepository.save(materiaDados);
				}
			}
		}

		List<AlunoMateria> materiasAtualizadas = materiaRepository.findByMatricula(matricula);
		return new DadosAluno(alunoAtualizado, materiasAtualizadas);
	}

	@DeleteMapping("/notas/{id}")
	public void deletarMateria(@PathVariable int id) {
		materiaRepository.deleteById(id);
	}

	@GetMapping("/notas/maiorq8")
	public List<DadosAluno> notasMaior8() {
		List<RegistroAluno> todosOsAlunos = alunoRepository.findAll();
		List<DadosAluno> resultadoFiltrado = new ArrayList<>();

		for (RegistroAluno aluno : todosOsAlunos) {
			List<AlunoMateria> materiasDoAluno = materiaRepository.findByMatricula(aluno.getMatricula());
			List<AlunoMateria> materiasNotasAltas = new ArrayList<>();

			for (AlunoMateria nota : materiasDoAluno) {
				if (nota.getNotaFinal() > 8.0) {
					materiasNotasAltas.add(nota);
				}
			}

			if (!materiasNotasAltas.isEmpty()) {
				resultadoFiltrado.add(new DadosAluno(aluno, materiasNotasAltas));
			}
		}
		
		return resultadoFiltrado;
	}
}

class DadosAluno {
	private RegistroAluno aluno;
	private List<AlunoMateria> materias;

	public DadosAluno() {
	}

	public DadosAluno(RegistroAluno aluno, List<AlunoMateria> materias) {
		this.aluno = aluno;
		this.materias = materias;
	}

	public RegistroAluno getAluno() {
		return aluno;
	}

	public void setAluno(RegistroAluno aluno) {
		this.aluno = aluno;
	}

	public List<AlunoMateria> getMaterias() {
		return materias;
	}

	public void setMaterias(List<AlunoMateria> materias) {
		this.materias = materias;
	}
}