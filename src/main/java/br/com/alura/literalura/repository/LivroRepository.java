package br.com.alura.literalura.repository;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT a FROM Livro l JOIN l.autor a WHERE a.autor = :autor")
    Autor buscarAutorPeloNome(String autor);

    @Query("SELECT a FROM Livro l JOIN l.autor a")
    List<Autor> buscarTodosAtores();

    @Query ("SELECT a FROM Livro l JOIN l.autor a WHERE a.anoNascimento <= :ano and a.anoFalecimento >= :ano")
    List<Autor> buscarAutoresVivosNoAno(Year ano);

    List<Livro> findByIdioma(String idioma);
}
