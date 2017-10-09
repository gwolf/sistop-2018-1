import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;

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

		String dir_actual = getDirActual(ruta);
		String tipo = "NULL";

		if (dir_actual.equals("R")) {
			for (int i = 0; i < Sistema.R.nombres_arch.size(); i++) {

				if((Sistema.R.inodos_arch.get(i).tipo.equals("archivo")))
					tipo = ANSI_BLUE+"archivo   "+ANSI_RESET;
				else
					tipo = ANSI_PURPLE+"directorio"+ANSI_RESET;

				String permisos = "";
				if (Sistema.R.inodos_arch.get(i).lectura == true) {
					permisos = permisos.concat("R");
				}else{
					permisos = permisos.concat("-");
				}
				if (Sistema.R.inodos_arch.get(i).escritura == true) {
					permisos = permisos.concat("W");
				}else{
					permisos = permisos.concat("-");
				}

				System.out.println(ANSI_YELLOW+ String.format("%1$-20s",Sistema.R.nombres_arch.get(i))+ANSI_RESET+"\t"+ tipo+"\t"+Sistema.R.inodos_arch.get(i).fecha_creacion+"\t"+String.format("%1$4d",Sistema.R.inodos_arch.get(i).longitud)+" bytes"+"\t"+permisos+"  "+Sistema.R.inodos_arch.get(i).getNumero());
			}
		}
	}

	//Crear directorio y lo guarda como objeto en el espacio de datos
	public static void mkd(String nombre) {
		
		if (nombre.contains(" ")) {
			System.out.println("No se admiten nombres con espacios en blanco, intenta nuevamente");
		}else {
			if(esValido(nombre) && !nombre.equals("")){
			Inodo i = new Inodo();
			Sistema.R.inodos_arch.add(i);
			Sistema.R.nombres_arch.add(nombre);
			Directorio n = new Directorio(nombre);
			Sistema.dd.directorios.add(n);
			}
		}	
	}

	//Crear archivo
	public static void mkf(String nombre) {

		if (nombre.contains(" ")) {
			System.out.println("No se admiten nombres con espacios en blanco, intenta nuevamente");
		}else {
			if(esValido(nombre) && !nombre.equals("")){
				try{
					File archivo = new File(nombre);
					archivo.createNewFile();
					Inodo i = new Inodo(archivo);
					Sistema.R.inodos_arch.add(i);
					Sistema.R.nombres_arch.add(nombre);
				}
				catch (Exception e){
				e.printStackTrace();
				}
			}
		}
	}

	//Editar archivo
	public static void add(String nombre){

		int i = Sistema.R.nombres_arch.indexOf(nombre);

		if(Sistema.R.inodos_arch.get(i).escritura){
			File archivo = new File(nombre);
			BufferedWriter bw;
			Scanner teclado = new Scanner(System.in);
			if (!archivo.exists()) {
				System.out.println("El archivo no existe, puedes crearlo escribiendo mkf "+nombre);
			}else {
				try{
					FileWriter f = new FileWriter(archivo, true);
					bw = new BufferedWriter(f);
					System.out.println("Escribe lo que agregarás al archivo: ");
	      			bw.write(teclado.nextLine());
					bw.close();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				//Se actualiza el nuevo tamaño del archivo
				int in = Sistema.R.nombres_arch.indexOf(nombre);
				Sistema.R.inodos_arch.get(in).update(archivo);
			}
		}else{
			System.out.println("No tienes permiso para escribir en este archivo");
		}
	}

	//Eliminar archivo o directorio si es que existe
	public static void rem(String nombre){
		int i = Sistema.R.nombres_arch.indexOf(nombre);
		if (i != -1) {
			Sistema.R.inodos_arch.remove(i);
			String nom = Sistema.R.nombres_arch.remove(i);
			//Si no se trata de un directorio, elimina al archivo
			if(!Sistema.dd.directorios.remove(nom)){
				File arch = new File(nombre);
				arch.delete();
			}	
			Sistema.R.inodos_ocupados.remove(i);		
		}else{
			System.out.println("El archivo o directorio no existe");
		}
	}

	public static int chmod(String operacion){
		String[] args = operacion.split(" ");
		if (args.length != 2) {
			System.out.println("Imposible completar la operación, invoca a chmod así:");
			System.out.println("chmode <nombre_de_archivo> <permisos_sin_espacios>");
			return 1;
		}
		String nom_archivo = args[0];
		String cambios = args[1];
		int i = Sistema.R.nombres_arch.indexOf(nom_archivo);
		Inodo inodo = Sistema.R.inodos_arch.get(i);

		if (cambios.contains("+r")) {
			inodo.lectura = true;
		}
		if (cambios.contains("-r")) {
			inodo.lectura = false;
		}
		if (cambios.contains("+w")){
			inodo.escritura = true;
		}
		if (cambios.contains("-w")) {
			inodo.escritura = false;
		}

		return 0;
	}

	//Abrir archivo
	public static void look(String nombre) {

		int i = Sistema.R.nombres_arch.indexOf(nombre);

		if(Sistema.R.inodos_arch.get(i).lectura){
			File archivo = new File(nombre);
			if (archivo.exists()) {
				String cadena;
				try{
					FileReader f = new FileReader(archivo);
					BufferedReader b = new BufferedReader(f);
					while((cadena = b.readLine())!=null) {
						System.out.println(cadena);
					}
					b.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("El archivo no existe");
			}
		}else{
			System.out.println("No tienes permiso para leer el archivo");
		}
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

		System.out.println(ANSI_GREEN+String.format("\n%1$-26s"+"%2$-15s","COMANDO","DESCRIPCIÓN")+ANSI_RESET);
		System.out.println(String.format("%1$-26s"+"%2$-15s","ls","Lista el contenido del directorio donde se ejecuta"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","mkd <nombre>","Crea un directorio con el nombre especificado"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","enter <nombre>","Entra al directorio especificado"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","back","Regresa al directorio anterior"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","rem <nombre>","Elimina al archivo o directorio especificado"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","add <nombre>","Agrega texto al archivo especificado"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","mkf <nombre>","Crea un archivo"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","look <nombre>","Muestra el contenido de el archivo especificado"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","chmod <nombre> <permisos>","Permisos posibles: +w +r -w -r. Modifica permisos del archivo"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","super","Muestra la información del superbloque"));
		System.out.println(String.format("%1$-26s"+"%2$-15s","help","Muestra la ayuda del sistema"));
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

	private static String getDirActual(String ruta){
		String[] lista = ruta.split(">");
		return lista[lista.length-1];
	}
}