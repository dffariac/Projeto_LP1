package org.grupo2.modelos;
import java.util.Map;
import org.grupo2.interfaces.GerenciamentoDeLivros;
import org.grupo2.interfaces.GerenciamentoDeUsuarios;



public class Administrador extends Usuario implements GerenciamentoDeUsuarios, GerenciamentoDeLivros {

    private int id;
    private int keychave = 0;

    public Administrador(int id, String nome, String cpf, String endereco, String email, String senha) {
        super(nome, cpf, endereco, email, senha);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    Map<Integer, Funcionario> bdFuncionario = Biblioteca.getFuncionarios();
    Map<Integer, Usuario> bdUsuario = Biblioteca.getUsuario();
    Map<Integer, Livro> bdLivro = Biblioteca.getLivros();

    public void cadastrarFuncionario(Funcionario funcionario) {
        bdFuncionario.put(null, funcionario);
        bdUsuario.put(null, funcionario);
        for (int i = 0; i < bdFuncionario.size(); i++) {
            if (funcionario.getNome().equals(bdFuncionario.get(i).getNome())) {
                System.out.println("Funcionário(a) " + bdFuncionario.get(i).getNome() + "cadastrado");
            }

        }

    }

    public void removerFuncionario(Funcionario funcionario) {
        try {
            for (int i = 0; i < bdFuncionario.size(); i++) {
                if (funcionario.getNome().equals(bdFuncionario.get(i).getNome())) {
                    bdFuncionario.remove(null, funcionario);
                    System.out.println("Funcionário(a) " + bdFuncionario.get(i).getNome());
                }
            }
            for (int i = 0; i < bdUsuario.size(); i++) {
                if (funcionario.getNome().equals(bdUsuario.get(i).getNome())) {
                    System.out.println("Funcionário(a) " + bdUsuario.get(i).getNome() + "removido");
                    bdUsuario.remove(null, funcionario);
                }
            }
        } catch (Exception erro) {
            System.out.println("Funcionário não encontrado");
        }
    }

    public void buscarFuncionario(Funcionario funcionario) {
        try {
            for (int i = 0; i < bdFuncionario.size(); i++) {
                if (funcionario.getNome().equals(bdFuncionario.get(i).getNome())) {
                    bdFuncionario.remove(null, funcionario);
                    System.out.println("Funcionário(a) " + bdFuncionario.get(i).getNome() + "encontrado(a)");
                }
            }

        } catch (Exception erro) {
            System.out.println("Funcionário não existe na base de dados");
        }

    }

    public static Administrador fromJson(String requestBody) {
        String requestBodyClean = requestBody.replace("{", "").replace("}", "");
        String[] splitProperties = requestBodyClean.split(",");
        int jsonId = Integer.parseInt(splitProperties[0].split(":")[1].trim());
        String jsonNome = splitProperties[1].split(":")[1].trim().replace("\"", "");
        String jsonCpf = splitProperties[2].split(":")[1].trim().replace("\"", "");
        String jsonEndereco = splitProperties[3].split(":")[1].trim().replace("\"", "");
        String jsonEmail = splitProperties[4].split(":")[1].trim().replace("\"", "");
        String jsonSenha = splitProperties[5].split(":")[1].trim().replace("\"", "");
        return new Administrador(jsonId, jsonNome, jsonCpf, jsonEndereco, jsonEmail, jsonSenha);
    }

    public String toJson() {
        return "{\"id\": " + this.getId() + ", \"nome\": \"" + this.getNome() +
                "\", \"cpf\": \"" + this.getCpf() + "\", \"endereco\": \"" + this.getEndereco() +
                "\", \"email\": \"" + this.getEmail() + "\"}";
    }

    @Override
    public void cadastrarLivro(int id, String titulo, String autor, String editora, int anoPublicacao,
                               int numExemplares, int numExemplaresDisponiveis) {
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

    public Livro atualizarLivro(Livro livro) {

        Livro atualizacaoLivro = buscarLivro(livro);

        atualizacaoLivro.setTitulo(livro.getTitulo());
        atualizacaoLivro.setAutor(livro.getAutor());
        atualizacaoLivro.setEditora(livro.getEditora());
        atualizacaoLivro.setAnoPublicacao(livro.getAnoPublicacao());
        atualizacaoLivro.setNumExemplares(livro.getNumExemplares());
        atualizacaoLivro.setNumExemplaresDisponiveis(livro.getNumExemplaresDisponiveis());

        return atualizacaoLivro;

    }

    @Override
    public void removerLivro(Livro livro) {
        for (int i = 0; i < bdLivro.size(); i++) {
            if (livro.equals(bdLivro.get(i))) {
                bdLivro.remove(keychave, livro);
                System.out.println("Livro removido com sucesso");
            }
        }
        throw new UnsupportedOperationException("Livro não encontrado");
    }

    @Override
    public Livro buscarLivro(Livro livro) {
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
    public void cadastrarUsuario(Usuario usuario) {
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
    public void atualizarUsuario(Usuario usuario) {


            }


    @Override
    public void removerUsuario(Usuario usuario) {
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
    public void buscarUsuario(Usuario usuario) {
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



