import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Sistema implements Serializable {

	static Superbloque superb;
	static Directorio R = new Directorio(); //La raíz
	static String ruta = "R";

	public static void main(String[] args) {

		/* Detecta si ya existe el superbloque, si es así lo carga como objeto, si no, lo crea*/

		File superbloque = new File("superbloque.bin");

		if (superbloque.exists()){
			System.out.println("Reconociendo la configuración del sistema existente");
			configurar();
		}
		else{
			System.out.println("No se detectó el sistema de archivos SISTOP en este volumen, a continuación se montará");
			montar();
		}

		menu();
	}

	public static void montar() {

		superb = new Superbloque();

		/* Guardo los metadatos del superbloque en superbloque.bin*/

		try (FileOutputStream fos = new FileOutputStream("superbloque.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(superb);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void configurar() {

		try (FileInputStream fis = new FileInputStream("superbloque.bin");
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			superb = (Superbloque) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		File raiz = new File("raiz.bin");

		if (raiz.exists()){
			try (FileInputStream fis = new FileInputStream("raiz.bin");
			ObjectInputStream ois = new ObjectInputStream(fis)) {
				R = (Directorio) ois.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public static void guardaRaiz(){
		try (FileOutputStream fos = new FileOutputStream("raiz.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(R);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static int menu(){

		Scanner teclado = new Scanner(System.in);
		String cmd = "NULL";
		String new_file = "NULL";
		String dir = "NULL";

		while (true) {
			System.out.print(Sistema.ruta+">");
			cmd = teclado.nextLine();

			//Separo al comando del argumento
			if (cmd.startsWith("mkd ")){
				new_file = cmd.substring(4);
				cmd = "mkd";
			}

			if (cmd.startsWith("mkf ")){
				new_file = cmd.substring(4);
				cmd = "mkf";
			}

			if (cmd.startsWith("enter ")){
				dir = cmd.substring(6);
				cmd = "enter";
			}

			switch (cmd) {

			 case "help": Comando.help();
			 break;

			 case "ls": Comando.ls(ruta);
			 break;

			 case "super": Comando.superb();
			 break;

			 case "mkf": Comando.mkf(new_file);
			 break;

			 case "mkd": Comando.mkd(new_file);
			 break;

			 case "back": Comando.back();
			 break;

			 case "enter": Comando.enter(dir);			
			 break;

			 case "exit": guardaRaiz();
			 				return 0;

			 default: System.out.println("No reconozco ese comando, por favor intenta de nuevo");
			 break;
			}
		}
	}
}