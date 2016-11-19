package br.univel.chat.hosts.control;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CheckUsuario implements Runnable {

	private final UsuarioManager manager;

	public CheckUsuario(final UsuarioManager manager) {
		this.manager = manager;
	}

	public void registrarCheck(){
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 0, 5, TimeUnit.SECONDS);
	}
	
	@Override
	public void run() {
		this.manager.eliminarSessoesAntigas();
	}

}
