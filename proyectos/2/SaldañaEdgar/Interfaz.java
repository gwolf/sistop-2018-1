import java.util.concurrent.Semaphore;

class Interfaz extends Thread {
	
	static final int WIDE = 80;
	private Semaphore coordinacionLista;

	Interfaz(Semaphore coordinacionLista){
		this.coordinacionLista = coordinacionLista;
	}

	public void run(){
		dibujaVentana();
	}

	private void dibujaVentana(){

		try{
			coordinacionLista.acquire();
		}catch(Exception e){
			e.printStackTrace();
		}			

		for (int i = 0; i <= WIDE; i++) {
			System.out.print("=");
		}
		System.out.println();

		for(int i = 0; i < Monitor.sizeDatos(); i++){
			//Columna de la memoria
			System.out.print("|");
			System.out.print(Monitor.getDatos(i));
			for (int j = 1; j < (WIDE)-Monitor.getDatos(i).length(); j++) {
				System.out.print(" ");
			}
			System.out.print("|\n");
		}

		for (int i = 0; i <= WIDE; i++) {
			System.out.print("=");
		}
		System.out.println();
	}

	static public String getLinea(){
		String aux = "";
		for (int j = 1; j < (WIDE); j++) {
				aux = aux.concat("-");
			}
		return aux;
	}
	private void clear(){
		System.out.print("\u001b[2J");
		System.out.flush();
	}
}