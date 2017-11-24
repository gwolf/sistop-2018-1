import java.util.concurrent.Semaphore;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;

class ColectorDeProceso extends Thread {

	String ruta = "/proc/";
	//variable como está escrita en el sistema
	String var;
	String etiqueta;
	int id;
	
	Semaphore mutex;
	Semaphore barrera;
	int num_hilos = Coordinador.getNumColectores();
	int cuenta;
	

	ColectorDeProceso(int id, String archivo, String var, String eti, Semaphore mutex, Semaphore barrera){
		this.ruta = ruta.concat(archivo);
		this.var = var;
		this.id = id;
		this.etiqueta = eti;
		this.mutex = mutex;
		this.barrera = barrera;
		this.cuenta = cuenta;
	}

	public void run(){
        String cadena;
        try{
        	FileReader f = new FileReader(ruta);
        	BufferedReader b = new BufferedReader(f);
        	while((cadena = b.readLine())!=null) {
	        	if (cadena.startsWith(var))
	        		agregaDatos(cadena);
        	}
        b.close();
		}catch(Exception e){
        	e.printStackTrace();
        }
        //System.out.println("Añado - "+this.getName());
	}

    //Implementación de la barrera
    public void agregaDatos(String cadena){
    	
    	String aux = daFormato(cadena);

    	try{
    		mutex.acquire();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	Coordinador.addCuenta();
    	mutex.release();

    	if(Coordinador.getCuenta() == num_hilos)
    		barrera.release();

    	try{
    		barrera.acquire();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	barrera.release();
    	//Punto crítico
		Coordinador.addDato(id,aux);
		Coordinador.colectoresListos.release();
    }

	public String buscarNumero(String cadena){
    	String aux = "";
        char[] arreglo = cadena.toCharArray();
        for (char caracter : arreglo){
            if (Character.isDigit(caracter))
                aux = aux + caracter;
        }   
        return aux;
    }

    public String daFormato(String cadena){
    	String aux = buscarNumero(cadena);
    	return etiqueta.concat(":    "+aux+" kb");
    }
}