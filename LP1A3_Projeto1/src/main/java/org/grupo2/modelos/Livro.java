package org.grupo2.modelos;

import java.util.Objects;

public class Livro {
    public Object Emprestimo;
    private int id;
    private String titulo;
    private String autor;
    private String editora;
    private int anoPublicacao;
    private int numExemplares;
    private int numExemplaresDisponiveis;

    public Livro(int id, String titulo, String autor, String editora, int anoPublicacao, int numExemplares, int numExemplaresDisponiveis) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.anoPublicacao = anoPublicacao;
        this.numExemplares = numExemplares;
        this.numExemplaresDisponiveis = numExemplaresDisponiveis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public int getNumExemplares() {
        return numExemplares;
    }

    public void setNumExemplares(int numExemplares) {
        this.numExemplares = numExemplares;
    }

    public int getNumExemplaresDisponiveis() {
        return numExemplaresDisponiveis;
    }

    public void setNumExemplaresDisponiveis(int numExemplaresDisponiveis) {
        this.numExemplaresDisponiveis = numExemplaresDisponiveis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return id == livro.id && anoPublicacao == livro.anoPublicacao && numExemplares == livro.numExemplares && numExemplaresDisponiveis == livro.numExemplaresDisponiveis && Objects.equals(titulo, livro.titulo) && Objects.equals(autor, livro.autor) && Objects.equals(editora, livro.editora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor, editora, anoPublicacao, numExemplares, numExemplaresDisponiveis);
    }

    
    public Emprestimo emprestar(Livro livro, Cliente cliente) throws Exception{
        //Executa as ações necessarias para realizar um empréstimo
        //checa se o numero de exemplares disponiveis é maior q 0
        if (livro.getNumExemplaresDisponiveis() > 0){
            //reduz o numero de exemplares disponiveis
            livro.setNumExemplaresDisponiveis(livro.getNumExemplaresDisponiveis() - 1);
            //cria objeto emprestimo
            Emprestimo emprestimo = new Emprestimo(id, livro, cliente);
            return emprestimo;
        }
        else{
            throw new Exception("Não há exemplares disponíveis para empréstimo.");
        }
    }
    

    public static boolean devolver(Livro livro) throws Exception {
        if (livro.getNumExemplaresDisponiveis() < livro.getNumExemplares()) {
            livro.setNumExemplaresDisponiveis(livro.getNumExemplaresDisponiveis() + 1);
            return true;
        } else {
            throw new Exception("Todos os livros já estão na biblioteca");
        }
    }

    public static boolean livroDisponivel(Livro livro) {
        return livro.getNumExemplaresDisponiveis() > 0;
    }

    public String toJson() {
        return "{\"id\": " + this.getId() + ", \"titulo\": \"" + this.getTitulo() +
                "\", \"autor\": \"" + this.getAutor() + "\", \"editora\": \"" + this.getEditora() +
                "\", \"anoPublicacao\": " + this.getAnoPublicacao() + ", \"numExemplares\": " + this.getNumExemplares() +
                ", \"numExemplaresDisponiveis\": " + this.getNumExemplaresDisponiveis() + "}";
    }

    public static Livro fromJson(String requestBody) {
        String requestBodyClean = requestBody.replace("{", "").replace("}","");
        String[] splitProperties = requestBodyClean.split(",");
        int jsonId = Integer.parseInt(splitProperties[0].split(":")[1].trim());
        String jsonTitulo = splitProperties[1].split(":")[1].trim().replace("\"", "");
        String jsonAutor = splitProperties[2].split(":")[1].trim().replace("\"", "");
        String jsonEditora = splitProperties[3].split(":")[1].trim().replace("\"", "");
        int jsonAnoPublicacao = Integer.parseInt(splitProperties[4].split(":")[1].trim());
        int jsonNumExemplares = Integer.parseInt(splitProperties[5].split(":")[1].trim());
        int jsonNumExemplaresDisponiveis = Integer.parseInt(splitProperties[6].split(":")[1].trim());
        return new Livro (jsonId, jsonTitulo, jsonAutor, jsonEditora, jsonAnoPublicacao,
                jsonNumExemplares, jsonNumExemplaresDisponiveis);
    }
}
