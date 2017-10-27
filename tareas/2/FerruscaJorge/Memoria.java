import java.util.Scanner;

/* El programa asigna cada por cada una de las letras del abecedario, es decir 25 VECES */

public class Memoria{
	static StringBuilder porcionMemoria = new StringBuilder("----------------"); //PORCION DE MEMORIA
	static String memToSplit = porcionMemoria.toString();	
	static Scanner teclado = new Scanner(System.in);
	public static int asigna(char nombre, int tamanio){
		try{
		String[] available = null;
		boolean asignado = false;
		int cuenta = 0;
		int salte=0;
			
			memToSplit = porcionMemoria.toString();
			available = memToSplit.split("[ABCDEFGHIJKLMNOPQRSTUVWYZ]"); 
			if(available[0] != null){
				//String mayor = available[0];
				//for(int a = 0; a < available.length-1; a++){
				//	if(available[a+1].length() > mayor.length())
				//		mayor = available[a+1]; 
				//}
				// System.out.println("mayor"+mayor); func
				int espacio = 0;
				int i = 0;
				while(i < (porcionMemoria.length()-1)){
					if(porcionMemoria.charAt(i) == '-'){
						int pivote = i;
						if(tamanio == 1){
							porcionMemoria.setCharAt(i, nombre);
							asignado = true;
							return 0;
						}
						int acumulado = 1; //hay una localidad disponible
						i++;
						if(i > (porcionMemoria.length()-1)){
							return 1; //se acabó el la memoria y no se encontró suficiente espacio
						}
						while(porcionMemoria.charAt(i) == '-'){
							acumulado++;
							if (acumulado >= tamanio){ //SE HACE LA SUSTITUCION
								for(int aux = 0; aux < tamanio; aux++){
									porcionMemoria.setCharAt(pivote, nombre);
									pivote++;
								}
								return 0; //se seteó el proceso con éxito
							}
							i++;
							if(i > (porcionMemoria.length()-1)){
								return 1; //otra vz se acabó la "memoria" y no hubo suficiente espacio
							}
						}
					} else {
						if(i > (porcionMemoria.length()-1))
							return 1;
						i++;
					}
				}
			} else {
				System.out.println("Ya no hay espacio disponible");
				return 1;
			}

			return 1;

		} catch(Exception e){
			System.out.println("Stack overflow, el programa se HA BLOQUEADO :(");
			return 1;
		}
		
	}
	
	public static int verificaCompresion(){
		int i = 0;
		System.out.println("**Compactacion requerida **");
		while(i < (porcionMemoria.length()-1)){
			if(porcionMemoria.charAt(i) == '-'){
				i++;
				if(i > (porcionMemoria.length()-1)){
					System.out.println("No se pudo realizar ninguna compactación!!");
					return 1; //No hubo compresion de ningun caracter
				}
				while(porcionMemoria.charAt(i) == '-'){
					i++;
					if(i > (porcionMemoria.length()-1)){
						System.out.println("No se pudo realizar ninguna compactación!!");
						return 1; //No hubo compresion de ningun caracter
					}
				}
				return 0; //Al menos unca localidad para hacer compactación	
			} else {
				i++;
				if(i > (porcionMemoria.length()-1)){
					System.out.println("No se pudo realizar ninguna compactación!!");
					return 1; //No hubo compresion de ningun caracter
				}
			}
		}
		System.out.println("Espacio insuficiente para hacer compactación.");
		return 1;
	}

	public static int comprimir(){
		int [] posiciones = new int[porcionMemoria.length()-1];
		int aux = 0;
		int indicesRecorridos = 1;
		for(int i = 0 ; i < porcionMemoria.length(); i++){
			if(porcionMemoria.charAt(i) == '-'){
				posiciones[aux] = i;
				System.out.println("posiciones["+aux+"] : "+posiciones[aux]);
				aux++;
			}	
		}
		for(int i = 0; i < aux; i++){ 
			porcionMemoria.deleteCharAt(posiciones[i]);
			try{
				posiciones[i+1] = posiciones[i+1] - indicesRecorridos; //se recorren los indices al borrar por lo tanto restamos uno al index original
				indicesRecorridos++;
			} catch(Exception e){}
		}
		for(int i = 0; i < aux; i++){
			porcionMemoria.append("-");
		}
		System.out.println("Compactación realizada con éxito!!");
		return 0;
	}

	public static int liberarMemoria (char nombre, int tamanio){ 
		boolean existe = false;
		System.out.println("Teclee el nombre del proceso a borrar: ");
		String conv = teclado.nextLine();
		char c =  Character.toUpperCase(conv.charAt(0));
		for(int i = 0; i < (porcionMemoria.length()-1); i++){
			if(porcionMemoria.charAt(i) == c){
				existe = true;
			}
		}
		if (!existe){
			System.out.println("No existe proceso con ese nombre");
			return 1;
		}
		memToSplit = porcionMemoria.toString();
		String cambia = ""+c;
		memToSplit = memToSplit.replaceAll(cambia, "-");
		porcionMemoria.replace(0, memToSplit.length()-1, memToSplit);
		if(Tabla.asigna(nombre, tamanio) == 1)
			return 1;
		else
			return 0;
	}
	
}