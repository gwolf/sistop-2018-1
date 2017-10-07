import java.io.File;



public class Comando {

	//Definición de constantes para colorear el texto
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	//Listar contenido del directorio
	public static void ls(String ruta) {

		String[] lista = ruta.split(">");
		String dir = lista[lista.length-1];
		String tipo = "NULL";

		if (dir.equals("R")) {
			for (int i = 0; i < Sistema.R.nombres_arch.size(); i++) {

				if((Sistema.R.inodos_arch.get(i).tipo.equals("archivo")))
					tipo = ANSI_BLUE+"archivo   "+ANSI_RESET;
				else
					tipo = ANSI_PURPLE+"directorio"+ANSI_RESET;

				System.out.println(ANSI_YELLOW+ Sistema.R.nombres_arch.get(i)+ANSI_RESET+"\t\t"+ tipo+"\t"+Sistema.R.inodos_arch.get(i).fecha_creacion+"\t"+Sistema.R.inodos_arch.get(i).longitud+" bytes"+"\t   "+Sistema.R.inodos_arch.get(i).get_numero());
			}
		}else{
			//Datos.lista_directorios();
			System.out.println(dir);
		}
	}

	//Crear directorio y lo guarda como objeto en el espacio de datos
	public static void mkd(String nombre) {
		
		if(esValido(nombre)){
			Inodo i = new Inodo('d');
			Sistema.R.inodos_arch.add(i);
			Sistema.R.nombres_arch.add(nombre);
			Directorio n = new Directorio(nombre);
			Sistema.dd.directorios.add(n);
		}
	}

	//Crear archivo
	public static void mkf(String nombre) {

		if(esValido(nombre)){
			Inodo i = new Inodo('f');
			Sistema.R.inodos_arch.add(i);
			Sistema.R.nombres_arch.add(nombre);
			/*try{
				Runtime p = Runtime.getRuntime();
				p.exec ("nano");
				System.out.println("bien");
			}
			catch (Exception e){
			e.printStackTrace();
			}*/
		}
	}

	//Eliminar archivo o directorio si es que existe
	public static void rem(String nombre){
		int i = Sistema.R.nombres_arch.indexOf(nombre);
		if (i != -1) {
			Sistema.R.inodos_arch.remove(i);
			String nom = Sistema.R.nombres_arch.remove(i);
			Sistema.dd.directorios.remove(nom);	
			Sistema.R.inodos_ocupados.remove(i);		
		}
	}

	//Abrir archivo
	public static void open() {
	}

	public static void back() {
		if (Sistema.ruta.equals("R")) {
			System.out.println("Ya estás en el directorio raíz");
		}else{
			String[] lista = Sistema.ruta.split(">");
			Sistema.ruta = "";
			for (int i = 0; i<lista.length-1; i++) {
				Sistema.ruta = Sistema.ruta.concat(lista[i]);
				if(i != lista.length-2)
					Sistema.ruta = Sistema.ruta.concat(">");
			}
		}
	}

	//Entrar a directorio
	public static void enter(String dir) {
		if (!Sistema.R.nombres_arch.contains(dir)) {
			System.out.println("No existe ese directorio, para crearlo escribe mkd "+dir);
		}else{
			int inodo = Sistema.R.nombres_arch.indexOf(dir);
			if (Sistema.R.inodos_arch.get(inodo).tipo.equals("directorio")){
				Sistema.ruta = Sistema.ruta.concat(">"+dir);

			}
			else
				System.out.println(dir+" no es un directorio, imposible acceder");
		}	
	}

	//Lista la ayuda
	public static void help() {
		System.out.println("Sección de ayuda");
		System.out.println("COMANDO\t\tDESCRIPCIÓN");
		System.out.println("ls\t\tLista el contenido del directorio donde se ejecuta");
		System.out.println("mkd <nombre>\tCrea un directorio con el nombre especificado");
		System.out.println("enter <nombre>\t Entra al directorio especificado");
		System.out.println("mkf\t\tCrea un archivo");
		System.out.println("open\t\tAbre un archivo");
		System.out.println("super\t\tMuestra la información del superbloque");
		System.out.println("help\t\tMuestra la ayuda del sistema");
	}

	//Lista metadatos del superbloque
	public static void superb() {

		System.out.println("El volumen se llama: "+Sistema.superb.etiqueta_vol);
		System.out.println("El sistema de archivos se llama: "+Sistema.superb.sist_archivos);
		System.out.println("Tamaño del volumen: "+Sistema.superb.tamanio_vol+" bytes");
		System.out.println("Tamaño del bloque: "+Sistema.superb.tamanio_bloque+" bytes");
	}

	private static boolean esValido(String nombre){
		if (Sistema.R.nombres_arch.contains(nombre)){
			System.out.println("Ya existe un archivo o directorio con ese nombre, intenta con otro");
			return false;
		}
	return true;
	}
}