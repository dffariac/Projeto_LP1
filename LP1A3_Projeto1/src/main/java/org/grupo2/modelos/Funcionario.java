package org.grupo2.modelos;

import java.util.Map;

import org.grupo2.interfaces.GerenciamentoDeLivros;
import org.grupo2.interfaces.GerenciamentoDeUsuarios;

public class Funcionario extends Usuario implements GerenciamentoDeUsuarios, GerenciamentoDeLivros {
    
   
    private int id;
    Map<Integer, Usuario>bdUsuario = Biblioteca.getUsuario();
    Map<Integer, Livro> bdLivro = Biblioteca.getLivros();
     //construtor
    public Funcionario(int id, String nome, String cpf, String endereco, String email, String senha) {
        super(nome, cpf, endereco, email, senha);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    public void realizarEmprestimo(){
        Livro.emprestar();
    }

    public void realizarDevolucao(){
        devolverLivro();
    }

    @Override
    public void emprestarLivro(int idEmprestimo, Livro livro, Cliente cliente) throws Exception{
        super.emprestarLivro(idEmprestimo, livro, cliente);
        
    }

    @Override
    public void devolverLivro(Livro livro) throws Exception{
        super.devolverLivro(livro);
    }

    // @Override
    // public void reservarLivro(int id, Livro livro, Cliente cliente) throws Exception{
    //     super.reservarLivro(id, livro, cliente);
    // }

    // @Override
    // public void cancelarReserva(Livro livro, Cliente cliente) throws Exception{
    //     super.cancelarReserva(livro, cliente);
    // }

    @Override
    public void cadastrarLivro(int id, String titulo, String autor, String editora, int anoPublicacao, int numExemplares, int numExemplaresDisponiveis){
        Livro livro = new Livro(id, titulo, autor, editora, anoPublicacao, numExemplares, numExemplaresDisponiveis);
        //Biblioteca.getLivros().put(48, livro);
        //receber dados do livro
        //Salvar livro no mapa de dados
    }

    @Override
    public void atualizarLivro(Livro livro){
        Biblioteca.getLivros().put(48, livro);
        //Adicionar logica para verificar livro por titulo e adicionar ao numero de exemplares
        return;
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
    public void buscarLivro (Livro livro){
    
    }

    @Override
    public void cadastrarUsuario( Usuario usuario ){
        
    }

    @Override
    public void atualizarUsuario(Usuario usuario){
        return;
    }

    @Override
    public void removerUsuario(Usuario usuario){
        return;
    }

    @Override
    public void buscarUsuario(Usuario usuario){
        return;
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
