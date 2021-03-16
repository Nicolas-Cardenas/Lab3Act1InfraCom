
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class ServidorTCP extends Thread  {
	

    private ServerSocket servidor;
    private Socket sc;
    private FileInputStream fis;
    private DataOutputStream dos;
	private int PUERTO;
	private String RUTA;
	private Hash hash;


	public ServidorTCP(int Puerto, String rutaArchivo) {

		hash = new Hash();
		RUTA = rutaArchivo;
		PUERTO = Puerto;

	}


	public void run()  {
			
		try {

			int bytes = 0;

			servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado con el puerto:" + " " + PUERTO);
			sc = servidor.accept();
			
			File archivo1Envio = new File(RUTA);
			

			byte[] arreglo = new byte[4*1024];
			fis= new FileInputStream(archivo1Envio);
			dos = new DataOutputStream(sc.getOutputStream());
			dos.writeLong(archivo1Envio.length());
			
			while((bytes=fis.read(arreglo))!=-1)
			{
				dos.write(arreglo, 0, bytes);
				dos.flush();
			}
			
			String hashF= hash.calcularHash(RUTA);
			System.out.println(hashF);
			sc.close();

		} 
		catch (IOException ex) {
		}

	}


}
