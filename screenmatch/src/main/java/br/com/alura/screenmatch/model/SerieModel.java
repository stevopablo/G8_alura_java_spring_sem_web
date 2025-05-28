package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignora o que não encontrar
@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieModel(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("Released") String lancamento,
                         @JsonAlias("Year") String ano,
                         @JsonAlias("Rated") String avaliacao,
                         @JsonAlias("Poster") String poster) {
}
