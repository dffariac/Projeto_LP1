package org.grupo2.consumidores;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ConsumidorEmprestimo {
    private static final String BASE_URL = "http://localhost:8080/emprestimos";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        // POST /emprestimos
        String requestBody = "{\"id\": 2, \"livroId\":  2, \"clienteId\": 2, " +
                "\"dataEmprestimo\": \"2023-05-07T16:34:45.314250200\", \"dataDevolucao\": \"2023-05-14T16:34:45.314250200\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST /emprestimos: " + response.body());

        // GET /emprestimos
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /emprestimos: " + response.body());

        // GET /emprestimos/{id}
        int id = 1;
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET /emprestimos/" + id + ": " + response.body());

        // PUT /emprestimos/{id}
        requestBody = "{\"id\": 3, \"livroId\":  3, \"clienteId\": 2, " +
                "\"dataEmprestimo\": \"2023-05-07T16:37:43.996462500Z\", \"dataDevolucao\": \"2023-05-14T16:37:43.996462500\"}";
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("PUT /emprestimos/" + id + ": " + response.body());

        // DELETE /emprestimos/{id}
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
