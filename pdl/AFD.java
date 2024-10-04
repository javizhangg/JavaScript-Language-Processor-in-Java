package pdl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//prueba
class Pair {
	private Integer estado; // El estado puede ser null en caso de error
	private Object accion; // La acción puede ser un Character o un Integer

	// Constructor para estado y acción (Character)
	Pair(Integer estado, Character accion) {
		this.estado = estado;
		this.accion = accion;
	}

	// Constructor para error (null estado, Integer acción)
	Pair(Integer error) {
		this.estado = null; // Estado es null en caso de error
		this.accion = error;
	}

	public Integer getEstado() {
		return estado;
	}

	public Object getAccion() {
		return accion;
	}

	public boolean esError() {
		return estado == null;
	}
}

// CLASE AFD ---------------------------------------------------------------
class AFD {
	// Pasamos estado->pasamos caracter->elegimos Estado o Accion
	private ArrayList<String> palabrasReservadas;
	private BufferedReader br;

	private ArrayList<Map<Character, Pair>> mt;
	// Variable que guarda el estado actual
	private int estado;
	// Variable que nos dice la linea actual
	private int posicionDeLinea;
	private Character[] chars;


	// Constructor de AFD que inicializa el set y el array de las palabras
	// reservadas y recibe las lineas del fichero fuente
	public AFD(BufferedReader br) throws IOException {
		this.estado = 0;
		this.posicionDeLinea = 0;

		this.mt = new ArrayList<>();
		// Inicializar la matriz de transiciones
		chars = new Character[] { 'a', 'b', '"', '+', '=', '&', '|', '(', ')', ',', ';', '{', '}', '/', '*', '_' };
		matriz();

		this.palabrasReservadas = new ArrayList<String>(Arrays.asList("var", "int", "boolean", "string", "void",
				"output", "input", "if", "else", "then", "do", "while", "function", "return"));
		this.br = br;
	}

	// METODO 1: PARA INICIALIZAR LA MATRIZ DE TRANSICIONES
	public void matriz() throws IOException {
		try {
			FileReader fr = new FileReader(
					"C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\Matriz.txt");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String linea = null;

		while ((linea = br.readLine()) != null) {
			Map<Character, Pair> colE = new HashMap<Character, Pair>();
			Map<Character, Pair> colA = leerCSV(linea, colE);
			mt.add(colA);
		}
	}

	// METODO 2: PARA INICIALIZAR LA MATRIZ DE TRANSICIONES
	public Map<Character, Pair> leerCSV(String linea, Map<Character, Pair> col) {
		// Dividir la línea usando ';' como delimitador
		String[] arraylinea = linea.split(";");
		int n = 0;
		// Ajustar el bucle para evitar índices fuera de rango y cadenas vacías
		for (int i = 1; i < arraylinea.length; i += 2) {
			// Verificar si las celdas contienen datos
			if (!arraylinea[i].isEmpty() && !arraylinea[i - 1].isEmpty()) {
				char actual = arraylinea[i].charAt(0); // Obtener el carácter
				int estado = Integer.parseInt(arraylinea[i - 1]); // Convertir el estado a entero

				// Añadir al mapa
				col.put(chars[n], new Pair(estado, actual));

				// Imprimir para seguimiento
				// System.out.println("Funcion: " + actual + ", Estado : " + estado);
			} else {
				// Obtener el carácter
				int error = Integer.parseInt(arraylinea[i]); // Convertir el estado a entero

				col.put(chars[n], new Pair(error));
				// Manejar el caso donde la cadena está vacía
				// System.out.println("Funcion: " + null + ", Error: " + error);
			}
			n++;
		}
		return col;
	}

	// public void imprimirMapa() throws IOException {
	// System.out.println(mt.get(7).get('_').getEstado());
	// System.out.println(mt.get(7).get('_').getAccion());

	// }

	// Método principal que me devuelve el token generado
	public Token getToken() throws IOException {
		estado = 0;
		// Nos servirá para detectar EOF
		int c = 0;
		// Si no lee EOF, hacemos un casting de char a c
		char car;
		Object accion;
		
		
		StringBuilder lexema = new StringBuilder();
		
		String auxLexema;
		int valor = 0;
		
		Token token = null;
		
		
		while (c != -1) {
			if (estado == 0 && lexema.isEmpty() && valor == 0) {
				c = leer();
			}

			if (c != -1) {
				// Si no hemos llegado al final, convertimos el dato leido de c y lo convertimos
				// en char car
				car = (char) c;
				 System.out.print(car + "\n");
			} else {
				break;
			}

			if (car == ' ') {
				c = leer();
				continue;

			}

			if (car == '\r') {
				c = leer();
				if (c == '\n') {
					posicionDeLinea++;
					continue;
				}
			}

			accion = accion(estado, identificar(car));
			if (accion == null) {
				genError(105,car, posicionDeLinea);
				estado = 0;
				continue;
			}
			if (accion instanceof Integer) {
				genError((int) accion,car, posicionDeLinea);
				continue;
			}
			// System.out.println(accion);
			estado = estado(estado, identificar(car));
			// System.out.println(estado);
			// System.out.print(car);

			if (estado == -1) {
				genError(105,car, valor);
			} else {
				switch ((char) accion) {
					case 'A':
						c=leer();
						break;
					case 'B':
						
						break;
					case 'C':
						break;
					case 'D':
						break;
					case 'E':
						estado = 0;
						genToken(16, "");
						break;
					case 'F':
						estado = 0;
						genToken(17, "");
						break;
					case 'G':
						estado = 0;
						genToken(18, "");
						break;
					case 'H':
						break;
					case 'I':
						break;
					case 'J':
						break;
					case 'K':
						break;
					case 'L':
						break;
					case 'M':
						break;
					case 'N':
						break;
					case 'O':
						break;
					case 'P':
						break;
					case 'Q':
						break;
					case 'R':
						break;
					case 'S':
						break;
					case 'T':
						break;

				}
			}

		}

		return token;
	}

	// Devuelve a si es letra, y devuelve b si es numero......
	private char identificar(char c) {
		switch (c) {
			case '"':
				return '"';
			case '+':
				return '+';
			case '=':
				return '=';
			case '&':
				return '&';
			case '|':
				return '|';
			case '(':
				return '(';
			case ')':
				return ')';
			case ',':
				return ',';
			case ';':
				return ';';
			case '{':
				return '{';
			case '}':
				return '}';
			case '_':
				return '_';
			default:
				if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z')) {
					return 'a';
				} else if (Character.isDigit(c)) {
					return 'b';
				} else {
					return 'z';
				}
		}
	}

