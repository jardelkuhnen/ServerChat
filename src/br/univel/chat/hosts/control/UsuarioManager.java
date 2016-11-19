package br.univel.chat.hosts.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import br.univel.chat.hosts.dto.Usuario;

public class UsuarioManager {

	private static final Collection<UsuarioTimer> USUARIOS = new ArrayList<UsuarioTimer>();

	public UsuarioManager() {

	}

	public void register(final Usuario usuario) {
		USUARIOS.add(new UsuarioTimer(usuario, 3L));
	}

	public void eliminarSessoesAntigas() {
		System.out.println("Eliminando sessões antigas");
		USUARIOS.removeIf(usuario -> usuario.isSessaoExperada());

	}

	public void atualizarSessao(final Usuario usuario) {

		if (!isUsuarioTemSessao(usuario)) {
			throw new IllegalStateException(
					"Usuario sem sessão não pode acessar");
		}

		USUARIOS.forEach(usuarioTimer -> {
			if (usuarioTimer.getUsuario().getNome()
					.equalsIgnoreCase(usuario.getNome())) {
				System.out.println(String.format(
						"Usuario %s teve sua sessao atualizada",
						usuario.getNome()));
				usuarioTimer.atualizarUltimoAcesso();
			}
		});

	}

	public Boolean isUsuarioTemSessao(Usuario usuario) {

		Collection<Usuario> usuarios = getUsuariosAtivos();

		Usuario usuarioAtivo = usuarios
				.stream()
				.filter(user -> user.getNome().equalsIgnoreCase(
						usuario.getNome())).findAny().orElse(null);

		return usuarioAtivo != null;

	}

	public Collection<Usuario> getUsuariosAtivos() {

		// faz um mapeamento de todos os usuarios da classe UsuarioTimer e
		// retorna uma lista com todos agrupados
		return USUARIOS.stream().map(UsuarioTimer::getUsuario)
				.collect(Collectors.toList());

	}

}
