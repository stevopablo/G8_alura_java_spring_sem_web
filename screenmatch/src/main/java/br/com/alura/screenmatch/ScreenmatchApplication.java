package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.SerieModel;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

		// String url = "http://www.omdbapi.com/?t=" + movie.replace(" ", "+") + "&apikey=6a5c2ac3";
	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		ConverteDados converteDados = new ConverteDados();

		String url = "http://www.omdbapi.com/?t=cars&apikey=6a5c2ac3";
		var json = consumoAPI.ObterDados(url);

		SerieModel dados = converteDados.obterDados(json, SerieModel.class);
		System.out.println("dados = " + dados);
	}

}
