
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServidorTCP  {


	public static void main(String[] args) throws Exception {
		
		System.out.println("El archivo 1 corresponde a 100MB");
		System.out.println("El archivo 2 corresponde a 250MB");
		System.out.println("¿Cual desea enviar?");

		Scanner myInput = new Scanner( System.in );
		int i = myInput.nextInt();
		myInput.close();
		if(i==1)
		{
			System.out.println("El archivo # " + i+" de 100MB será enviado");
		}
		else if (i==2)
		{
			System.out.println("El archivo #" + i +" de 250MB será enviado");
		}
		else
		{
			throw new Exception("El numero de archivo debe ser 1 o 2");
		}
		
		ServerSocket servidor = null;
		Socket sc = null;	
		try {

			servidor = new ServerSocket(9999);
			System.out.println("Servidor iniciado");

			while (true) {
				sc = servidor.accept();
				System.out.println("Cliente conectado");
				File archivo1Envio = new File("C:\\Users\\nicoc\\Documents\\test"+i+".txt");
				byte[] arreglo = new byte[(int)archivo1Envio.length()];
				FileInputStream fis= new FileInputStream(archivo1Envio);
				BufferedInputStream bis = new BufferedInputStream(fis);
				OutputStream os = sc.getOutputStream();
				bis.read(arreglo, 0, arreglo.length);
				os.write(arreglo, 0, arreglo.length);


				os.flush();
				sc.close();	

			}

		} 
		catch (IOException ex) {
		}

	}	

}
