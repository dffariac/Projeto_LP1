package org.grupo2.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import org.grupo2.modelos.Biblioteca;
import org.grupo2.modelos.Livro;

public class LivroHandler implements HttpHandler{
	  private static Integer proximoId = 2;

	    public LivroHandler() {
	    }

	    @Override
	    public void handle(HttpExchange exchange) throws IOException {
	        String requestMethod = exchange.getRequestMethod();
	        String path = exchange.getRequestURI().getPath();
	        String[] pathParts = path.split("/");
	        if (pathParts.length == 3 && "livros".equals(pathParts[1])) {
	            Integer id = Integer.parseInt(pathParts[2]);
	            if ("GET".equals(requestMethod)) {
	                handleGetLivro(exchange, id);
	            } else if ("DELETE".equals(requestMethod)) {
	                handleDeleteLivro(exchange, id);
	            }
	        } else if ("GET".equals(requestMethod)) {
	            handleGetLivros(exchange);
	        } else if ("POST".equals(requestMethod)) {
	            handlePostLivro(exchange);
	        } else {
	            handleBadRequest(exchange, "Requisicao invalida: " + requestMethod + " " + path);
	        }
	    }

	    private void handleGetLivros(HttpExchange exchange) throws IOException {
	        StringBuilder response = new StringBuilder();
	        response.append("[");
	        for (Livro livro : Biblioteca.getLivros().values()) {
	            response.append(livro.toJson());
	            response.append(",");
	        }
	        if (Biblioteca.getLivros().size() > 0) {
	            response.deleteCharAt(response.length() - 1);
	        }
	        response.append("]");
	        sendResponse(exchange, response.toString());
	    }

	    private void handleGetLivro(HttpExchange exchange, Integer id) throws IOException {
	        Livro livro = Biblioteca.getLivros().get(id);
	        if (livro == null) {
	            handleNotFound(exchange, "Livro não encontrado com o id " + id);
	        } else {
	            sendResponse(exchange, livro.toJson());
	        }
	    }

	    private void handleNotFound(HttpExchange exchange, String s) throws IOException {
	        sendResponse(exchange, s, 404);
	    }

	    private void handlePostLivro(HttpExchange exchange) throws IOException {
	        String requestBody = new String(exchange.getRequestBody().readAllBytes());
	        Livro livro = Livro.fromJson(requestBody);
	        livro.setId(proximoId++);
	        Biblioteca.getLivros().put(livro.getId(), livro);
	        sendResponse(exchange, livro.toJson(), 201);
	    }

	    private void handleDeleteLivro(HttpExchange exchange, Integer id) {
	    }

	    private void handleBadRequest(HttpExchange exchange, String s) {
	    }

	    private void sendResponse(HttpExchange exchange, String response, int rCode) throws IOException {
	        long headerLength = rCode != 404 ? response.getBytes().length : response.length();
	        exchange.sendResponseHeaders(rCode, headerLength);
	        OutputStream os = exchange.getResponseBody();
	        os.write(response.getBytes());
	        os.close();
	    }

	    private void sendResponse(HttpExchange exchange, String response) throws IOException {
	        sendResponse(exchange, response, 200);
	    }	    
}