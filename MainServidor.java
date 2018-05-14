package ChatServidor;

import java.io.IOException;

public class MainServidor {

	public static void main(String[] args) 
			throws IOException {
		new Servidor(1235).executa();
	}
}