package br.univel.chat.hosts;

import br.univel.chat.hosts.control.CheckUsuario;
import br.univel.chat.hosts.control.UsuarioManager;
import br.univel.chat.hosts.control.socket.ControllerSocket;

public class Main {

	
	public static void main(String[] args) {
		
		UsuarioManager manager = new UsuarioManager();
		CheckUsuario check = new CheckUsuario(manager);
		check.registrarCheck();
		ControllerSocket controller = new ControllerSocket(manager);
		controller.iniciar();
		
	}
}
