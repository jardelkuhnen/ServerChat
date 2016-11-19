package br.univel.chat.hosts.control;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import br.univel.chat.hosts.dto.Usuario;

public class UsuarioTimer {

	private final Usuario usuario;
	private LocalDateTime ultimoAcesso;
	private final Long tempoSessao;

	public UsuarioTimer(final Usuario usuario, final Long tempoSessao) {
		this.usuario = usuario;
		this.tempoSessao = tempoSessao;
		ultimoAcesso = LocalDateTime.now();
	}

	public UsuarioTimer atualizarUltimoAcesso() {
		// retornar a ultima data atual
		this.ultimoAcesso = LocalDateTime.now();
		return this;
	}

	public boolean isSessaoExperada() {

		LocalDateTime tempoMaximo = this.ultimoAcesso.plus(this.tempoSessao,
				ChronoUnit.SECONDS);

		return tempoMaximo.isBefore(LocalDateTime.now());

	}

	public Usuario getUsuario() {
		return usuario;
	}
}
