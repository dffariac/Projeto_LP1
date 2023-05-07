package org.grupo2.interfaces;

import org.grupo2.modelos.Usuario;

public interface GerenciamentoDeUsuarios{

    public void cadastrarUsuario(Usuario usuario);

    public void atualizarUsuario(Usuario usuario);

    public void removerUsuario(Usuario usuario);

    public void buscarUsuario(Usuario usuario);
}
