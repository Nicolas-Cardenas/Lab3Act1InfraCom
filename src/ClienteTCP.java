
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;





public class ClienteTCP extends Thread {
	
	private int identificador;
	
	private int clientes;
	
	private DataInputStream dis;
	
	private DataOutputStream dos;
	
	private int PUERTO;
	
	private Hash hash;
	

	public ClienteTCP(int Puerto, int ID) {
		
		this.identificador =ID;
		
		hash= new Hash();

		this.PUERTO = Puerto;
	}
	
	public void run()  {
		
		try(Socket sock = new Socket("localhost", PUERTO)) 
		{
			dis = new DataInputStream(sock.getInputStream());
			dos = new DataOutputStream(sock.getOutputStream());
			clientes = dis.read();
			
			
			
			FileOutputStream fos = new FileOutputStream("ArchivosRecibidos/Cliente"+identificador+"-Prueba-"+clientes+".txt");
			int cantidad = dis.read();
			String hash2 = new String(dis.readNBytes(cantidad), StandardCharsets.UTF_8);
			
			int bytes =0;
			int paquetes =0;
			long size = dis.readLong();
			long tamanioArchivo = size;
			dos.write(identificador);
			
			
			
			byte [] arreglo  = new byte [4*1024];
			long tiempoInicio = System.currentTimeMillis();
			while(size > 0 && (bytes = dis.read(arreglo, 0, (int)Math.min(arreglo.length, size)))!=-1)
			{
				fos.write(arreglo, 0 , bytes);
				paquetes++;
				size -= bytes;
			}
			fos.close();
			
			long finalTiempo = System.currentTimeMillis();
			long tiempoTotal = finalTiempo - tiempoInicio;
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
			LocalDateTime now = LocalDateTime.now();  
			String nombre="Logs/Clientes/"+dtf.format(now)+"Cliente"+(identificador)+"-log.txt"; 
			
			int archivo=dis.read();
			String nombreArchivo="";
			if(archivo==1) 
			{
				nombreArchivo="test1.txt";
			}
			else if(archivo ==2)
			{
				nombreArchivo ="test2.txt";
			}
			
			
			String hashF = calcularHash("ArchivosRecibidos/Cliente"+identificador+"-Prueba-"+clientes+".txt");
			if(!hashF.equals(hash2))
			{
				System.out.println("El archivo tiene problemas de integridad");
			}
			else
			{
				System.out.println("El archivo no presenta problemas de integridad!!!!");
			}
			
			generarLog(nombre, nombreArchivo, tamanioArchivo, paquetes, tiempoTotal, identificador, hashF);
			
			
			
			dis.close();
			dos.close();
			
		}
		catch (Exception e) {
			
			e.printStackTrace();

		}

	}
	
	
	public String calcularHash(String ruta) throws IOException
	{
		return hash.calcularHash(ruta);
	}
	
	private void generarLog(String nombre, String nombreArchivo, long tamanioArchivo, int pPaquetes, long tiempoT, int idC, String hashF) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter(nombre, "UTF-8");
		writer.println("Nombre del archivo: "+nombreArchivo);
		writer.println("Tamaño del archivo: "+tamanioArchivo+"B");
		writer.println("Id Cliente de transferencia: "+idC);
		writer.println("Tiempo Transferencia Total: "+tiempoT+"milisegundos");
		writer.println("Cantidad de Paquetes Transmitidos: "+pPaquetes);
		writer.println("Hash que se recibió: "+ hashF);
		writer.close();	
	}

}