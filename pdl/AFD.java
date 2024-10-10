package pdl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

}

// CLASE AFD ---------------------------------------------------------------
class AFD {
	// Pasamos estado->pasamos caracter->elegimos Estado o Accion
	private Map<String, Integer> palabrasReservadas;
	private BufferedReader br;
	private FileWriter fw;
	private ArrayList<Map<Character, Pair>> mt;
	// Variable que guarda el estado actual
	private int estado;
	// Variable que nos dice la linea actual
	private int posicionDeLinea;
	private Character[] chars;
	private Map<String,Integer> tablaSimbolo;
	
	private int contsimb;

	// Constructor de AFD que inicializa el set y el array de las palabras
	// reservadas y recibe las lineas del fichero fuente
	public AFD(BufferedReader br, FileWriter fw) throws IOException {
		this.estado = 0;
		this.posicionDeLinea = 0;
		this.mt = new ArrayList<>();
		this.fw = fw;
		// Inicializar la matriz de transiciones
		chars = new Character[] { 'a', 'b', '"', '+', '=', '&', '|', '(', ')', ',', ';', '{', '}', '/', '*', '_', ' ',
				'\r' };
		this.matriz();

		this.palabrasReservadas = new HashMap<>();
		palabrasReservadas.put("var", 9);
		palabrasReservadas.put("int", 10);
		palabrasReservadas.put("boolean", 11);
		palabrasReservadas.put("string", 12);
		palabrasReservadas.put("void", 13);
		palabrasReservadas.put("output", 14);
		palabrasReservadas.put("input", 15);
		palabrasReservadas.put("if", 22);
		palabrasReservadas.put("else", 23);
		palabrasReservadas.put("then", 24);
		palabrasReservadas.put("do", 25);
		palabrasReservadas.put("while", 26);
		palabrasReservadas.put("function", 27);
		palabrasReservadas.put("return", 28);
		
		this.contsimb=0;
		this.tablaSimbolo = new HashMap<>();
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
		// Ajustamos el bucle para evitar índices fuera de rango y cadenas vacías
		for (int i = 1; i < arraylinea.length; i += 2) {
			// Verificamos si las celdas contienen datos
			if (!arraylinea[i].isEmpty() && !arraylinea[i - 1].isEmpty()) {
				char actual = arraylinea[i].charAt(0); // Obtenemos el carácter
				int estado = Integer.parseInt(arraylinea[i - 1]); // Convertimos el estado a entero

				// Añadimos al mapa
				col.put(chars[n], new Pair(estado, actual));
			} else {
				// Obtener el carácter
				int error = Integer.parseInt(arraylinea[i]); // Convertir el estado a entero

				// Manejar el caso donde la cadena está vacía
				col.put(chars[n], new Pair(error));
			}
			n++;
		}
		return col;
	}

	// Método principal que me devuelve el token generado
	public Token getToken() throws IOException {
		// Nos servirá para detectar EOF
		int c = 0;
		// Si no lee EOF, hacemos un casting de char a c
		char car;
		Object accion;
		StringBuilder lexema = new StringBuilder();
		String auxLexema;
		int valor = 0;
		Token token = null;
		// Esta variable nos sirve para no perder el caracter que hemos leido, en el
		// caso de generar token
		boolean leido = false;

		while (c != -1) {
			if (estado == 0 && lexema.isEmpty() && valor == 0 && leido == false) {
				c = leer();
			}
			if(c!=-1) {
			car = (char) c;
			System.out.print(car);
			}else {
				break;
			}

			accion = accion(estado, identificar(car));
			if (accion == null) {
				genError(106, posicionDeLinea);
				valor = 0;
				lexema.delete(0, lexema.length());
				leido = false;
				estado = 0;
				continue;
			}
			if (accion instanceof Integer) {
				genError((int) accion, posicionDeLinea);
				valor = 0;
				lexema.delete(0, lexema.length());
				leido = false;
				estado = 0;
				continue;
			}
			estado = estado(estado, identificar(car));

			if (estado == -1) {
				genError(106, valor);
				valor = 0;
				lexema.delete(0, lexema.length());
				leido = false;
				estado = 0;
				continue;
			} else {
				switch ((char) accion) {
					case 'A':
						if (car == '\r') {
							c = leer();
							if (c == '\n') {
								posicionDeLinea++;
							}
						} else {
							c = leer();
							leido = true;
						}
						break;
					case 'B':
						lexema.append(car);
						c = leer();
						break;
					case 'C':
						auxLexema = lexema.toString();
						if (esPalabraReservada(auxLexema)) {
							genToken(palabrasReservadas.get(auxLexema), "");
						} else if (!tablaSimbolo.containsKey(auxLexema)) {
							InsertarTS(auxLexema);
							genToken(1, String.valueOf(tablaSimbolo.get(auxLexema)));
						}else {
							genToken(1, String.valueOf(tablaSimbolo.get(auxLexema)));
						}
						
						
						lexema.delete(0, lexema.length());
						leido = true;
						estado = 0;
						break;
					case 'D':
						auxLexema = lexema.toString();
						if (!tablaSimbolo.containsKey(auxLexema)) {
							InsertarTS(auxLexema);
						}
						genToken(1, String.valueOf(tablaSimbolo.get(auxLexema)));
						lexema.delete(0, lexema.length());
						leido = true;
						estado = 0;
						break;
					case 'E':
						genToken(16, "");
						leido = false;
						break;
					case 'F':
						genToken(17, "");
						leido = false;
						break;
					case 'G':
						valor = valor * 10 + (c - 48);
						c = leer();
						break;
					case 'H':
						if (valor <= 32767) {
							genToken(2, String.valueOf(valor));
							leido = true;
							valor = 0;
						} else {
							genError(107, posicionDeLinea);
							valor = 0;
							lexema.delete(0, lexema.length());
							leido = false;
							estado = 0;
						}
						break;
					case 'I':
						genToken(18, "");
						leido = false;
						break;
					case 'J':
						genToken(19, "");
						leido = false;
						break;
					case 'K':
						lexema.append(car);
						auxLexema = lexema.toString();
						if (auxLexema.length() <= 64) {
							genToken(3, auxLexema);
							lexema.delete(0, lexema.length());
							c = leer();
						} else {
							genError(108, posicionDeLinea);
							valor = 0;
							lexema.delete(0, lexema.length());
							leido = false;
							estado = 0;
						}
						break;
					case 'L':
						genToken(5, "");
						leido = false;
						break;

					case 'M':
						genToken(7, "");
						leido = true;
						break;
					case 'N':
						genToken(20, "");
						leido = false;
						break;
					case 'O':
						genToken(21, "");
						leido = false;
						break;
					case 'P':
						genToken(6, "");
						leido = false;
						break;
					case 'Q':
						genToken(8, "");
						leido = false;
						break;
					case 'R':
						genToken(4, "");
						leido = false;
						break;
				}
			}
		}
		return token;
	}

