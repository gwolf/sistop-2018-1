/*Solo contiene una lista dinámica donde se guardan los objetos Directorio,
los archivos regulares no se guardan aquí, se guardan directamente en el volumen del sistema*/

import java.util.ArrayList;
import java.io.Serializable;

public class Datos implements Serializable{

	ArrayList<Directorio> directorios = new ArrayList<Directorio>();
}