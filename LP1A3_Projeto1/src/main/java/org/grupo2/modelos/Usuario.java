package org.grupo2.modelos;

import java.util.Optional;

public abstract class Usuario{
    private String nome;
    private String cpf;
    private String endereco;
    private String email;
    private String senha;

    public Usuario() {
    }

    public Usuario(String nome, String cpf, String endereco, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

   
    

    public void emprestarLivro(int idEmprestimo, Livro livro, Cliente cliente) throws Exception {
        if (Biblioteca.existeEmprestimoPorId(idEmprestimo)) {
            throw new Exception("Emprestimo já existente.");
        }
        if (livro.getNumExemplaresDisponiveis() > 0) {
            throw new Exception("Não há exemplares disponíveis.");
        }
        if (Reserva.existeReservaPorLivroECliente(livro, cliente)) {
            cancelarReserva(livro, cliente);
        }
        Emprestimo emprestimo = new Emprestimo(idEmprestimo, livro, cliente);
        Biblioteca.salvarEmprestimo(emprestimo);
        System.out.println("Emprestimo realizado com sucesso");
    }
   
    // Versao para o handler
//     public Emprestimo emprestarLivro(int id, Livro livro, Cliente cliente, Instant dataEmprestimo, Instant dataDevolucao) throws Exception {
//         try {
//             Biblioteca.existeEmprestimoPorId(id);
//             livro.emprestar();
//             if (Reserva.existeReservaPorLivroECliente(livro, cliente)) {
//                 cancelarReserva(livro, cliente);
//             }
//             return Biblioteca.salvarEmprestimo(new Emprestimo(id, livro, cliente, dataEmprestimo, dataDevolucao));
//         } catch (Exception e) {
//             throw new Exception(e.getMessage());
//         }
//     }

    public void devolverLivro(Livro livro) throws Exception {
        if (Livro.devolver(livro)) {
            System.out.println("Livro devolvido!");
        } else {
            System.out.println("Houve um problema");
        }
    }

    public void reservarLivro(int id, Livro livro, Cliente cliente) throws Exception {
        if (!Livro.livroDisponivel(livro)) {
            throw new Exception("Não há exemplares disponíveis.");
        }
        Reserva reserva = new Reserva(id, livro, cliente);
        Biblioteca.salvarReserva(reserva);
        System.out.println("Livro reservado.");
    }

    public static void cancelarReserva(Livro livro, Cliente cliente) throws Exception {
        Reserva reserva = Biblioteca.procurarReservaPorLivroECliente(livro, cliente);
        if (Objects.nonNull(reserva)) {
            reserva.deletarReserva();
            devolverLivro(livro);
        }
    }
}
