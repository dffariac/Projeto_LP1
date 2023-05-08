package org.grupo2.modelos;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.grupo2.consumidores.*;
import org.grupo2.handlers.AdministradorHandler;
import org.grupo2.handlers.ClienteHandler;
import org.grupo2.handlers.EmprestimoHandler;
import org.grupo2.handlers.FuncionarioHandler;
import org.grupo2.handlers.LivroHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

public class Biblioteca {
    private final static Integer idLivros = 0;
    private final static Integer idUsuarios = 0;
    private final static Integer idEmprestimos = 0;
    private final static Integer idReservas = 0;
    private static Map<Integer, Livro> livros = new HashMap<Integer, Livro>();
    private static Map<Integer, Cliente> clientes = new HashMap<Integer, Cliente>();
    private static Map<Integer, Funcionario> funcionarios = new HashMap<Integer, Funcionario>();
    private static Map<Integer, Administrador> administradores = new HashMap<Integer, Administrador>();
    private static Map<Integer, Emprestimo> emprestimos = new HashMap<Integer, Emprestimo>();
    private static Map<Integer, Reserva> reservas = new HashMap<Integer, Reserva>();
    private static Map<Integer, Usuario> usuario = new HashMap<Integer, Usuario>();
    
  

    public static void setLivros(Map<Integer, Livro> livros) {
        Biblioteca.livros = livros;
    }

    public static void setClientes(Map<Integer, Cliente> clientes) {
        Biblioteca.clientes = clientes;
    }

    public static void setFuncionarios(Map<Integer, Funcionario> funcionarios) {
        Biblioteca.funcionarios = funcionarios;
    }

    public static void setAdministradores(Map<Integer, Administrador> administradores) {
        Biblioteca.administradores = administradores;
    }

    public static Map<Integer, Usuario> getUsuario() {
        return usuario;
    }

    public static void setUsuario(Map<Integer, Usuario> usuario) {
        Biblioteca.usuario = usuario;
    }

    private static final int PORT = 8080;

