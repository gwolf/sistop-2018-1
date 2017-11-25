import java.util.concurrent.Semaphore;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Coordinador extends Thread {

	private Semaphore puedesImprimir = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);
	private Semaphore barrera = new Semaphore(0);
	private Semaphore torniquete = new Semaphore(1);
	private static int cuenta = 0;
	//private Interfaz interfaz = new Interfaz(torniquete,puedesImprimir);
	private int libre;
	private int total;

	/*Relación entre la variable a monitorear del sistema
	y la etiqueta que se mostrará en pantalla
	para agregar o eliminar variables qué monitorear
	bastará con agregar o quitar elementos de
	ambas listas.
	*/
	private String[] variable = {"MemTotal:",
								"MemFree:",
								"MemAvailable:",
								"Buffers",
								"Active:",
								"Active(anon)",
								"Inactive(anon)",
								"Inactive:",
								"AnonPages:",
								"SwapTotal",
								"SwapFree",
								"Dirty:"
								};

	private static String[] etiqueta = {"Total",
										"Libre",
										"Disponible",
										"Buffers",
										"Activa",
										"Activa anónima",
										"Inactiva anónima",
										"Inactiva",
										"Páginas anónimas",
										"Swap total",
										"Swap libre",
										"Sucia"
										};

	private static int num_datos = etiqueta.length;
	private static ColectorDeProceso[] colectores = new ColectorDeProceso[num_datos];
	static Semaphore colectoresListos = new Semaphore(1 - num_datos);
	//datos recuperados por los colectores
	private static String[] datos = new String[num_datos];
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_RESET = "\u001B[0m";



	public void run(){

		while(true){
			for (int i = 0; i < etiqueta.length; i++) {
				new ColectorDeProceso(i,variable[i],etiqueta[i],mutex,barrera,torniquete,puedesImprimir).start();
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

			//Espero a que todos hayan pasado la barrera
			try{
				colectoresListos.acquire();
			}catch(Exception e){
				e.printStackTrace();
			}

			for (int i = 0; i < datos.length; i++)
				Monitor.addDatos(datos[i]);

			new Interfaz(torniquete,puedesImprimir).start();

			try{
				sleep(1500);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	//Lee información para la barra de utilización de la memoria
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
		for (int i =0; i < ((Interfaz.WIDE*ocupado)/100)-((ocupado)/20); i++) {
			barra = barra + '#';
		}
		for (int i =0; i < Interfaz.WIDE-5-(((Interfaz.WIDE*ocupado)/100)-((ocupado)/20)); i++) {
			barra = barra + '·';
		}
		barra = barra.concat(" "+ocupado + "%");
		return ANSI_YELLOW+barra+ANSI_RESET;
	}

	public static int getNumColectores(){
    	return colectores.length;
    }
    public static int getCuenta(){
    	return cuenta;
    }
    public static void addCuenta(){
    	cuenta++;
    }
    public static void addDato(int i, String dato){
    	datos[i] = dato;
    }
}