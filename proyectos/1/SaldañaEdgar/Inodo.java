import java.io.Serializable;
import java.util.Random;

public class Inodo implements Serializable {

	int num_inodo;
	int longitud;
	int permisos;
	String tipo;
	// ult_acceso;

	public int get_numero() {
		return num_inodo;
	}
	Inodo(char type){
		asignaNum();
		longitud = 0;
		permisos = 7;
		tipo = (type == 'd')? "directorio":"archivo";
	}

	private void asignaNum(){
		int elementos =  Sistema.R.inodos_arch.size();
		
		Random r = new Random();
		int valorDado = r.nextInt(elementos*10)+1;
		num_inodo = valorDado;
	}
}