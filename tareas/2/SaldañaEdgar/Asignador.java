/*Asignación de procesos en memoria por peor ajuste*/

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Asignador {

	static char[] memoria = new char[30];
	static ArrayList<Character> procesosActivos = new ArrayList<Character>();

	public static void main(String[] args){

		inicializa(memoria);
		System.out.println("Memoria inicializada:\n");
		imprime(memoria);

		menu();
	}

	public static int menu(){

		Scanner teclado = new Scanner(System.in);
		String opcion = "z";

		System.out.println("Escribe a para agregar proceso, s para suprimir, q para salir");

		while (true) {

			opcion = teclado.nextLine();

			switch (opcion) {
				case "a": asignaProceso();
							imprime(memoria);
				break;
				case "s": eliminaProceso();
							imprime(memoria);
				break;
				case "q": return 0;
				default: System.out.println("No reconozco ese comando");
			}
		}	
	}

	//Verifica si hay espacio suficiente para un proceso
	//Verifica si hay espacio libre contiguo en memoria y asigna el proceso en el bloque libre más grande (Peor ajuste)
	public static void asignaProceso(){
		char[] proceso = nuevoProceso();
		
		if (proceso.length <= libres()) {
			if (proceso.length <= libresContiguos()) {
				int indice = obtenIndice();
				System.out.println("Entra proceso ("+proceso[0]+") de tamaño "+proceso.length);
				for (int i = 0; i < proceso.length; i++) {
					memoria[indice+i] = proceso[i];
				}
			}else{
				compacta();
				int indice = obtenIndice();
				System.out.println("Entra proceso ("+proceso[0]+") de tamaño "+proceso.length);
				for (int i = 0; i < proceso.length; i++) {
					memoria[indice+i] = proceso[i];
				}
			}
			procesosActivos.add(proceso[0]);
		}else{
			System.out.println("No hay espacio suficiente para el proceso "+proceso[0]);
		}	
	}

	//Elige al azar una letra y la asigna como un nuevo proceso que es un arreglo con tamaño al azar de 2 a 15
	public static char[] nuevoProceso(){
		boolean asignado = false;
		char nombre = '?';

		while (!asignado) {
			int r = new Random().nextInt(90-65)+65;
			char letra = (char) r;
			if(procesosActivos.contains(letra)){}
			else{
				nombre = letra;
				asignado = true;
			}
		}
		
		int tamanio = new Random().nextInt(16-2)+2;
		char[] proceso = new char[tamanio];
		for (int i = 0; i < tamanio; i++) {
			proceso[i] = nombre;
		}

		return proceso;
	}

	//Recorre los procesos para que quede solo un bloque libre
	public static void compacta(){
		char[] aux = new char[30];
		aux = inicializa(aux);
		int desplazamiento = 0;

		for (int i = 0; i < 30; i++) {
			if(memoria[i] == '-'){
				desplazamiento++;
			}else{
				aux[i-desplazamiento] = memoria[i];
			}
		}
		memoria = aux;
		System.out.println("**Procesos compactados**");
		imprime(memoria);
	}

	//Llena a un arreglo de espacios en blanco '-'
	public static char[] inicializa(char[] arreglo){
		for (int i = 0; i < arreglo.length; i++) {
			arreglo[i] = '-';
		}
		return arreglo;
	}

	public static void imprime(char[] arreglo){
		for (int i = 0; i < arreglo.length; i++) {
			System.out.print(arreglo[i]);
		}
		System.out.println("\n");
	}

	//Encuentra el primer índice del bloque libre más grande
	public static int obtenIndice(){
		int i_inicio = 0;
		int i_final = 0;
		int indice = 0;
		int tamanio = 0;

		for (int i = 0; i < memoria.length; i++) {
			if (memoria[i] == '-') {
				i_inicio = i;
				i_final = i;
				for (int j = i; memoria[j] == '-' && j < memoria.length-1; j++) {
					i_final++;
				}
				if ((i_final-i_inicio) > tamanio) {
					i = i_final;
					tamanio = (i_final-i_inicio)+1;
					indice = i_inicio;
				}
			}
		}
		return indice;
	}

	//Elimina un proceso al azar
	public static void eliminaProceso(){
		if (!procesosActivos.isEmpty()) {
			int r = new Random().nextInt(procesosActivos.size());
			char nombre = procesosActivos.remove(r);

			for (int i = 0; i < memoria.length; i++) {
				if(memoria[i] == nombre)
					memoria[i] = '-';
			}
			System.out.println("Se eliminó al proceso "+nombre);
		}
	}

	//Devuelve el espacio libre total (contiguo y no contiguo) de la memoria
	public static int libres(){
		int contador = 0;
		for (int i = 0; i < memoria.length; i++) {
			if(memoria[i] == '-')
				contador++;
		}
		return contador;
	}

	//Devuelve el tamaño del bloque libre más grande
	public static int libresContiguos(){
		int i_inicio = 0;
		int i_final = 0;
		int tamanio = 0;

		for (int i = 0; i < memoria.length; i++) {
			if (memoria[i] == '-') {
				i_inicio = i;
				i_final = i;
				for (int j = i; memoria[j] == '-' && j < memoria.length-1; j++) {
					i_final++;
				}
				if ((i_final-i_inicio) > tamanio) {
					i = i_final;
					tamanio = (i_final-i_inicio)+1;
				}
			}
		}
		return tamanio;
	}
}