package br.univel.chat.hosts.control.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import br.univel.chat.hosts.control.UsuarioManager;
import br.univel.chat.hosts.dto.Usuario;

public class ProcessarMensagem implements Runnable {

	private final Socket conexao;
	private final UsuarioManager manager;

	public ProcessarMensagem(final Socket conexao, final UsuarioManager manager) {
		this.conexao = conexao;
		this.manager = manager;
	}

	@Override
	public void run() {

		try (InputStream input = conexao.getInputStream();
				ObjectInputStream objInput = new ObjectInputStream(input);
				OutputStream output = conexao.getOutputStream();
				ObjectOutputStream objOutput = new ObjectOutputStream(output)) {

			Usuario usuario = (Usuario) objInput.readObject();

			if (manager.isUsuarioTemSessao(usuario)) {
				manager.atualizarSessao(usuario);
			} else {
				manager.register(usuario);
			}

			Collection<Usuario> usuarios = manager.getUsuariosAtivos();
			ArrayList<Usuario> array = new ArrayList<>(usuarios);
			objOutput.writeObject(array);
			objOutput.flush();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();

		} finally {
			if (!this.conexao.isClosed()) {

				try {
					this.conexao.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}
