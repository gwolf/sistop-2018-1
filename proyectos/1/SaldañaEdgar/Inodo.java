import java.io.Serializable;

public class Inodo implements Serializable {

	String nombre_arch;
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
		tipo = (type == 'd')? "directorio":"archivo";
	}
}