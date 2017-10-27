import java.util.Random;
import java.util.Scanner;


public class TestMemoria{
	
	// static char[] porcion = new char[20]; //La "memoria" se llamará porcion
	static Scanner getch = new Scanner(System.in);

	public static void main(String[] args) {
		Random r = new Random();
		System.out.println("Asignacion original: ");
		System.out.println(Tabla.porcionMemoria);
		for(int i = 0; i < 26; ++i) { // Programa limitado a hacerse por cada letra del abecedario
			int numUnidades = r.nextInt((15 - 2) + 1) + 1;
			char nombreProceso = (char) (i+65);
			System.out.println("---------------------------");
			System.out.println("\n\nAsignando proceso: " + nombreProceso + " de "+ numUnidades);
			if(Tabla.asigna(nombreProceso, numUnidades) == 1) //se busca asignar el  proceso nombreProceso cuyo tamaño es de numUnidades
				if(Tabla.verificaCompresion() == 1){
					if(Tabla.liberarMemoria(nombreProceso, numUnidades) == 1)
						System.out.println("No se pudo colocar el proceso "+nombreProceso+ ", pruebe con otro proceso");
				}
				else{
					if(Tabla.comprimir() == 1){ //se intenta comprimir los procesos que ya están en ejecucion
						if(Tabla.liberarMemoria(nombreProceso, numUnidades) == 1)
							System.out.println("No se pudo colocar el proceso "+nombreProceso+ ", pruebe con otro proceso");
					} else {
						if(Tabla.asigna(nombreProceso, numUnidades) == 1)
							System.out.println("No se pudo colocar el proceso "+nombreProceso+ ", pruebe con otro proceso");
					}
				}
			System.out.println("\nNueva asignacion:");
			System.out.println(Tabla.porcionMemoria.toString());
			System.out.print("Presione cualq tecla para continuar");
			getch.nextLine();
		}	
	}
}