import java.util.ArrayList;

class MonitorGral {

	private static final int WIDE = 80;

	static MonitorMem monitorMemoria = new MonitorMem();
	static MonitorCPU monitorCPU = new MonitorCPU();

	static ArrayList<String> datosMem = new ArrayList<String>();
	static ArrayList<String> datosCPU = new ArrayList<String>();

	public static void main(String[] args) {

		//Agrego encabezados
		datosMem.add("MEMORIA");
		datosCPU.add("PROCESADOR");
		datosMem.add(getLinea());
		datosCPU.add(getLinea());

		datosMem.add("hola 1");
		datosMem.add("hola 2");
		datosMem.add("hola 3");
		datosMem.add("hola hola hola hola");
		datosMem.add("hola 5");
		datosMem.add("hola 6");
		datosMem.add("hola 7");
		datosMem.add("hola 8");
		datosCPU.add("hola 1");
		datosCPU.add("hola 2");
		datosCPU.add("hola 3");
		datosCPU.add("hola 4");
		datosCPU.add("hola 5 adiooooos");
		datosCPU.add("hola 6");
		datosCPU.add("hola 7");
		datosCPU.add("hola 8");

		dibujaVentana();

		System.out.println(monitorMemoria.getState());
		monitorMemoria.start();
		monitorCPU.start();
	}

	private static void dibujaVentana(){
		for (int i = 0; i <= WIDE; i++) {
			System.out.print("=");
		}
		System.out.println();

		for(int i = 0; i < datosMem.size(); i++){
			//Columna de la memoria
			System.out.print("|");
			System.out.print(datosMem.get(i));
			for (int j = 1; j < (WIDE/2)-datosMem.get(i).length(); j++) {
				System.out.print(" ");
			}
			//Columna del procesador
			System.out.print("|");
			System.out.print(datosCPU.get(i));
			for (int j = 1; j < (WIDE/2)-datosCPU.get(i).length(); j++) {
				System.out.print(" ");
			}

			System.out.print("|\n");
		}	
	}

	private static String getLinea(){
		String aux = "";
		for (int j = 1; j < (WIDE/2); j++) {
				aux = aux.concat("-");
			}
		return aux;
	}
}