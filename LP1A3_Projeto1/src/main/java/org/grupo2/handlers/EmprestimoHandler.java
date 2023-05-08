package org.grupo2.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.grupo2.modelos.Biblioteca;
import org.grupo2.modelos.Cliente;
import org.grupo2.modelos.Emprestimo;
import org.grupo2.modelos.Livro;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;

public class EmprestimoHandler implements HttpHandler {

    private static Integer proximoId = 2;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length == 3 && "emprestimos".equals(pathParts[1])) {
            Integer id = Integer.parseInt(pathParts[2]);
            if ("GET".equals(requestMethod)) {
                handleGetEmprestimo(exchange, id);
            } else if ("DELETE".equals(requestMethod)) {
                handleDeleteEmprestimo(exchange, id);
            } else if ("PUT".equals(requestMethod)) {
                handlePutEmprestimo(exchange, id);
            }
        } else if ("GET".equals(requestMethod)) {
            handleGetEmprestimos(exchange);
        } else if ("POST".equals(requestMethod)) {
            handlePostEmprestimo(exchange);
        } else {
            handleBadRequest(exchange, "Requisicao inválida: " + requestMethod + " " + path);
        }
    }

    // CREATE
    private void handlePostEmprestimo(HttpExchange exchange) throws Exception {
        Funcionario funcionario = new Funcionario();
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        ArrayList<String> arrayListEmprestimo = Emprestimo.arrayListFromJson(requestBody);
        int emprestimoId = Integer.parseInt(arrayListEmprestimo.get(0));
        Livro livro = Biblioteca.getLivros().get(Integer.parseInt(arrayListEmprestimo.get(1)));
        Cliente cliente = Biblioteca.getClientes().get(Integer.parseInt(arrayListEmprestimo.get(2)));
        Instant dataEmprestimo = Instant.parse(arrayListEmprestimo.get(3));
        Instant dataDevolucao = Instant.parse(arrayListEmprestimo.get(4));
        Emprestimo emprestimo = funcionario.realizarEmprestimo(emprestimoId, livro, cliente, dataEmprestimo, dataDevolucao);
        sendResponse(exchange, emprestimo.toJson(), 201);
    }

    // READ ALL
    private void handleGetEmprestimos(HttpExchange exchange) throws IOException {
        sendResponse(exchange, Biblioteca.listarEmprestimos().toString());
    }

    // READ
    private void handleGetEmprestimo(HttpExchange exchange, Integer id) throws IOException {
        Emprestimo emprestimo = Biblioteca.getEmprestimos().get(id);
        if (emprestimo == null) {
            handleNotFound(exchange, "Emprestimo não encontrado com o id " + id);
        } else {
            sendResponse(exchange, emprestimo.toJson());
        }
    }

    // UPDATE
    private void handlePutEmprestimo(HttpExchange exchange, Integer id) throws IOException {
        Emprestimo emprestimo = Biblioteca.getEmprestimos().get(id);
        if (emprestimo == null) {
            handleNotFound(exchange, "Emprestimo não encontrado com ID: " + id);
        } else {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            ArrayList<String> arrayListEmprestimo = Emprestimo.arrayListFromJson(requestBody);
            int emprestimoId = Integer.parseInt(arrayListEmprestimo.get(0));
            Livro livro = Biblioteca.getLivros().get(Integer.parseInt(arrayListEmprestimo.get(1)));
            Cliente cliente = Biblioteca.getClientes().get(Integer.parseInt(arrayListEmprestimo.get(2)));
            Instant dataEmprestimo = Instant.parse(arrayListEmprestimo.get(3));
            Instant dataDevolucao = Instant.parse(arrayListEmprestimo.get(4));
            Emprestimo emprestimoAtualizado = new Emprestimo(emprestimoId, livro, cliente, dataEmprestimo, dataDevolucao);
            emprestimo.setId(emprestimoAtualizado.getId());
            emprestimo.setLivro(emprestimoAtualizado.getLivro());
            emprestimo.setCliente(emprestimoAtualizado.getCliente());
            emprestimo.setDataEmprestimo(emprestimoAtualizado.getDataEmprestimo());
            emprestimo.setDataDevolucao(emprestimoAtualizado.getDataDevolucao());
            sendResponse(exchange, emprestimo.toJson());
        }
    }

    // DELETE
    private void handleDeleteEmprestimo(HttpExchange exchange, Integer id) throws IOException {
        Emprestimo emprestimo = Biblioteca.getEmprestimos().remove(id);
        if (emprestimo == null) {
            handleNotFound(exchange, "Emprestimo não encontrado com ID: " + id);
        } else {
            try {
                System.out.println("DELETE /emprestimos/" + id + ": Emprestimo deletado com sucesso.");
                sendResponse(exchange, "", 204);
            } catch (IOException e) {
                throw new IOException();
            }
        }
    }

    private void handleNotFound(HttpExchange exchange, String s) throws IOException {
        sendResponse(exchange, s, 404);
    }

    private void handleBadRequest(HttpExchange exchange, String s) throws IOException {
        sendResponse(exchange, s, 400);
    }

    private void sendResponse(HttpExchange exchange, String response, int rCode) throws IOException {
        long headerLength = (rCode == 404 || rCode == 400) ? response.length() : response.getBytes().length;
        if (rCode == 204) {
            headerLength = -1L;
        }
        exchange.sendResponseHeaders(rCode, headerLength);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        sendResponse(exchange, response, 200);
    }
}
