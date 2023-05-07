package org.grupo2.modelos;

import java.util.Map;
import java.util.Scanner;

import org.grupo2.interfaces.GerenciamentoDeLivros;
import org.grupo2.interfaces.GerenciamentoDeUsuarios;

import static org.grupo2.modelos.Biblioteca.usuario;

public class Funcionario extends Usuario implements GerenciamentoDeUsuarios, GerenciamentoDeLivros {

    private int id;
    private int keychave = 0;

        Map<Integer, Usuario>bdUsuario = Biblioteca.getUsuario();
        Map<Integer, Livro> bdLivro = Biblioteca.getLivros();
        Map<Integer, Emprestimo> bdEmprestimo = Biblioteca.getEmprestimos();

        //construtor
    public Funcionario(int id, String nome, String cpf, String endereco, String email, String senha) {
        super(nome, cpf, endereco, email, senha);
        this.id = id;
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos da classe
    
    public void realizarEmprestimo(int keychave, Cliente cliente, Livro livro) {
        keychave++;
            if (livro.getNumExemplaresDisponiveis() > 0) {
                Emprestimo emprestimo = new Emprestimo(keychave, livro, cliente);
                    bdEmprestimo.put(keychave, emprestimo);
                        livro.setNumExemplaresDisponiveis(livro.getNumExemplaresDisponiveis() -1);
            }
    }
    public void realizarDevolucao(){

    }

    @Override
    public void emprestarLivro(int idEmprestimo, Livro livro, Cliente cliente) throws Exception{
        super.emprestarLivro(idEmprestimo, livro, cliente);
        
    }

    @Override
    public void devolverLivro(Livro livro) throws Exception{
        super.devolverLivro(livro);
    }

     @Override
    public void reservarLivro(int id, Livro livro, Cliente cliente) throws Exception{
        super.reservarLivro(id, livro, cliente);
    }

    @Override
    public void cancelarReserva(Livro livro, Cliente cliente) throws Exception{
        super.cancelarReserva(livro, cliente);
     }

    @Override
    public Livro atualizarLivro(Livro livro){
        Livro atualizacaoLivro = buscarLivro(livro);

        atualizacaoLivro.setTitulo(livro.getTitulo());
        atualizacaoLivro.setAutor(livro.getAutor());
        atualizacaoLivro.setEditora(livro.getEditora());
        atualizacaoLivro.setAnoPublicacao(livro.getAnoPublicacao());
        atualizacaoLivro.setNumExemplares(livro.getNumExemplares());
        atualizacaoLivro.setNumExemplaresDisponiveis(livro.getNumExemplaresDisponiveis());

        return atualizacaoLivro;


    public void cadastrarLivro ( int id, String titulo, String autor, String editora, int anoPublicacao, int numExemplares, int numExemplaresDisponiveis){
        Livro livro = new Livro(id, titulo, autor, editora, anoPublicacao, numExemplares, numExemplaresDisponiveis);
        keychave++;

        for (int i = 0; i < bdLivro.size(); i++) {
            if (livro.equals(bdLivro.get(i))) {
                System.out.println("Livro já existe");
                break;
            } else {
                bdLivro.put(keychave, livro);
                System.out.println("Livro cadastrado com sucesso");
            }
        }
    }

    @Override
    public void removerLivro(Livro livro){
        for (int i=0;i < bdLivro.size(); i++) {
            if(livro.equals(bdLivro.get(i))){
              bdLivro.remove(null, livro);
            }
          }
          throw new UnsupportedOperationException("Livro não encontrado");
    }

    @Override
    public Livro buscarLivro (Livro livro){
            try {
                for (int i = 0; i < bdLivro.size(); i++) {
                    System.out.println("Id: " + bdLivro.get(i).getId() + "\n Nome: " +
                            bdLivro.get(i).getTitulo() + "\n Disponiveis:" + bdLivro.get(i).getNumExemplaresDisponiveis());

                }
            } catch (Exception erroBuscaLivro) {
                System.out.println("Livro não encontrado");
            }
            return livro;
    }

    @Override
    public void cadastrarUsuario( Usuario usuario ){
        for (int i = 0; i < bdUsuario.size(); i++) {
            if (usuario.equals(bdUsuario.get(i))) {
                System.out.println("Usuário já existe no sistema");
                break;
            } else {
                bdUsuario.put(null, usuario);
                break;
            }
        }
    }

    @Override
    public void atualizarUsuario(Usuario usuario){

            }
        }
    }

    @Override
    public void removerUsuario(Usuario usuario){
        try {
            for (int i = 0; i < bdUsuario.size(); i++) {
                if (usuario.getNome().equals(bdUsuario.get(i).getNome())) {
                    System.out.println("Usuario " + bdUsuario.get(i).getNome() + "removido");
                    bdUsuario.remove(null, usuario);
                    break;
                }
            }
        }
        catch (Exception erro5) {
            System.out.println("Usuário não encontrado");
        }
    }

    @Override
    public Usuario buscarUsuario(Usuario usuario){
        try {
            for (int i = 0; i < bdUsuario.size(); i++) {
                if (usuario.equals(bdUsuario.get(i))) {
                    System.out.println("Usuário encontrado!" + "\n Nome:" + bdUsuario.get(i).getNome() +
                            "\n Email :" + bdUsuario.get(i).getEmail());
                }
            }
        } catch (Exception error) {
            System.out.println("Usuaŕio não encontrado");
        }

    }
    }
    
    public String toJson() {
        return "{\"id\": " + this.getId() + ", \"nome\": \"" + this.getNome() +
                "\", \"cpf\": \"" + this.getCpf() + "\", \"endereco\": \"" + this.getEndereco() +
                "\", \"email\": \"" + this.getEmail() + "\"}";
    }

    public static Funcionario fromJson(String requestBody) {
        String requestBodyClean = requestBody.replace("{", "").replace("}","");
        String[] splitProperties = requestBodyClean.split(",");
        int jsonId = Integer.parseInt(splitProperties[0].split(":")[1].trim());
        String jsonNome = splitProperties[1].split(":")[1].trim().replace("\"","");
        String jsonCpf = splitProperties[2].split(":")[1].trim().replace("\"","");
        String jsonEndereco = splitProperties[3].split(":")[1].trim().replace("\"","");
        String jsonEmail = splitProperties[4].split(":")[1].trim().replace("\"","");
        String jsonSenha = splitProperties[5].split(":")[1].trim().replace("\"","");
        return new Funcionario(jsonId, jsonNome, jsonCpf, jsonEndereco, jsonEmail, jsonSenha);
    }

        }
