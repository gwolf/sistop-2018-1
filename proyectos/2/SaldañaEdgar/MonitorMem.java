import java.util.concurrent.Semaphore;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class MonitorMem extends Thread {

	private Semaphore llegaMem;
	private Semaphore llegaCPU;
	private Semaphore lleganMonitores;

	private static int libre;
	private static int total;

	MonitorMem(Semaphore llegaMem, Semaphore llegaCPU, Semaphore lleganMonitores){
		this.llegaMem = llegaMem;
		this.llegaCPU = llegaCPU;
		this.lleganMonitores = lleganMonitores;
	}

	public void run(){

		while(true){

			try{
				leeInfo("/proc/meminfo");
			}catch(Exception e){
				e.printStackTrace();
			}

			MonitorGral.clearMem();
			MonitorGral.addMem("MEMORIA");
			//El argumento de obtenBarra es el porcentaje ocupado
			MonitorGral.addMem(obtenBarra(100-(libre*100)/total));
			MonitorGral.addMem(Interfaz.getLinea());
			MonitorGral.addMem("total: "+total+" kb");
			MonitorGral.addMem("libre: "+libre+" kb");
			;
			MonitorGral.addMem("hola hola hola hola");
			try{
				sleep(5000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			MonitorGral.addMem("hola 5");
			MonitorGral.addMem("hola 6");
			MonitorGral.addMem("hola 7");
			MonitorGral.addMem("hola 8");

			//Rendezvous
			llegaMem.release();
			try{
					llegaCPU.acquire();
				}catch(Exception e){
					e.printStackTrace();
			}
			//Señal a la interfaz
			lleganMonitores.release();

			try{
				sleep(2000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

    public static void leeInfo(String archivo) throws FileNotFoundException, IOException {
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

	public static int buscarNumero(String cadena){
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