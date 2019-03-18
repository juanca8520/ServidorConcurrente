package Servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ThreadCliente extends Thread{
	private int id;
	private Socket socket;
	private Servidor servidor;
	private boolean ocupado;

	public ThreadCliente(int pId, Servidor pServidor) {
		id = pId;
		socket = null;
		servidor = pServidor;
		ocupado = false;
	}

	public void run()
	{
		while(true)
		{
			BufferedReader lector = null;
			BufferedWriter escritor = null;
			try 
			{
				lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				escritor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				ocupado = true;
				String linea = lector.readLine();
				while(linea!=null)
				{
					if(linea.startsWith("registrar") || linea.startsWith("verificar"))
					{
						if(linea.startsWith("registrar"))
						{
							linea = lector.readLine();
							if(linea!=null)
							{
								String[] a = linea.split(";");
								if(servidor.registrarUsuario(a[0], a[1]))
								{
									escritor.write("Se registró exitosamente el usuario! :)");
								}
								else
								{
									escritor.write("El usuario ya existe");
								}
							}
						}
						else if(linea.startsWith("verificar"))
						{
							linea = lector.readLine();
							if(linea!=null)
							{
								String[] a = linea.split(";");
								if(servidor.verificarUsuario(a[0], a[1]))
								{
									escritor.write("OK");
								}
								else
								{
									escritor.write("Error, el usuario no existe o la contrasenha está errada");
								}
							}

						}
					}
				}
				lector.close();
				escritor.close();
				ocupado = false;
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			try 
			{
				socket.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void asignarSocket(Socket s)
	{
		socket = s;
	}

	public boolean estaOcupado(){
		return ocupado;
	}
}
