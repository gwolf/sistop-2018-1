import java.io.Serializable;
import java.io.File;
import java.util.Random;
import java.util.Calendar;

public class Inodo implements Serializable {

	int num_inodo;
	long longitud;
	boolean lectura;
	boolean escritura;
	String tipo;
	String fecha_creacion;

	Calendar c1 = Calendar.getInstance();

	public int getNumero() {
		return num_inodo;
	}
	//Constructor para cuando es un directorio
	Inodo(){
		asignaNum();
		longitud = 0;
		escritura = true;
		lectura = true;
		fecha_creacion = c1.getTime().toString();
		tipo = "directorio";
	}

	//Constructor para cuando es un archivo regular
	//Recibe al archivo para calcular su tamaño
	Inodo(File nombre){
		asignaNum();
		longitud = nombre.length();
		escritura = true;
		lectura = true;
		fecha_creacion = c1.getTime().toString();
		tipo = "archivo";
	}

	private void asignaNum(){

		int elementos =  Sistema.R.inodos_arch.size()+1;
		
		Random r = new Random();
		int valorDado = r.nextInt(elementos*10)+1;
		while (Sistema.R.inodos_ocupados.contains(valorDado)) {
				valorDado++;
		}
		num_inodo = valorDado;
		//Agrego el numero de inodo a la lista de los numeros de inodo ocupados
		Sistema.R.inodos_ocupados.add(valorDado);
	}

	public void update(File nombre){
		longitud = nombre.length();
	}
}