    public static void startServer() throws IOException, InterruptedException {
        // Clientes
        Cliente cliente1 = new Cliente(1, "Nome Cliente 1", "100.000.000-01", "Endereco Cliente 1", "cliente1@email.com", "senha_cliente1");
        clientes.put(1, cliente1);

        // Funcionarios
        funcionarios.put(1, new Funcionario(1,"Nome Funcionario 1", "200.000.000-01", "Endereco Funcionario 1", "funcionario1@email.com", "senha_funcionario1"));

        // Administradores
        administradores.put(1, new Administrador(1,"Nome Administrador 1", "300.000.000-01", "Endereco Administrador 1", "administrador1@email.com", "senha_administrador1"));

        // Livros
        Livro livro1 = new Livro(1, "Harry Potter e a Pedra Filosofal", "J.K Rowling", "Rocco", 1997, 12, 12);
        livros.put(1, livro1);
        livros.put(2,  new Livro(2,"Harry Potter e a Camara Secreta","J.K Rowling","Rocco",1998,10,10));
        livros.put(3,  new Livro(3,"Harry Potter e o Prisioneiro de Azkaban","J.K Rowling","Rocco",1999,13,9));

        // Emprestimos
        emprestimos.put(1, new Emprestimo(1, livro1, cliente1));

        // start server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/clientes", new ClienteHandler());
        server.createContext("/funcionarios", new FuncionarioHandler());
        server.createContext("/administradores", new AdministradorHandler());
        server.createContext("/livros", new LivroHandler());
        server.createContext("/emprestimos", new EmprestimoHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("\nServidor iniciado na porta " + PORT + ".");

        // Chamando Consumidores
        System.out.println("\n*** ACTIONS DO MODELO Cliente ***");
        ConsumidorCliente.main(null);
        System.out.println("\n*** ACTIONS DO MODELO Funcionario ***");
        ConsumidorFuncionario.main(null);
        System.out.println("\n*** ACTIONS DO MODELO Administrador ***");
        ConsumidorAdministrador.main(null);
        System.out.println("\n*** ACTIONS DO MODELO Livro ***");
        ConsumidorLivro.main(null);
        System.out.println("\n*** ACTIONS DO MODELO Emprestimo ***");
        ConsumidorEmprestimo.main(null);

        // stop server
        server.stop(0);
        System.out.println("\nServidor na porta " + PORT + " foi  finalizado.");
    }

    public static Map<Integer, Livro> getLivros() {
        return livros;
    }

    public static Map<Integer, Cliente> getClientes() {
        return clientes;
    }

    public static Map<Integer, Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public static Map<Integer, Reserva> getReservas() {
        return reservas;
    }

    public static Map<Integer, Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public static Map<Integer, Administrador> getAdministradores() {
        return administradores;
    }

    public static Livro buscarLivro(String titulo) {
        for (int i = 0; i < getLivros().values().size(); i++) {
            if (getLivros().get(i).getTitulo().equals(titulo)) {
                return getLivros().get(i);
            }
        }
        return null;
    }

    public void listarLivros() {
    for (int i=0; i < livros.size(); i++) {
        System.out.println("Id: " + livros.get(i).getId() + "\n Nome: " +
        livros.get(i).getTitulo() + "\n Disponiveis:" + livros.get(i).getNumExemplaresDisponiveis());
    }
    }
    
//     public static StringBuilder listarLivros() {
//         StringBuilder response = new StringBuilder();
//         response.append("[");
//         for (Livro livro : Biblioteca.getLivros().values()) {
//             response.append(livro.toJson());
//             response.append(",");
//         }
//         if (Biblioteca.getLivros().size() > 0) {
//             response.deleteCharAt(response.length() - 1);
//         }
//         response.append("]");
//         return response;
//     }

    public void buscarUsuarioNome(String nome) {
        try {
            for (int i = 0; i<usuario.size();i++) {
                if(nome.equalsIgnoreCase(usuario.get(i).getNome())) {
                    System.out.println("Usuário encontrado!" + "\n Nome:" + usuario.get(i).getNome() +
                    "\n Email :"+ usuario.get(i).getEmail());
                }
            }
        }
        catch (Exception error) {
            System.out.println("Usuaŕio não encontrado");
        }
    }

    public void buscarUsuarioCpf(String cpf) {
        try {
            for (int i = 0; i<usuario.size();i++) {
                if(cpf.equalsIgnoreCase(usuario.get(i).getCpf())) {
                    System.out.println("Usuário encontrado!" + "\n Nome:" + usuario.get(i).getNome() +
                    "\n Email :"+ usuario.get(i).getEmail() + "\n CPF:" + usuario.get(i).getCpf());
                }
            }
        }
        catch (Exception error) {
            System.out.println("Usuaŕio não encontrado");
        }
    }

    public void listarUsuarios() {
       for (int i = 0; i < usuario.size(); i++) {
        System.out.println(usuario.get(i).toString());
       }
    }

    public void listarClientes() {
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println(clientes.get(i).toString());
           }
    }
    
   //   Versao com integracao com api
//     public static StringBuilder listarClientes() {
//         StringBuilder response = new StringBuilder();
//         response.append("[");
//         for (Cliente cliente : Biblioteca.getClientes().values()) {
//             response.append(cliente.toJson());
//             response.append(",");
//         }
//         if (Biblioteca.getClientes().size() > 0) {
//             response.deleteCharAt(response.length() - 1);
//         }
//         response.append("]");
//         return response;
//     }

    public void listarFuncionarios() {
        for (int i = 0; i < funcionarios.size(); i++) {
            System.out.println(funcionarios.get(i).toString());
           }
    }
    
       //   Versao com integracao com api
//     public static StringBuilder listarFuncionarios() {
//         StringBuilder response = new StringBuilder();
//         response.append("[");
//         for (Funcionario funcionario : Biblioteca.getFuncionarios().values()) {
//             response.append(funcionario.toJson());
//             response.append(",");
//         }
//         if (Biblioteca.getFuncionarios().size() > 0) {
//             response.deleteCharAt(response.length() - 1);
//         }
//         response.append("]");
//         return response;
//     }
    
