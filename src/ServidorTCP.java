


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class ServidorTCP extends Thread  {

	
	

	private int id;
	private int numeroArchivo;
	private ServerSocket servidor;
	private DataOutputStream dos;
	private DataInputStream dis;
	private String RUTA;
	private Hash hash;
	private int numeroClientes;


	public ServidorTCP(int idF, int Puerto, String rutaArchivo, int numerArch, int numeroCli) {
		
		try {
			this.id = idF;
			this.servidor = new ServerSocket(Puerto);
			RUTA = rutaArchivo;
			hash = new Hash();
			numeroArchivo = numerArch;
			numeroClientes=numeroCli;
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		

	}


	public void run()  {

		try 
		{
			System.out.println("Servidor iniciado con el puerto:" + " " + servidor.getLocalPort());
			Socket cs = servidor.accept();
			dis = new DataInputStream(cs.getInputStream());
			dos = new DataOutputStream(cs.getOutputStream());
			


			int paquetes=0;
			int bytes = 0;
			File archivo1Envio = new File(RUTA);
			String hashF =calcularHash(RUTA);
			FileInputStream fis = new FileInputStream(archivo1Envio);
			dos.write(numeroClientes);
			dos.write(hashF.length());
			dos.write(hashF.getBytes(StandardCharsets.UTF_8));
			String nombreArchivo = RUTA.substring(15,RUTA.length());
			
			
			long tamanio = archivo1Envio.length();
			dos.writeLong(tamanio);
			int idC = dis.read();
			
		
			byte[] arreglo = new byte[4*1024];
			long tiempoInicio = System.currentTimeMillis();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now(); 
			String nombreLog="Logs/Servidor/"+dtf.format(now)+"idServidor"+id+"-log.txt"; 
			
			while((bytes=fis.read(arreglo))!=-1)
			{
				dos.write(arreglo, 0, bytes);
				paquetes++;
				dos.flush();
			}
			if(numeroArchivo==1)
			{
				dos.write(1);
				dos.flush();
			}
			else if (numeroArchivo==2)
			{
				dos.write(2);
				dos.flush();
			}
			fis.close();
			
			long finalTiempo = System.currentTimeMillis();
			long tiempoTotal = finalTiempo - tiempoInicio;
			int estadoT=dis.read();
			
			generarLog(nombreLog, nombreArchivo, tamanio, paquetes, tiempoTotal, idC, estadoT, hashF);
			
			fis.close();
			
			dis.close();
			dos.close();
			
			cs.close();

		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	

	private String calcularHash(String ruta) throws IOException
	{
		return hash.calcularHash(ruta);
	}
	
	private void generarLog(String nombre, String nombreArchivo, long tamanioArchivo, int pPaquetes, long tiempo, int idC, int estado, String hashCalculado) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter(nombre, "UTF-8");
		writer.println("Nombre del archivo: "+nombreArchivo);
		writer.println("Tamaño del archivo: "+tamanioArchivo+"B");
		writer.println("Id Cliente transferencia: "+idC);
		writer.println("Tiempo de transferencia Total: "+tiempo+"milisegundos");
		writer.println("Paquetes Transmitidos: "+pPaquetes);
		writer.println("Estado de transferencia: "+estado);
		writer.println("Hash que se envio: "+ hashCalculado);
		writer.close();
		
	}


}
