import java.io.Serializable;

public class Inodo implements Serializable {

	int num_inodo;
	int longitud;
	int permisos;
	String tipo;
	// ult_acceso;
	
	String tipo_arch;

	public static void get_numero() {
		Superbloque superb = new Superbloque();
	}
	Inodo(int num, char type){
		num_inodo = num;
		longitud = 0;
		permisos = 7;
		tipo = (type == 'd')? "directorio":"archivo";
	}
}