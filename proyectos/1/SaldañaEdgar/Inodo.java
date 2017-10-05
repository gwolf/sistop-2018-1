import java.io.Serializable;

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
		longitud = 0;
		permisos = 7;
		tipo = (type == 'd')? "directorio":"archivo";
	}
}