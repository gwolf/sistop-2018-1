import java.io.File;

public class Comando {

	//Listar contenido del directorio
	public static void ls(String ruta) {

		String[] lista = ruta.split(">");
		String dir = lista[lista.length-1];
		String tipo = "NULL";

		if (dir.equals("R")) {
			for (int i = 0; i < Sistema.R.nombres_arch.size(); i++) {

			if((Sistema.R.inodos_arch.get(i).tipo == "archivo"))
				tipo = "archivo   ";
			else
				tipo = "directorio";
			System.out.println(Sistema.R.inodos_arch.get(i).get_numero()+"\t"+ tipo+"\t"+Sistema.R.inodos_arch.get(i).longitud+" bytes"+"\t\t"+Sistema.R.nombres_arch.get(i));
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
			Sistema.R.inodos_arch.get(Sistema.R.inodos_arch.size()-1).num_inodo = Sistema.R.inodos_arch.size()-1;
			Sistema.R.nombres_arch.add(nombre);
			Directorio n = new Directorio(nombre);
			Sistema.dd.directorios.add(Sistema.R.inodos_arch.size()-1,n);
		}
		//File dir = new File(nombre);
		//dir.mkdir();
	}

	//Crear archivo
	/*public static void mkf(String nombre) {
		Sistema.R.entra(nombre,'f');
		try{
			Runtime p = Runtime.getRuntime();
			p.exec ("firefox");
			System.out.println("bien");
		}
		catch (Exception e)
		{e.printStackTrace();}
	}*/

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