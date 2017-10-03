import java.util.ArrayList;
import java.io.Serializable;

public class Directorio implements Serializable {

	String nombre;

	ArrayList<String> nombres_arch = new ArrayList<String>();
	ArrayList<Inodo> inodos_arch = new ArrayList<Inodo>();
	ArrayList<Directorio> lista_directorios = new ArrayList<Directorio>();

	Directorio(){
		nombre = "R";
	}

	Directorio(String name){
		nombre = name;	
	}

	public void entra(String nombre, char tipo){
		if (nombres_arch.contains(nombre))
			System.out.println("Ya existe un archivo o directorio con ese nombre, intenta con otro");
		else{
			nombres_arch.add(nombre);
			Inodo i = new Inodo(nombres_arch.indexOf(nombre), tipo);
			inodos_arch.add(i);
			if (tipo == 'd') {

			}
		}
	}
}