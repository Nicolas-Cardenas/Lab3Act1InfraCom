import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteTCP {



	public static void main(String[] args) throws IOException {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;

		try {
			sock = new Socket("localhost", 9999);
			System.out.println("Conectando");
			byte [] arreglo  = new byte [6000000];
			InputStream is = sock.getInputStream();
			fos = new FileOutputStream("C:\\Users\\nicoc\\Documents\\ArchivoDescargado.txt");
			bos = new BufferedOutputStream(fos);
			is.read(arreglo,0,arreglo.length);
			System.out.println("Archivo recibido");
			bos.write(arreglo, 0 , arreglo.length);
			bos.flush();

			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (sock != null) sock.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}

}