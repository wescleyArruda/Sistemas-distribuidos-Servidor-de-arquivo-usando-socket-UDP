package ChatServidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	private int porta;
	private List<Socket> clientes;

	public Servidor(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<>();
	}

	public void executa() throws IOException  {
		try(ServerSocket servidor = new ServerSocket(this.porta)){
			System.out.println("Porta 1235 aberta!");
	
			while (true) {
				// aceita um cliente
				Socket cliente = servidor.accept();
				System.out.println("Nova conexão com o cliente " + 
						cliente.getInetAddress().getHostAddress());
				// adiciona saida do cliente à lista
				this.clientes.add(cliente);
				// cria tratador de cliente numa nova thread
				MensagemDoCliente therad = new MensagemDoCliente(cliente, this);
				new Thread(therad).start();
			}
		}
	}

	public void DistribuiMensagem(Socket recebeCliente, String msg) {
		// envia msg para todo mundo
		for (Socket cliente : this.clientes) {
			if(!cliente.equals(recebeCliente)){
				try {
					PrintStream ps = new PrintStream(cliente.getOutputStream());
					ps.println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