	// Método para ver si palabra es una palabra reservada
	private boolean esPalabraReservada(String palabra) {
		return palabrasReservadas.contains(palabra);
	}

	// Genera e imprime en la salida de err el error lexico detectado
	private void genError(int codError, char car , int linea) {
		Error error;
		switch (codError) {
			case 100 -> {
				error = new Error("No se puede empezar con el caracter :",car, linea + 1);
				System.err.println(error);
			}
			case 101 -> {
				error = new Error("No se puede empezar con el caracter :" , car , linea + 1);
				System.err.println(error);
				break;
			}
			case 102 -> {
				error = new Error("se esperaba caracter :", car , linea + 1);
				System.err.println(error);
				break;
			}
			case 103 -> {
				error = new Error("se esperaba caracter :", car , linea + 1);
				System.err.println(error);
				break;
			}
			case 104 -> {
				error = new Error("se esperaba caracter :",  car ,linea + 1);
				System.err.println(error);
				break;
			}
			case 105 -> {
				error = new Error("Se ha leido un caracter erroneo",  car ,linea + 1);
				System.err.println(error);
				break;
			}
		}
	}

	// Nos devuelve el valor de estado en la matriz
	public int estado(int estado, char c) {
		if (mt.get(estado).get(c) == null)
			return -1;
		return mt.get(estado).get(c).getEstado();
	}

	// Nos devuelve el valor de accion en la matriz
	public Object accion(int estado, char c) {
		Object dato;

		if (mt.get(estado).get(c) == null)
			return null;

		dato = mt.get(estado).get(c).getAccion();

		if (dato == null)
			return 1;
		if (dato instanceof Character)
			return (char) dato;
		else
			return (int) dato;
	}

	// TODO Crea un token con la correspondiente categoria lexica y lo escribe en el
	// fichero de tokens
	private Token genToken(int categoriaLexica, String cadena) {
		Token token = null;

		// Si es una categoriaLexica que no necesita atributos
		if(categoriaLexica == 1 || categoriaLexica == 2 || categoriaLexica == 3){
		token = new Token(categoriaLexica, cadena);
		System.out.println((posicionDeLinea + 1) + ":" + token);
		}
		else{
			token = new Token(categoriaLexica, "_");
		System.out.println((posicionDeLinea + 1) + ":" + token);
		}
		estado = 0; // Reseteamos el estado para la siguiente palabra
			return token;

	}

	// Leemos de la linea linea el caracter de la posicion posCaracter
	private int leer() throws IOException {
		int c = br.read();
		return c;
	}
}