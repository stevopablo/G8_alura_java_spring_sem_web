package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.SerieModel;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String BASE_URL = "http://www.omdbapi.com/?t=";
    private final String KEY = "&apikey=6a5c2ac3";
    private final ConverteDados conversor = new ConverteDados();

    public void ExibirMenu() throws IOException, InterruptedException {
        System.out.println("Enter Movie/Serie Title to search: ");
        var name = input.nextLine().replace(" ", "+");
        var json = consumoAPI.ObterDados(BASE_URL + name + KEY);
        SerieModel data = conversor.obterDados(json, SerieModel.class);
        System.out.println("data = " + data);
    }
}
