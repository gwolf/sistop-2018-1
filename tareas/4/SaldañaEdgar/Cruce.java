/*El cruce del río*/

import java.util.concurrent.Semaphore;

class Cruce {

	static final int hilos = 26;
	static final int hacker = 1;
	static final int serf = 0;
	static private Semaphore mutex = new Semaphore(1);
	static private int[] balsa = new int[4];

	public static void main(String[] args) {
		
		iniciaBalsa();

		for (int i = 0; i <= hilos/2; i++) {
			new Persona(hacker, mutex).start();
			new Persona(serf, mutex).start();
		}
	}

	public static void iniciaBalsa(){
		for (int i = 0; i < balsa.length; i++) {
			balsa[i] = -1;
		}
	}

	//Regresa false solamente cuando la balsa ya está llena
	public static boolean aborda(int id){
		if(cuenta(-1) == 0)
			return false;
		for (int i = 0; i < balsa.length; i++) {
			if(balsa[i] == -1){
				//Si hay exactamente un lugar disponible
				if ((cuenta(-1) == 1)) {
					if((id == 1)&&(cuenta(1)%2 != 0)){
						//System.out.println("Aborda un hacker");
						balsa[i] = id;
						return true;					
					}else
					if((id == 0)&&(cuenta(0)%2 != 0)){
						//System.out.println("Aborda un serf");
						balsa[i] = id;
						return true;
					}
				}else{
					balsa[i] = id;
					return true;
				}
			}
		}
		return true;
	}

	public static int cuenta(int id){
		int p = 0;
		for (int i = 0; i < balsa.length; i++) {
			if(balsa[i] == id)
				p++;
		}
		return p;
	}

	public static void imprimeBalsa(){
		System.out.print("Balsa:");
		for (int i = 0; i < balsa.length; i++) {
			if(balsa[i] == hacker)
				System.out.println("\thacker");
			if(balsa[i] == serf)
				System.out.println("\tserf");
		}
		System.out.println();
	}
}