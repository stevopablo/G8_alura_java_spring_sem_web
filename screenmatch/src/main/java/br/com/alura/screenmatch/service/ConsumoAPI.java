package br.com.alura.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String ObterDados(String endereco) throws IOException, InterruptedException {
        try {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                                .newBuilder()
                                .uri(URI.create(endereco))
                                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e);
        }

    }
}
