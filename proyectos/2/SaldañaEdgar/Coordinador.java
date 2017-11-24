import java.util.concurrent.Semaphore;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Coordinador extends Thread {

	private Semaphore coordinacionLista;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore barrera = new Semaphore(0);

	private int libre;
	private int total;
	private static ColectorDeProceso[] colectores = new ColectorDeProceso[5];

	Coordinador(Semaphore coordinacionLista){
		this.coordinacionLista = coordinacionLista;
	}

	public void run(){
		colectores[0] = new ColectorDeProceso(0, "meminfo", "MemTotal", "Total", mutex, barrera);
		colectores[1] = new ColectorDeProceso(1, "meminfo", "MemFree", "Libre", mutex, barrera);
		colectores[2] = new ColectorDeProceso(2, "meminfo", "MemAvailable", "Disponible", mutex, barrera);
		colectores[3] = new ColectorDeProceso(3, "meminfo", "Dirty", "Sucia", mutex, barrera);
		colectores[4] = new ColectorDeProceso(4, "meminfo", "Dirty", "Promedios", mutex, barrera);

		for (int i = 0; i < 10; i++) {
			Monitor.addDatos("");
		}

		for (int i = 0; i < colectores.length; i++) {
			colectores[i].start();
		}

		try{
			leeInfo("/proc/meminfo");
		}catch(Exception e){
			e.printStackTrace();
		}
		Monitor.clearDatos();
		Monitor.addDatos("MEMORIA");
		//El argumento de obtenBarra es el porcentaje ocupado
		Monitor.addDatos(obtenBarra(100-(libre*100)/total));
		Monitor.addDatos(Interfaz.getLinea());

		//Señal a la interfaz
		coordinacionLista.release();

		try{
			sleep(2000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//Lee información para la barra
    public void leeInfo(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
        	if (cadena.startsWith("MemFree:"))
				libre = buscarNumero(cadena);
        	if (cadena.startsWith("MemTotal:"))
        		total = buscarNumero(cadena);
        }
        b.close();
    }

    public static int getNumColectores(){
    	return colectores.length;
    }

	public int buscarNumero(String cadena){
    	String aux = "";
        char[] arreglo = cadena.toCharArray();
        for (char caracter : arreglo){
            if (Character.isDigit(caracter))
                aux = aux + caracter;
        }   
        return Integer.parseInt(aux);
    }

    public String obtenBarra(int ocupado){
		String barra = "";
		for (int i =0; i < ((Interfaz.WIDE*ocupado)/200)-((ocupado)/15); i++) {
			barra = barra + '#';
		}
		for (int i =0; i < (((Interfaz.WIDE*100)-1000-(ocupado*Interfaz.WIDE)+(10*ocupado))/200); i++) {
			barra = barra + '·';
		}
		barra = barra.concat(" "+ocupado + "%");
		return barra;
	}
}