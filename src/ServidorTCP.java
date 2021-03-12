
import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorTCP extends Thread {
	
	
    private final static String archivo1 = "";
    private final static String archivo2 = "";

	public static void main(String[] args) {
		
		
		ServerSocket servidor = null;
		Socket sc = null;
		

		//puerto de nuestro servidor
		final int PUERTO = 5000;
		
	
		try {
			//Creamos el socket del servidor
			servidor = new ServerSocket(PUERTO);
			System.out.println("Servidor iniciado");

			//Siempre estara escuchando peticiones
			while (true) {

				//Espero a que un cliente se conecte
				sc = servidor.accept();
				System.out.println("Cliente conectado");
				
				File archivo1Envio = new File(archivo1);
				File archivo2Envio = new File(archivo2);
				
				byte[] arreglo = new byte[(int)archivo1Envio.length()];
				byte[] arreglo2 = new byte[(int)archivo2Envio.length()];
				
				FileInputStream fis= new FileInputStream(archivo1Envio);
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				FileInputStream fis2= new FileInputStream(archivo2Envio);
				BufferedInputStream bis2 = new BufferedInputStream(fis2);
				
				
				bis.read(arreglo, 0, arreglo.length);
				bis2.read(arreglo2, 0, arreglo2.length);
				
				OutputStream os = sc.getOutputStream();
				System.out.println("Sending " + archivo1 + "(" + arreglo.length + " bytes)");
				System.out.println("Sending " + archivo2 + "(" + arreglo2.length + " bytes)");
				os.write(arreglo, 0, arreglo.length);
				os.write(arreglo2, 0, arreglo2.length);
				
				os.flush();
				sc.close();	

			}

		} catch (IOException ex) {
			Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	
	
	public void start()
	{
		for(int i=1; i<25;i++)
		{
			ServidorTCP s = new ServidorTCP();
			Thread t = new Thread(s);
			t.start();
	
		}
	}
	
	@Override
    public void run() {

    }

	

}
