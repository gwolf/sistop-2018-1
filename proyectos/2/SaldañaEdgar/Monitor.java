import java.util.ArrayList;
import java.util.concurrent.Semaphore;

class Monitor {

	private static Coordinador coordinador = new Coordinador();
	private static ArrayList<String> datos = new ArrayList<String>();

	public static void main(String[] args) {
		coordinador.start();
	}

	public static boolean addDatos(String aux){
		return datos.add(aux);
	}
	public static void clearDatos(){
		datos.clear();
	}
	public static String getDatos(int aux){
		return datos.get(aux);
	}
	public static int sizeDatos(){
		return datos.size();
	}
}