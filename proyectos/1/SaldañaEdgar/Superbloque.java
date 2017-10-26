import java.io.Serializable;

public class Superbloque implements Serializable {

	/*Descripción del volumen*/
	
	int tamanio_vol;
	int tamanio_bloque;
	String sist_archivos;
	String etiqueta_vol;

	Superbloque(){

		sist_archivos = "SISTEMA_DE_ARCHIVOS_SISTOP";
		etiqueta_vol = System.getProperty("user.dir");
		tamanio_bloque = 4096;
		tamanio_vol = 4096000;
	}
}