package pdl12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//prueba
class Pair<I,D>{
	private int estado;
	private char acciones;

	Pair(int estado,char acciones){
		this.estado = estado;
		this.acciones = acciones;
	}

	public int getEstado() {
		return estado;
	}

	public char getAcciones() {
		return acciones;
	}
}
//CLASE AFD ---------------------------------------------------------------
class AFD{
	//Pasamos estado->pasamos  caracter->elegimos Estado o Accion
	Map<Integer, Map<Character, Pair<Integer, Character>>> mt;
	ArrayList<String> palabrasReservadas; 
	ArrayList<Integer> estadosFinales;
	BufferedReader br;

	//Variable que guarda el estado actual
	int estado;
	//Variable que nos dice la linea actual
	int posicionDeLinea;

	//Constructor de AFD que inicializa el set y el array de las palabras reservadas y recibe las lineas del fichero fuente
	public AFD(BufferedReader br) {
		this.estado = 0;
		this.posicionDeLinea = 0;
		mt = new HashMap<Integer,Map<Character,Pair<Integer,Character>>>();
		Map<Character,Pair<Integer,Character>> colE0 = new HashMap<Character,Pair<Integer,Character>>();
		Map<Character,Pair<Integer,Character>> colE1 = new HashMap<Character,Pair<Integer,Character>>();
		colE0.put('a', new Pair<Integer,Character>(1,'A'));
		colE1.put('a', new Pair<Integer,Character>(1,'B'));
		//		colE1.put('a', new Pair<Integer,Character>(1,'B'));
		colE1.put('z', new Pair<Integer,Character>(4,'C'));
		mt.put(0, colE0);
		mt.put(1, colE1);
		this.palabrasReservadas = new ArrayList<String>(Arrays.asList("for","var","int","boolean","string","void","output","input","if","else","then","do","while","function","return"));
		//		this.estadosFinales = new ArrayList<Integer>(Arrays.asList(1,10,1));
		this.br = br;
	}

	//	public void imprimirMapa() {
	//		System.out.println(mt.get(0).get('a').getEstado());
	//		System.out.println(mt.get(0).get('a').getAcciones());
	//		
	//	}

	//Método principal que me devuelve el token generado
	public Token getToken () throws IOException {
		estado = 0;
		//Nos servirá para detectar EOF
		int c = 0;
		//Si no lee EOF, hacemos un casting de char a c
		char car;
		char accion;
		StringBuilder lexema = new StringBuilder();
		String auxLexema;
		int valor = 0;
		Token token = null;
		while(c != -1) {
			c = leer();
			if(c!=-1) {
				//Si no hemos llegado al final, convertimos el dato leido de c y lo convertimos en char car
				car = (char)c;
				System.out.print(c);
				System.out.print(car); 

			}
			else {
				break;
			}
			
			if(car  == '\r') {
				c = leer();
					System.out.println("se ha leido \\r");
				if(c == '\n') {
					System.out.println("se ha leido \\n");
					continue;
					//										c = leer();
					//							
					linea
				}
			}
			
			
			accion = accion(estado,identificar(car));
			//						System.out.println(accion);
			estado = estado(estado,identificar(car));
			//						System.out.println(estado);
			//									System.out.print(car); 
			if(estado == -1 ) {
				//				if(car =='\r');
				car ='s';
				genError("Se ha leido un caracter invalido: " + car, posicionDeLinea);
			}
			else {
				switch(accion) {
				case 'A': //
					lexema.append(car);
					break;
				case 'B':
					lexema.append(car);
					break;
				case 'C':
					auxLexema = lexema.toString();

					auxLexema.trim();
					//					System.out.println(auxLexema);
					if(esPalabraReservada(auxLexema)) {
						genToken(20,auxLexema);
						estado = 0;
						lexema.delete(0, auxLexema.length());

					}
					else {
						palabrasReservadas.add(auxLexema);
						genToken(21,String.valueOf(palabrasReservadas.indexOf(auxLexema)));
						lexema.delete(0, auxLexema.length());
					}
					break;

				}
			}
		}
		return token;
	}
	//Devuelve a si es letra, y devuelve b si es numero......
	//Devuelve el caracter c en caso contrario
	private char identificar(char c) {
		if(Character.isLetter(c)) {
			return 'a';
		}
		else if(Character.isDigit(c)) {
			return 'b';
		}
		else if( c=='"'){
			return 'd';
		}
		else if (c=='+'){
			return 'e';
		}
		else if(c=='='){
			return 'f';
		}
		else if (c=='&'){
			return 'g';
		}
		else if (c== '|'){
			return 'h';
		}
		else if (c=='('){
			return 'i';
		}
		else if (c==')'){
			return 'j';
		}
		else if (c==','){
			return 'k';
		}
		else if (c==';'){
			return 'n';
		}
		else if (c=='{'){
			return 'o';
		}
		else if (c=='}'){
			return 'p';
		}
		else{
			return 'z';
		}

	}

	//Método para ver si palabra es una palabra reservada
	private boolean esPalabraReservada(String palabra) {
		return palabrasReservadas.contains(palabra);
	}

	//Nos devuelve el valor de accion en la matriz 
	public char accion(int estado,char c) {
		if(mt.get(estado).get(c)==null)
			return 1;
		return mt.get(estado).get(c).getAcciones();
	}

	//Nos devuelve el valor de estado en la matriz 
	public int estado(int estado,char c) {
		if(mt.get(estado).get(c) == null)
			return -1;
		return mt.get(estado).get(c).getEstado();
	}

	//TODO Crea un token con la correspondiente categoria lexica y lo escribe en el fichero de tokens
	private Token genToken(int categoriaLexica, String cadena) {
		Token token = null;
		if(categoriaLexica <0);

		else { //Si es una categoriaLexica que no necesita atributos
			token = new Token(categoriaLexica,cadena);
			System.out.println((posicionDeLinea + 1) +  ":" + token);
		}
		return token;

	}
	//Genera e imprime en la salida de err el error lexico detectado
	private void genError(String mensaje, int linea) {
		Error error = new Error(mensaje,linea + 1);
		System.err.println(error);
	}

	//Leemos de la linea linea el caracter de la posicion posCaracter
	private int leer() throws IOException { 
		int c = br.read();
		return  c;
	}
}