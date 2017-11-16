import java.util.concurrent.Semaphore;

class Interfaz extends Thread {
	
	static final int WIDE = 80;
	//private static final String clear() = System.out.print("\e[2J\e[H");
	private static Semaphore lleganMonitores;

	Interfaz(Semaphore lleganMonitores){
		this.lleganMonitores = lleganMonitores;
	}

	public void run(){

		

		dibujaVentana();
	}

	private static void dibujaVentana(){

		while(true){
			//System.out.print("\u001b[2J");

			try{
				lleganMonitores.acquire();
			}catch(Exception e){
				e.printStackTrace();
			}
			
			System.out.flush();

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
			for (int i = 0; i <= WIDE; i++) {
				System.out.print("=");
			}
			System.out.println();
			try{
				sleep(2000);
			}catch(Exception e){
				e.printStackTrace();
			}
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