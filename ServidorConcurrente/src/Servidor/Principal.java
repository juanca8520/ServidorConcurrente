package Servidor;

public class Principal {

	public static void main(String[] args) {
		Servidor servidor = new Servidor(8080);
		servidor.start();
	}

}
