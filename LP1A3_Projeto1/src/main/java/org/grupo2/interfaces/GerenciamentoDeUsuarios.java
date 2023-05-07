package org.grupo2.interfaces;

import org.grupo2.modelos.Usuario;

public interface GerenciamentoDeUsuarios{

    public void cadastrarUsuario(Usuario usuario);

    public Usuario atualizarUsuario(Usuario usuario);

    public void removerUsuario(Usuario usuario);

    public Usuario buscarUsuario(Usuario usuario);
}
