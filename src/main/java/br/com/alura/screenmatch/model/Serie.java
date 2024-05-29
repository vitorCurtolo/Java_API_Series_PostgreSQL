package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultaChatGPT;
import br.com.alura.screenmatch.service.traducao.Tradutor;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
        //this.sinopse = ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim();
        this.sinopse = Tradutor.traduzirInglesParaPortugues(dadosSerie.sinopse()).trim();
    }

    public Serie() {

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

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("===========================================\n");
        sb.append("           Detalhes da Série                \n");
        sb.append("===========================================\n");
        sb.append(String.format("Gênero: %s\n", genero));
        sb.append(String.format("Título: %s\n", titulo));
        sb.append(String.format("Total de Temporadas: %s\n", totalTemporadas));
        sb.append(String.format("Total de Episódios: %s\n", episodios.size()));
        sb.append(String.format("Avaliação: %s\n", avaliacao));
        sb.append(String.format("Atores: %s\n", atores));
        sb.append(String.format("Poster: %s\n", poster));
        sb.append(String.format("Sinopse: %s\n", sinopse));

        return sb.toString();
    }
}
