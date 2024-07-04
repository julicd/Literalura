package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private LivroRepository repository;
    private ConsumoAPI consumo = new ConsumoAPI();
    private Scanner leitura = new Scanner(System.in);
    private ConverteDados converteDados = new ConverteDados();
    private final String ENDERECOBUSCA = "https://gutendex.com/books/?search=";
    private List<DadosLivro> dadosLivros = new ArrayList<>();

    public Principal(LivroRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() throws JsonProcessingException {
        int opcao;
        String menu = """
                \n1 - Buscar nome do livro
                2 - Listar livros registrados
                3 - Listar Autores
                4 - Listar Autores vivos em determinado ano
                5 - Listar Livros em determinado Idioma\n         
                0 - Sair
                    """;

        do {
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscaLivroNaWeb();
                    break;
                case 2:
                    listaLivros();
                    break;
                case 3:
                    listaAutores();
                    break;
                case 4:
                    listaAutoresVivoPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;

            }
        } while (opcao != 0);


    }

    private void listaAutoresVivoPorAno() {

        try {
            System.out.println("Digite o ano:");
            int ano = leitura.nextInt();
            leitura.nextLine();
            List<Autor> autores = repository.buscarAutoresVivosNoAno(Year.of(ano));
            if (autores.size() > 0) {
                autores.forEach(System.out::println);
            }else {
                System.out.println("Nenhum autor cadastrado estava vivo no ano informado");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            leitura.nextLine();
        }
    }


    private void buscaLivroNaWeb() throws JsonProcessingException {
        System.out.println("Informe o nome do livro que deseja:");
        var livroBuscado = leitura.nextLine();
        var json = consumo.obterDados(ENDERECOBUSCA + livroBuscado.replace(" ", "+"));
        var newJson = converteDados.extraiObjetoJson(json, "results");
        System.out.println(newJson);
        dadosLivros = converteDados.obterLista(newJson, DadosLivro.class);

        if (dadosLivros.size() > 0) {
            Livro livro = new Livro(dadosLivros.get(0));

            Autor autor = repository.buscarAutorPeloNome(livro.getAutor().getAutor());
            if (autor != null) {
                livro.setAutor(null);
                repository.save(livro);
                livro.setAutor(autor);
            }
            livro = repository.save(livro);
        } else {
            System.out.println("Livro não encontrado");
        }

    }

    private void listaLivros() {
        List<Livro> livros = repository.findAll();
        livros.forEach(System.out::println);
    }

    private void listaAutores() {
        List<Autor> autors = repository.buscarTodosAtores();
        autors.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Digite o idioma para busca
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        String idioma = leitura.nextLine();
        List<Livro> livros = repository.findByIdioma(idioma);
        if (!livros.isEmpty()){
            livros.forEach(System.out::println);
        }else{
            System.out.println("Não exite livros nesse idioma cadastrado");
        }
    }

}
