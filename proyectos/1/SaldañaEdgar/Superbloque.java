import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Superbloque {

	/*Descripción del volumen*/
	
	int tamanio_vol;
	int tamanio_bloque;
	String sist_archivos;
	String etiqueta_vol;

	Superbloque(){

		sist_archivos = "SISTEMA_DE_ARCHIVOS_SISTOP";
		etiqueta_vol = System.getProperty("user.dir");


		/* Guardo los metadatos del superbloque en superb.dat*/

		try (FileOutputStream fos = new FileOutputStream("superbloque.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(sist_archivos);
		} catch(Exception e){
			e.printStackTrace();
		}

	}

	Superbloque(String nombre){

	}

	public void getTipoSistema(String[] args) {
		
	}
}