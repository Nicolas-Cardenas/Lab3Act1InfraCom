


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ServidorTCP extends Thread  {

	

	private int numeroArchivo;
	private ServerSocket servidor;
	private Socket sc;
	private DataOutputStream dos;
	private DataInputStream dis;
	private int PUERTO;
	private String RUTA;
	private Hash hash;


	public ServidorTCP(int Puerto, String rutaArchivo, int numerArch) {

		numeroArchivo = numerArch;
		hash = new Hash();
		RUTA = rutaArchivo;
		PUERTO = Puerto;

	}


	public void run()  {

		try 
		{

			servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado con el puerto:" + " " + PUERTO);
			sc = servidor.accept();
			dos = new DataOutputStream(sc.getOutputStream());
			dis = new DataInputStream(sc.getInputStream());


			enviarArchivo(RUTA);
			

			sc.close();

		} 
		catch (IOException ex) {
		}

	}

	public void enviarArchivo(String ruta) throws IOException
	{
		int bytes = 0;
		File archivo1Envio = new File(ruta);
		String hash =calcularHash(RUTA);			
		System.out.println(hash);
		FileInputStream fis = new FileInputStream(archivo1Envio);
		
		long tamanio = archivo1Envio.length();

		
		dos.writeLong(archivo1Envio.length());
		
	
		byte[] arreglo = new byte[4*1024];
		
		while((bytes=fis.read(arreglo))!=-1)
		{
			dos.write(arreglo, 0, bytes);
			dos.flush();
		}
	}

	public String calcularHash(String ruta) throws IOException
	{
		return hash.calcularHash(ruta);
	}


}
