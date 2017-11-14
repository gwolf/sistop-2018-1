import java.util.ArrayList;
import java.util.concurrent.Semaphore;

class MonitorGral {

	private static Semaphore lanzaInterfaz = new Semaphore(-1);

	private static MonitorMem monitorMemoria = new MonitorMem(lanzaInterfaz);
	private static MonitorCPU monitorCPU = new MonitorCPU(lanzaInterfaz);
	private static Interfaz interfaz = new Interfaz(lanzaInterfaz);

	private static ArrayList<String> datosMem = new ArrayList<String>();
	private static ArrayList<String> datosCPU = new ArrayList<String>();

	public static void main(String[] args) {

		interfaz.start();
		monitorMemoria.start();
		monitorCPU.start();
	}

	public static boolean addMem(String aux){
		return datosMem.add(aux);
	}
	public static boolean addCPU(String aux){
		return datosCPU.add(aux);
	}
	public static String getMem(int aux){
		return datosMem.get(aux);
	}
	public static String getCPU(int aux){
		return datosCPU.get(aux);
	}
	public static int sizeMem(){
		return datosMem.size();
	}
	public static int sizeCPU(){
		return datosCPU.size();
	}
}