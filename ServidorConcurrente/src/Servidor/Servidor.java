package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Servidor extends Thread{
	
	public final static int CANTIDAD_USUARIOS = 150;

	private ServerSocket socket;
	private ThreadCliente[] clientes;
	private HashMap<String, String> base;

	public Servidor(int port) 
	{
		base = new HashMap<>();
		try {
			socket = new ServerSocket(port);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		clientes = new ThreadCliente[CANTIDAD_USUARIOS];

		for (int i = 0; i < clientes.length; i++) 
		{
			clientes[i] = new ThreadCliente(i,this);
		}
	}

	public void run()
	{
		int i = 0;
		while(true)
		{
			Socket s2 = null;

			try 
			{
				s2 = socket.accept();
				clientes[i].asignarSocket(s2);
				clientes[i].start();
				i = i++;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public synchronized boolean registrarUsuario(String user, String pass)
	{
		boolean registre = false;

		if(!base.containsKey(user))
		{
			base.put(user, pass);
			registre = true;
		}
		return registre;	
	}
	
	public synchronized boolean verificarUsuario(String user, String pass)
	{
		boolean verifico = false;
		if(base.containsKey(user))
		{
			verifico = base.get(user).equals(pass);
		}
		return verifico;
	}
}
