
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ClienteTCP extends Thread {
	
	private int id;
	
	private Socket sock;
	
	private DataInputStream dis;
	
	private int PUERTO;
	
	private Hash hash;
	

	public ClienteTCP(int Puerto, int ID) {
		
		id =ID;
		
		hash= new Hash();

		PUERTO = Puerto;
	}
	
	public void run()  {
		
		try 
		{
			sock = new Socket("localhost", PUERTO);
			System.out.println("Conectando al puerto:" +" " + PUERTO);
			dis = new DataInputStream(sock.getInputStream());
			System.out.println("Archivo recibido");
			
			recibirArchivo("C:\\Users\\nicoc\\Documents\\ArchivoDescargado"+PUERTO+".txt");
			
			sock.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	public void recibirArchivo(String ruta) throws IOException
	{
		
		FileOutputStream fos = new FileOutputStream("C:\\Users\\nicoc\\Documents\\ArchivoDescargado"+PUERTO+".txt");
		int cantidad = dis.read();
		String hash2 = new String(dis.readNBytes(cantidad), StandardCharsets.UTF_8);
		
		int bytes =0;
		long size = dis.readLong();
		byte [] arreglo  = new byte [4*1024];
		
		while(size >0 && (bytes=dis.read(arreglo, 0, (int)Math.min(arreglo.length, size)))!=-1)
		{
			fos.write(arreglo, 0 , bytes);
			size -= bytes;
		}
		fos.close();
		
		String hashF = calcularHash("C:\\Users\\nicoc\\Documents\\ArchivoDescargado"+PUERTO+".txt");
		
		
		if(!hashF.equals(hash2))
			System.out.println("Paila");
		
	}
	
	public String calcularHash(String ruta) throws IOException
	{
		return hash.calcularHash(ruta);
	}

}