import java.util.ArrayList;
import java.io.Serializable;

public class Directorio implements Serializable {

	String nombre;

	//Lleva la relación entre inodos y nombres de archivos utilizando 
	//listas dinámicas

	ArrayList<String> nombres_arch = new ArrayList<String>();
	ArrayList<Inodo> inodos_arch = new ArrayList<Inodo>();

	ArrayList<Integer> inodos_ocupados = new ArrayList<Integer>();

	//Constructor para la raíz
	Directorio(){
		nombre = "R";
	}

	Directorio(String nam){
		nombre = nam;	
	}
}