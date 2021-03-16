
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ClienteTCP extends Thread {
	
	
	private Socket sock;
	
	private DataInputStream dis;
	
	private int PUERTO;
	
	private Hash hash;
	

	public ClienteTCP(int Puerto) {
		
		hash= new Hash();

		PUERTO = Puerto;
	}
	
	public void run()  {
		
		int bytes = 0;
		try {
			sock = new Socket("localhost", PUERTO);
			System.out.println("Conectando al puerto:" +" " + PUERTO);
			
			
			dis = new DataInputStream(sock.getInputStream());
			
			FileOutputStream fos = new FileOutputStream("C:\\Users\\nicoc\\Documents\\ArchivoDescargado"+PUERTO+".txt");
			
			System.out.println("Archivo recibido");
			
			int cantidad = dis.read();
			String hash2 = new String(dis.readNBytes(cantidad), StandardCharsets.UTF_8);
			long size = dis.readLong();
			
			byte [] arreglo  = new byte [4*1024];
			
			while(size >0 && (bytes=dis.read(arreglo, 0, (int)Math.min(arreglo.length, size)))!=-1)
			{
				fos.write(arreglo, 0 , bytes);
				size -= bytes;
			}
			fos.close();
			
			String hashF = hash.calcularHash("C:\\Users\\nicoc\\Documents\\ArchivoDescargado"+PUERTO+".txt");
			
			if(!hashF.equals(hash2))
				System.out.println("Paila");
			
			sock.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}

}