	// Devuelve a si es letra, y devuelve b si es numero....
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
			case '/':
				return '/';
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
			case '*':
				return '*';
			case ' ':
				return ' ';
			case '\r':
			case '\n': //Manejamos de esta manera los saltos de linea
				return '\r';
			default:
				if (Character.isLetter(c)) {
					return 'a';
				} else if (Character.isDigit(c)) {
					return 'b';
				} else {
					return 'z';
				}
		}
	}

	// Genera e imprime en la salida de err el error lexico detectado
	private void genError(int codError, int linea) {
		Error error;
		switch (codError) {
			case 100 -> {
				error = new Error("No se puede empezar con el caracter '*'", linea + 1);
				System.out.println(error);
			}
			case 101 -> {
				error = new Error("No se puede empezar con el caracter '_'", linea + 1);
				System.out.println(error);
				break;
			}
			case 102 -> {
				error = new Error("se esperaba caracter '&'", linea + 1);
				System.out.println(error);
				break;
			}
			case 103 -> {
				error = new Error("se esperaba caracter '='", linea + 1);
				System.out.println(error);
				break;
			}
			case 104 -> {
				error = new Error("se esperaba caracter '*'", linea + 1);
				System.out.println(error);
				break;
			}
			case 105 -> {
				error = new Error("Una cadena no puede tener salto de linea ", linea + 1);
				System.out.println(error);
				break;
			}
			case 106 -> {
				error = new Error("Se ha leido un caracter erroneo ", linea + 1);
				System.out.println(error);
				break;
			}
			case 107 -> {
				error = new Error("Supera el maximo entero valido ", linea + 1);
				System.out.println(error);
				break;
			}
			case 108 -> {
				error = new Error("Supera el maximo de 64 caracteres ", linea + 1);
				System.out.println(error);
				break;
			}
		}
	}

	// Método para ver si palabra es una palabra reservada
	private boolean esPalabraReservada(String palabra) {
		return palabrasReservadas.containsKey(palabra);
	}

	// Nos devuelve el valor de estado en la matriz
	public int estado(int estado, char c) {
		if (mt.get(estado).get(c) == null) {
			return -1;
		}
		return mt.get(estado).get(c).getEstado();
	}

	// Nos devuelve el valor de accion en la matriz
	public Object accion(int estado, char c) {
		Object dato;

		if (mt.get(estado).get(c) == null) {
			return null;
		}

		dato = mt.get(estado).get(c).getAccion();

		if (dato == null)
			return 1;
		if (dato instanceof Character)
			return (char) dato;
		else
			return (int) dato;
	}

	// Crea un token con la correspondiente categoria lexica y lo escribe en el
	// fichero de tokens
	private Token genToken(int categoriaLexica, String cadena) throws IOException {
		Token token = null;

		// Si es una categoriaLexica que no necesita atributos
		if (categoriaLexica == 1 || categoriaLexica == 2 || categoriaLexica == 3) {
			token = new Token(categoriaLexica, cadena);
			fw.write(token + "\n");
			// fw.write((posicionDeLinea + 1) + ":" + token + "\n");
		} else {
			token = new Token(categoriaLexica, "");
			fw.write(token + "\n");
			// fw.write((posicionDeLinea + 1) + ":" + token + "\n");
		}
		this.estado = 0; // Reseteamos el estado para la siguiente palabra
		return token;
	}
	
	private void InsertarTS(String lexema) throws IOException {
		tablaSimbolo.put(lexema, contsimb);
		contsimb++;
	}
	// Leemos de la linea linea el caracter de la posicion posCaracter
	private int leer() throws IOException {
		int c = br.read();
		return c;
	}
}