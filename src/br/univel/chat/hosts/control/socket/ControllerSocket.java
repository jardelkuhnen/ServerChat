package br.univel.chat.hosts.control.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.univel.chat.hosts.control.UsuarioManager;

public class ControllerSocket implements Runnable {

	private final UsuarioManager manager;
	private ExecutorService pool = Executors.newFixedThreadPool(4);

	public ControllerSocket(final UsuarioManager manager) {
		this.manager = manager;
	}

	public void iniciar() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try (ServerSocket server = new ServerSocket(5000)) {

			while (!server.isClosed()) {
				System.out.println("Aguardando cliente...");

				Socket conexao = server.accept();
				System.out.println("Cliente conectado, processando mensagem");
				pool.submit(new ProcessarMensagem(conexao, manager));

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
