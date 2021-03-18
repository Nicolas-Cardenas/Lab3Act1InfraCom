import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		
		System.out.println("¿Cuantos clientes desea crear?");
		Scanner myInput = new Scanner( System.in );
		int o = Integer.parseInt(myInput.nextLine());
	
		myInput.close();
		
		for(int i =1; i<=o;i++)
		{
			int puerto = 5555+i;
			ClienteTCP cliente = new ClienteTCP(puerto, i);
			cliente.start();
		}
		// TODO Auto-generated method stub

	}

}
