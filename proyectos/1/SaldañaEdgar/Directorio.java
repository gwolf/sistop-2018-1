import java.util.ArrayList;
import java.io.Serializable;

public class Directorio implements Serializable {

	String nombre;

	ArrayList<String> nombres_arch = new ArrayList<String>();
	ArrayList<Inodo> inodos_arch = new ArrayList<Inodo>();

	Directorio(){
		nombre = "R";
	}

	Directorio(String name){
		nombre = name;	
	}
}