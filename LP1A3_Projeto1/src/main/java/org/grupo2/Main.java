package org.grupo2;

import org.grupo2.modelos.Biblioteca;
import org.grupo2.modelos.Cliente;
import org.grupo2.modelos.Emprestimo;
import org.grupo2.modelos.Funcionario;
import org.grupo2.modelos.Livro;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Livro Hp = new Livro(45,"Harry Potter e a Pedra Filosofal","J.K Rowling","Rocco",1997,12,12);
        Livro Hp2 = new Livro(46,"Harry Potter e a Camara Secreta","J.K Rowling","Rocco",1998,10,10);
        Livro Hp3 = new Livro(47,"Harry Potter e o Prisioneiro de Azkaban","J.K Rowling","Rocco",1999,13,9);
        System.out.println(Hp);
        System.out.println(Hp2);
        System.out.println(Hp3);
        Biblioteca.startServer();
    }

    public Emprestimo realizarEmprestimo(){

        Livro livro = new Livro(1, "Livro 1", "Autor 1", "Editora 1", 2021, 5, 5);
    
        Cliente cliente = new Cliente(1, "cliente 1", "111.222.333.44", "endereco 1", "email1", "senha1");
        
        Emprestimo emprestimo = Livro.emprestar(livro, cliente);

    }
}