package org.grupo2;

import org.grupo2.modelos.Biblioteca;
import org.grupo2.modelos.Cliente;
import org.grupo2.modelos.Emprestimo;
import org.grupo2.modelos.Funcionario;
import org.grupo2.modelos.Livro;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Biblioteca.startServer();
    }

    // public Emprestimo realizarEmprestimo(){

    //     Livro livro = new Livro(1, "Livro 1", "Autor 1", "Editora 1", 2021, 5, 5);
    
    //     Cliente cliente = new Cliente(1, "cliente 1", "111.222.333.44", "endereco 1", "email1", "senha1");
        
    //     Emprestimo emprestimo = Livro.emprestar(livro, cliente);

    // }
}