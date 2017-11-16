import java.util.concurrent.Semaphore;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class MonitorCPU extends Thread {

	private Semaphore llegaMem;
	private Semaphore llegaCPU;

	private static int libre;
	private static int total;
	private static int swap;

	MonitorCPU(Semaphore llegaMem, Semaphore llegaCPU){
		this.llegaMem = llegaMem;
		this.llegaCPU = llegaCPU;
	}

	public void run(){

		while (true) {

			try{
				leeInfo("/proc/meminfo");
			}catch(Exception e){
				e.printStackTrace();
			}

			MonitorGral.clearCPU();
			MonitorGral.addCPU("PROCESADOR");
			//El argumento de obtenBarra es el porcentaje ocupado
			MonitorGral.addCPU(obtenBarra(100-(libre*100)/total));
			MonitorGral.addCPU(Interfaz.getLinea());
			MonitorGral.addCPU("total: "+total+" kb");
			MonitorGral.addCPU("libre: "+libre+" kb");
			MonitorGral.addCPU("SWAP: "+swap+" kb");
			;
			/*try{
				sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}*/
			MonitorGral.addCPU("hola 3");
			MonitorGral.addCPU("hola 4");
			MonitorGral.addCPU("hola 5 adiooooos");
			MonitorGral.addCPU("hola 6");
			MonitorGral.addCPU("hola 7");
			MonitorGral.addCPU("hola 8");

			//Rendezvous
			llegaCPU.release();
			try{
					llegaMem.acquire();
				}catch(Exception e){
					e.printStackTrace();
			}

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
        	if (cadena.startsWith("/"))
				swap = buscarNumero(cadena);
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
		return barra.concat(" "+ocupado+ "%");
	}
}