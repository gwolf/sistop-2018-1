import java.util.concurrent.Semaphore;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;

class ColectorDeProceso extends Thread {

	String ruta = "/proc/";
	String var;
	String etiqueta;
	int id;

	Semaphore mutex;
	Semaphore barrera;
	int num_hilos = Coordinador.getNumColectores();
	int cuenta = 0;

	ColectorDeProceso(int id, String archivo, String var, String eti, Semaphore mutex, Semaphore barrera){
		this.ruta = ruta.concat(archivo);
		this.var = var;
		this.id = id;
		this.etiqueta = eti;
		this.mutex = mutex;
		this.barrera = barrera;
	}

	public void run(){

        String cadena;
        try{
        	FileReader f = new FileReader(ruta);
        	BufferedReader b = new BufferedReader(f);
        
        	while((cadena = b.readLine())!=null) {
	        	if (cadena.startsWith(var)){
	        		agregaDatos(cadena);
	        	}
        	}
        b.close();
		}catch(Exception e){
        	e.printStackTrace();
        }
	}

	public int buscarNumero(String cadena){
    	String aux = "";
        char[] arreglo = cadena.toCharArray();
        for (char caracter : arreglo){
            if (Character.isDigit(caracter))
                aux += caracter;
        }   
        return Integer.parseInt(aux);
    }

    //Sección crítica de la barrera
    public void agregaDatos(String cadena){
    	
    	
    	Monitor.addDatos(id+3,etiqueta +" "+ buscarNumero(cadena));
    }
}