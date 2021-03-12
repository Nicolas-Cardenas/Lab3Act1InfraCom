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

	public final static int SOCKET_PORT = 13267;      // you may change this
	public final static String SERVER = "127.0.0.1";  // localhost
	
	public final static String FILE_TO_RECEIVED = "./archivos";  // you may change this, I give a
	// different name because i don't want to
	// overwrite the one used by server...
	
	public final static String FILE_TO_RECEIVED2 = "./archivos";

	public final static int FILE_SIZE = 6022386;
	public final static int FILE_SIZE2 = 6022386;// file size temporary hard coded
	// should bigger than the file to be downloaded


	public static void main(String[] args) throws IOException {

		int bytesRead;
		int bytesRead2;
		int current = 0;
		int current2 = 0;
		FileOutputStream fos = null;
		FileOutputStream fos2 = null;
		BufferedOutputStream bos = null;
		BufferedOutputStream bos2 = null;
		Socket sock = null;
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting...");

			// receive file
			byte [] arreglo1  = new byte [FILE_SIZE];
			byte [] arreglo2  = new byte [FILE_SIZE2];
			
			InputStream is = sock.getInputStream();
			InputStream is2 = sock.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED);
			fos2 = new FileOutputStream(FILE_TO_RECEIVED2);
			bos = new BufferedOutputStream(fos);
			bos2 = new BufferedOutputStream(fos2);
			bytesRead = is.read(arreglo1,0,arreglo1.length);
			bytesRead2 = is2.read(arreglo2, 0, arreglo2.length);
			current = bytesRead;
			current2 = bytesRead2;

			do {
				bytesRead =
						is.read(arreglo1, current, (arreglo1.length-current));
				if(bytesRead >= 0) current += bytesRead;
			} while(bytesRead > -1);

			bos.write(arreglo1, 0 , current);
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED
					+ " downloaded (" + current + " bytes read)");
			
			do {
				bytesRead2 =
						is.read(arreglo2, current2, (arreglo2.length-current2));
				if(bytesRead2 >= 0) current2 += bytesRead2;
			} while(bytesRead2 > -1);

			bos2.write(arreglo2, 0 , current2);
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED2
					+ " downloaded (" + current + " bytes read)");
			
			
		}
		finally {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (bos2 != null) bos2.close();
			if (sock != null) sock.close();
		}
	}

}