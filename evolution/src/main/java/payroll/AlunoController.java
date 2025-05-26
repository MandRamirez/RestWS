package payroll;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoRepository repository;

    public AlunoController(AlunoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Aluno> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Aluno novo(@RequestBody Aluno aluno) {
        return repository.save(aluno);
    }

    @GetMapping("/{id}")
    public Aluno buscar(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    @PutMapping("/{id}")
    public Aluno atualizar(@RequestBody Aluno alunoAtualizado, @PathVariable Long id) {
        return repository.findById(id)
                .map(aluno -> {
                    aluno.setNome(alunoAtualizado.getNome());
                    aluno.setMatricula(alunoAtualizado.getMatricula());
                    aluno.setCurso(alunoAtualizado.getCurso());
                    return repository.save(aluno);
                })
                .orElseGet(() -> {
                    alunoAtualizado.setId(id);
                    return repository.save(alunoAtualizado);
                });
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
