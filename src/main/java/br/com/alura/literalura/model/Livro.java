package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    private String idioma;
    private Integer numeroDownload;


    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.autor = new Autor(dadosLivro.autor().get(0));
        this.idioma = dadosLivro.idioma().get(0);
        this.numeroDownload = dadosLivro.numeroDownload();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Integer numeroDownload) {
        this.numeroDownload = numeroDownload;
    }


    @Override
    public String toString() {
        return  "------------------ LIVRO ------------------" +
                "\nTítulo:             " + this.titulo +
                "\nAutor:              " + this.autor.getAutor() +
                "\nIdioma:             " + this.idioma +
                "\nNúmero de Download: " + this.numeroDownload +
                "\n-------------------------------------------\n";
    }
}
