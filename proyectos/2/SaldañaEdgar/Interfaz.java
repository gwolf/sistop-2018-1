import java.util.concurrent.Semaphore;

class Interfaz extends Thread {
	
	private static final int WIDE = 80;
	private static Semaphore s;

	Interfaz(Semaphore s){
		this.s = s;
	}

	public void run(){		
		dibujaVentana();
	}

	private static void dibujaVentana(){
		try{
			s.acquire();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		for (int i = 0; i <= WIDE; i++) {
			System.out.print("=");
		}
		System.out.println();

		for(int i = 0; i < MonitorGral.sizeMem(); i++){
			//Columna de la memoria
			System.out.print("|");
			System.out.print(MonitorGral.getMem(i));
			for (int j = 1; j < (WIDE/2)-MonitorGral.getMem(i).length(); j++) {
				System.out.print(" ");
			}
			//Columna del procesador
			System.out.print("|");
			System.out.print(MonitorGral.getCPU(i));
			for (int j = 1; j < (WIDE/2)-MonitorGral.getCPU(i).length(); j++) {
				System.out.print(" ");
			}

			System.out.print("|\n");
		}	
	}

	static String getLinea(){
		String aux = "";
		for (int j = 1; j < (WIDE/2); j++) {
				aux = aux.concat("-");
			}
		return aux;
	}
}