import java.io.Serializable;
import java.util.Random;
import java.util.Calendar;

public class Inodo implements Serializable {

	int num_inodo;
	int longitud;
	int permisos;
	String tipo;
	String fecha_creacion;

	Calendar c1 = Calendar.getInstance();

	public int get_numero() {
		return num_inodo;
	}
	Inodo(char type){
		asignaNum();
		longitud = 0;
		permisos = 7;
		fecha_creacion = c1.getTime().toString();
		tipo = (type == 'd')? "directorio":"archivo";
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
}