import java.util.Scanner;

public class Servidor {
	

	public static void main(String[] args) {
		
		String rutaArchivo = "";
		
		System.out.println("¿Cuantas conexiones desea crear?");
		Scanner myInput = new Scanner( System.in );
		int o = Integer.parseInt(myInput.nextLine());
		
		System.out.println("¿Cual archivo desea enviar?");
		System.out.println("El archivo 1 corresponde a 100MB");
		System.out.println("El archivo 2 corresponde a 250MB");
	
		int i = Integer.parseInt(myInput.nextLine());
		myInput.close();
		
		if(i==1)
		{
			System.out.println("El archivo # " + i+" de 100MB será enviado");
			rutaArchivo =  "archivosEnviar/test1.txt";
		}
		else if (i==2)
		{
			System.out.println("El archivo #" + i +" de 250MB será enviado");
			rutaArchivo =  "archivosEnviar/test2.txt";
			
		}
		else
		{
			return;
		}
		
		
		
		for(int j =1; j<=o;j++)
		{
			int puerto = 5555+j;
			ServidorTCP conexion = new ServidorTCP(j,puerto, rutaArchivo,i,o);
			conexion.start();
		}

	}

}
