package org.grupo2.modelos;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Emprestimo {
    private int id;
    private Livro livro;
    private Cliente cliente;
    private Instant dataEmprestimo;
    private Instant dataDevolucao;

    public Emprestimo(int id, Livro livro, Cliente cliente) {
        this.id = id;
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = Instant.now();
        this.dataDevolucao = dataEmprestimo.plus(Duration.ofDays(7));
    }

    public Emprestimo(int id, Livro livro, Cliente cliente, Instant dataEmprestimo, Instant dataDevolucao) {
        this.id = id;
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Instant getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Instant dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Instant getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Instant dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo that = (Emprestimo) o;
        return id == that.id && Objects.equals(livro, that.livro) && Objects.equals(cliente, that.cliente) && Objects.equals(dataEmprestimo, that.dataEmprestimo) && Objects.equals(dataDevolucao, that.dataDevolucao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, livro, cliente, dataEmprestimo, dataDevolucao);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", livro=" + livro +
                ", cliente=" + cliente +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }

    public String toJson() {
        return "{\"id\": " + this.getId() + ", \"livroId\": " + this.getLivro().getId() +
                ", \"clienteId\": " + this.getCliente().getId() + ", \"dataEmprestimo\": \"" + this.getDataEmprestimo().toString() +
                "\", \"dataDevolucao\": \"" + this.getDataDevolucao().toString() + "\"}";
    }

    public static ArrayList<String> arrayListFromJson(String requestBody) {
        String requestBodyClean = requestBody.replace("{", "").replace("}","");
        String[] splitProperties = requestBodyClean.split(",");
        String jsonId = splitProperties[0].split(":")[1].trim().replace("\"","");
        String jsonLivroId = splitProperties[1].split(":")[1].trim().replace("\"","");
        String jsonClienteId = splitProperties[2].split(":")[1].trim().replace("\"","");
        String s3 = splitProperties[3].split(":")[3];
        String s4 = splitProperties[4].split(":")[3];
        String Z3 = s3.charAt(s3.length() - 2) == 'Z' ? "" : "Z";
        String Z4 = s4.charAt(s4.length() - 2) == 'Z' ? "" : "Z";
        String jsonDataEmprestimo = (splitProperties[3].split(":")[1] + ":" + splitProperties[3].split(":")[2] + ":" + splitProperties[3].split(":")[3]).replace("\"", "").trim() + Z3;
        String jsonDataDevolucao = (splitProperties[4].split(":")[1] + ":" + splitProperties[4].split(":")[2] + ":" + splitProperties[4].split(":")[3]).replace("\"", "").trim() + Z4;
        return new ArrayList<>(Arrays.asList(jsonId, jsonLivroId, jsonClienteId, jsonDataEmprestimo, jsonDataDevolucao));
    }
}