    // Versao que junta os 3 maps com a api
//     public static StringBuilder listarUsuarios() throws Exception {
//         StringBuilder response = new StringBuilder();
//         response.append("[");
//         for (Cliente cliente : Biblioteca.getClientes().values()) {
//             response.append(cliente.toJson());
//             response.append(",");
//         }
//         for (Funcionario funcionario : Biblioteca.getFuncionarios().values()) {
//             response.append(funcionario.toJson());
//             response.append(",");
//         }
//         for (Administrador administrador : Biblioteca.getAdministradores().values()) {
//             response.append(administrador.toJson());
//             response.append(",");
//         }
//         if (Biblioteca.getClientes().size() > 0) {
//             response.deleteCharAt(response.length() - 1);
//         }
//         response.append("]");
//         return response;
//     }

    public void listarEmprestimos() {
        for (int i = 0; i < emprestimos.size(); i++) {
            System.out.println(emprestimos.get(i).toString());
           }
    }

    public void listarEmprestimosCliente(Cliente cliente) {
        try {
            for (int i = 0; i < emprestimos.size(); i++){
                if (emprestimos.get(i).getCliente().equals(cliente)) {
                    System.out.println(emprestimos.get(i).getCliente().equals(cliente));
                    break;
                }
            }
        } catch (Exception erroEmprestimoClinete){
            System.out.println("Empréstimo não encontrado");
        }

    }
    
    // Versao com integração na api
//     public static StringBuilder listarEmprestimos() {
//         StringBuilder response = new StringBuilder();
//         response.append("[");
//         for (Emprestimo emprestimo : Biblioteca.getEmprestimos().values()) {
//             response.append(emprestimo.toJson());
//             response.append(",");
//         }
//         if (Biblioteca.getEmprestimos().size() > 0) {
//             response.deleteCharAt(response.length() - 1);
//         }
//         response.append("]");
//         return response;
//     }

// Lista os emprestimos em uma data
    public void listarEmprestimosData(Date data) {
         try {
             for (int i = 0; i < emprestimos.size(); i++) {
                 if (Objects.equals(data, emprestimos.get(i).getDataEmprestimo())) {
                     System.out.println("Livro :" + emprestimos.get(i).getLivro() + "\n Data empréstimo:" +
                             emprestimos.get(i).getDataEmprestimo());

                 }
             }
         }
         catch (Exception erroData ) {
             System.out.println("Data de emprestimo não encontrada");
         }
    }

    public void login(String cpf, String senha) {
        for (int i = 0; i < usuario.size(); i++) {
            if (usuario.get(i).getSenha().equals(senha) && usuario.get(i).getCpf().equals(cpf)) {
                System.out.println("Usuário autenticado");
                break;
        }

            }
    }

    public static boolean existeEmprestimoPorId(int id) throws Exception {
        if (Objects.nonNull(emprestimos.get(id))) {
            throw new Exception("Emprestimo já existente.");
        }
        return true;
    }

    public static void salvarEmprestimo(Emprestimo emprestimo) {
        emprestimos.put(Biblioteca.idEmprestimos + 1, emprestimo);
    }

    public static Emprestimo salvarEmprestimo(Emprestimo emprestimo) {
        getEmprestimos().put(emprestimo.getId(), emprestimo);
        return emprestimo;
    }

    public static Reserva procurarReservaPorLivroECliente(Livro livro, Cliente cliente) {
        for (int i = 1; i < getReservas().size() + 1; i++) {
            if (getReservas().get(i).getLivro().equals(livro) && reservas.get(i).getCliente().equals(cliente)) {
               return reservas.get(i);
            }
        }
        return null;
    }

    public static Optional<Reserva> procuraReservaPorId(int id) {
        return Optional.of(reservas.get(id));
    }

    public static void cancelarReserva(Reserva reserva) {
        reservas.remove(reserva.getId());
    }
}
