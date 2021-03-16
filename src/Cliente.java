import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		
		System.out.println("¿Cuantos clientes desea crear?");
		Scanner myInput = new Scanner( System.in );
		int o = myInput.nextInt();
		
		myInput.close();
		
		
		for(int i =0; i<o;i++)
		{
			int puerto = 5555+i;
			ClienteTCP cliente = new ClienteTCP(puerto);
			cliente.start();
		}
		// TODO Auto-generated method stub

	}

}
