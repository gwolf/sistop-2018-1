import java.util.ArrayList;
import java.io.Serializable;

public class DirectorioRaiz implements Serializable {

	String nombre_arch;
	Inodo i_nodo;
	ArrayList<String> nombres_arch = new ArrayList<String>();
	ArrayList<Inodo> inodos_arch = new ArrayList<Inodo>();

	public void setDir(String[] args) {
		
	}
	public void getDir(String[] args) {
		
	}

	public void entra(String nombre, char tipo){
		nombres_arch.add(nombre);
		Inodo i = new Inodo(nombres_arch.indexOf(nombre), tipo);
		inodos_arch.add(i);
	}
}