package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.EpisodioModel;
import br.com.alura.screenmatch.model.SerieModel;
import br.com.alura.screenmatch.model.TemporadaModel;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String BASE_URL = "http://www.omdbapi.com/?t=";
    private final String KEY = "&apikey=6a5c2ac3";
    private final ConverteDados conversor = new ConverteDados();

    public void ExibirMenu() throws IOException, InterruptedException {
        System.out.println("Enter Movie/Serie Title to search: ");
        var name = input.nextLine().replace(" ", "+").trim();
        var json = consumoAPI.ObterDados(BASE_URL + name + KEY);
        SerieModel data = conversor.obterDados(json, SerieModel.class);
        System.out.println("data = " + data);

        List<TemporadaModel> temporadaList = new ArrayList<>();
        for (int i = 1; i <= data.totalTemporadas(); i++) {
            json = consumoAPI.ObterDados(BASE_URL + name + "&season=" + i  + KEY);
            TemporadaModel dataSeason = conversor.obterDados(json, TemporadaModel.class);
            temporadaList.add(dataSeason);
        }
        //temporadaList.forEach(System.out::println);

        temporadaList.forEach(t ->
        t.episodios().forEach(episodioModel -> System.out.println("episodioModel.titulo() = " + episodioModel.titulo())));

        List<EpisodioModel> episodios = temporadaList.stream()
                                        .flatMap(t -> t.episodios()
                                        .stream()).toList();

        //episodios.forEach(System.out::println);
        System.out.println("Top 5: ");
        episodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodioModel::avaliacao).reversed())
                .limit(5)
                .toList()
                .forEach(System.out::println);

        List<Episodio> episodioList = temporadaList.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                        .collect(Collectors.toList());


        episodioList.forEach(System.out::println);

        System.out.println("Enter year: ");
        var year = input.nextInt();
        LocalDate dateYear = LocalDate.of(year, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodioList.stream()
                .filter(e -> e.getLancamento() != null && e.getLancamento().isAfter(dateYear))
                .forEach(e -> System.out.println(
                        "Season: " + e.getTemporada() +
                                "episode: " + e.getTitulo() +
                                "released: " + e.getLancamento().format(formatador)
                ));

        Map<Integer, Double> avalicaoPorTemporada = episodioList.stream()
                    .filter(e -> e.getAvaliacao() > 0.0)
                    .collect(Collectors.groupingBy(Episodio::getTemporada,
                            Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println("avalicaoPorTemporada = " + avalicaoPorTemporada);

        DoubleSummaryStatistics estatistica = episodioList.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("estatistica = " + estatistica);
        System.out.println("Media: "+ estatistica.getAverage());
        System.out.println("Pior episodio: "+ estatistica.getMin());
        System.out.println("Melhor episodio: "+ estatistica.getMax());
        System.out.println("Quantidade: "+ estatistica.getCount());

//        System.out.println("Search for an episode: ");
//        String trechoTitulo = input.next();
//        Optional<Episodio> searchEpisode = episodioList.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if (searchEpisode.isPresent()) {
//        System.out.println("season: "+ searchEpisode.get().getTemporada());
//        } else {
//            System.out.println("Episode not found");
//        }
    }
}