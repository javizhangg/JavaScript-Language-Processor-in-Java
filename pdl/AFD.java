package pdl;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// CLASE AFD ---------------------------------------------------------------
class AFD {
	// Pasamos estado->pasamos caracter->elegimos Estado o Accion
	private Map<String, Integer> palabrasReservadas;
	private BufferedReader br;
	public FileWriter fwTokens;
	public FileWriter fwTS;
	private Matriz mt;
	// Variable que guarda el estado actual
	public int estado;
	// Variable que nos dice la linea actual
	public int posicionDeLinea;

	public TablasDeSimbolos gestorTablas;
	public TS posEnTablaSimbolo;
	public TS tablaGlobal;
	public AnalizadorSemantico As;

	// Esta variable nos sirve para no perder el caracter que hemos leido, en el
	// caso de generar token
	public boolean leido = false;
	public boolean esSimbolo = true;
	// Nos servirá para detectar EOF
	public boolean ultimaint = false;
	public int c = 0;

	// Constructor de AFD que inicializa el set y el array de las palabras
	// reservadas y recibe las lineas del fichero fuente
	public AFD(BufferedReader br, FileWriter fwTokens,FileWriter fwTS) throws IOException {
		this.estado = 0;
		this.posicionDeLinea = 1;

		this.mt = new Matriz(System.getProperty("user.dir")  + "/src/pdl/Matriz.txt");
		this.fwTokens = fwTokens;
		this.fwTS = fwTS;

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

		this.gestorTablas = new TablasDeSimbolos(fwTS);
		this.As = new AnalizadorSemantico(gestorTablas);
		this.posEnTablaSimbolo = new TS();



		this.br = br;
	}

	//Busca ese lexema en la tabla de símbolos activa; si no lo encuentra 
	// y la tabla activa no es la global, sigue buscando
	public boolean BuscaTS(String lexema) {
		boolean encontrado = false;
		if(gestorTablas.getGestorTS().containsKey(1)) { 
			encontrado = gestorTablas.getGestorTS().get(1).tablaSimbolo.containsKey(lexema);
		}
		else if(!encontrado)
		{
			encontrado =  gestorTablas.getGestorTS().get(0).tablaSimbolo.containsKey(lexema);
		}
		return encontrado;
	}
	//Busca ese lexema en la tabla de símbolos activa; si no lo encuentra 
	// y la tabla activa no es la global, sigue buscando
	public boolean BuscaTSGlobal(String lexema) {
		return gestorTablas.getGestorTS().get(0).tablaSimbolo.containsKey(lexema);
	}



	// Método principal que me devuelve el token generado
	public Token getToken(boolean valorZonaDeclarada) throws IOException {
		char car;
		Object accion;
		StringBuilder lexema = new StringBuilder();
		String auxLexema;
		double valor = 0;
		Token token = null;
		Tipo tipo = new Tipo();
		 boolean errorReportado = false;
		// Salimos del bucle después de procesar un token
		while (true) {
			if (estado == 0 && !leido) 
				c = leer();

			car = (char) c;
			//System.out.print(car);
			accion = accion(estado, identificar(c));

	        // Acción nula (carácter no reconocido)
	        if (accion == null) {
	            if (!errorReportado) {
	            	token = genToken(100, "error","");
	                new Error(106, posicionDeLinea, String.valueOf(c)).getError();
	                errorReportado = true; // Marcar como reportado
	            }
	            c = leer(); // Avanzar para evitar bucle
	            estado = 0; // Reiniciar estado
	            lexema.setLength(0); // Limpiar lexema
	            continue;
	        }

	        // Acción de error (Integer)
	        if (accion instanceof Integer) {
	            if (!errorReportado) {
	            	token = genToken(100, "error","");
	                new Error((int) accion, posicionDeLinea).getError();
	                errorReportado = true; // Marcar como reportado
	            }
	            c = leer(); // Avanzar para evitar bucle
	            estado = 0; // Reiniciar estado
	            lexema.setLength(0); // Limpiar lexema
	            continue;
	        }
			 estado = estado(estado, identificar(c));
		        errorReportado = false; // Reiniciar bandera si avanzamos correctamente
			if(estado==3) 
				esSimbolo=false;
			if (estado == -1) {
				 if (!errorReportado) {
					 token = genToken(100, "error","");
		                new Error(106, posicionDeLinea).getError();
		                errorReportado = true; // Marcar como reportado
		            }
		            c = leer(); // Avanzar para evitar bucle
		            estado = 0; // Reiniciar estado
		            lexema.setLength(0); // Limpiar lexema
		            continue;
			} else {
				switch ((char) accion) {
				case 'A':
					if (car == '\r') {
						c = leer();
						if (c == '\n') 
							posicionDeLinea++;
					} else {
						c = leer();
						if(c != ' ')
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
						token = genToken(palabrasReservadas.get(auxLexema), "",auxLexema);
						//Zona declarada = true
					} else if (valorZonaDeclarada) {
						if(!BuscaTS(auxLexema)) {
							posEnTablaSimbolo = As.getTablaActual();
							posEnTablaSimbolo.InsertarTS(auxLexema);
							token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
						}else {
							posEnTablaSimbolo = As.getTablaActual();
							token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
						}
						//Zona declarada = false
					}else {
						if(!BuscaTS(auxLexema)) {
							posEnTablaSimbolo = As.getTablaGlobal();
							posEnTablaSimbolo.InsertarTS(auxLexema);
						}
						else{
							posEnTablaSimbolo = As.getTablaActual();
						}
						token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
					}
					lexema.delete(0, lexema.length());
					leido = true;
					return token;
				case 'D':
					auxLexema = lexema.toString();
					if (valorZonaDeclarada) {
						if(!BuscaTS(auxLexema)) {
							posEnTablaSimbolo = As.getTablaActual();
							posEnTablaSimbolo.InsertarTS(auxLexema);
							token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
						}else {
							//Error de identificador ya declarado
							posEnTablaSimbolo = As.getTablaActual();
							token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema); 
						}
						//Zona declarada = false
					}else {
						if(!BuscaTS(auxLexema)) {
							posEnTablaSimbolo = As.getTablaGlobal();
							posEnTablaSimbolo.InsertarTS(auxLexema);
						}
						else{
							posEnTablaSimbolo = As.getTablaActual();
						}
						token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
					}
					lexema.delete(0, lexema.length());
					leido = true;
					return token;
				case 'E':
					token = genToken(16, "","(");
					leido = false;
					return token;
				case 'F':
					token = genToken(17, "",")");
					leido = false;
					return token;
				case 'G':
					valor = valor * 10 + (c - 48);
					c = leer();
					leido = false;
					break;
				case 'H':
					if (valor <= 32767) {
						token = genToken(2, String.valueOf(valor),"entero");
						valor = 0;
					} else {
						new Error(107, posicionDeLinea, String.valueOf(valor)).getError();
						token = genToken(100, "error","");
						valor = 0;
						lexema.delete(0, lexema.length());
						estado = 0;
					}
					leido = true;
					return token;
				case 'I':
					token = genToken(18, "",",");
					leido = false;
					return token;
				case 'J':
					token = genToken(19, "",";");
					leido = false;
					return token;
				case 'K':
					lexema.append(car);
					auxLexema = lexema.toString();
					if (auxLexema.length() <= 64) {
						token = genToken(3, auxLexema,auxLexema);
					} else {
						new Error(108, posicionDeLinea,auxLexema).getError();
						token = genToken(100, "error","");
						estado = 0;
					}
					lexema.delete(0, lexema.length());
					valor = 0;
					leido = false;
					esSimbolo=true;
					return token;
				case 'L':
					token = genToken(5, "","==");
					leido = false;
					return token;
				case 'M':
					token = genToken(7, "","=");
					leido = true;
					return token;
				case 'N':
					token = genToken(20, "","{");
					leido = false;
					return token;
				case 'O':
					token = genToken(21, "","}");
					leido = false;
					return token;
				case 'P':
					token = genToken(6, "","&&");
					leido = false;
					return token;
				case 'Q':
					token = genToken(8, "","|=");
					leido = false;
					return token;
				case 'R':
					token = genToken(4, "","+");
					leido = false;
					return token;
				case 'S':
					token = genToken(29,"","eof");
					ultimaint=true;

					return token;
				}
			}
			if(c==-1) {
				if(!ultimaint) 
					continue;
				if(valor != 0 && valor <= 32767) {
					token = genToken(2,String.valueOf(valor),"entero");
					valor = 0;
					return token;
				}else if(valor!=0) {
					token = genToken(100, "error","");
					new Error(107, posicionDeLinea, String.valueOf(valor)).getError();
				}
				auxLexema = lexema.toString();
				if(!esSimbolo) {
					token = genToken(100, "error","");
					new Error(106, posicionDeLinea).getError();
				}else if(auxLexema.length()>0 ) {
					if (esPalabraReservada(auxLexema)) {
						token = genToken(palabrasReservadas.get(auxLexema), "",auxLexema);
						//Zona declarada = true
					} else if (valorZonaDeclarada) {
						if(!BuscaTS(auxLexema)) {
							posEnTablaSimbolo = As.getTablaActual();
							posEnTablaSimbolo.InsertarTS(auxLexema);
							token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
						}else {
							//Error de identificador ya declarado
							token = genToken(100, "error","");
							new Error(305, posicionDeLinea).getError();
						}
						//Zona declarada = false
					}else {
						if(!BuscaTS(auxLexema)) {
							posEnTablaSimbolo = As.getTablaGlobal();
							posEnTablaSimbolo.InsertarTS(auxLexema);
						}
						else{
							posEnTablaSimbolo = As.getTablaActual();
						}
						token = genToken(1, posEnTablaSimbolo.tablaSimbolo.get(auxLexema).getLexema(),auxLexema);
					}
				}
				lexema.delete(0, lexema.length());
				return token;
			}
		}
	}

	// Devuelve a si es letra, y devuelve b si es numero....
	private char identificar(int c) {
		switch (c) {
		case -1:
			return 'c';
		case 39:
			return '\'';
		case 43:
			return '+';
		case 61:
			return '=';
		case 38:
			return '&';
		case 124:
			return '|';
		case 40:
			return '(';
		case 41:
			return ')';
		case 47:
			return '/';
		case 44:
			return ',';
		case 59:
			return ';';
		case 123:
			return '{';
		case 125:
			return '}';
		case 95:
			return '_';
		case 42:
			return '*';
		case 32:
		case 9:
			return ' ';
		case 13:
		case 10: //Manejamos de esta manera los saltos de linea
			return '\r';
		default:
			if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
				return 'a';
			} else if (c >= 48 && c <= 57) {
				return 'b';
			} else 
				return 'z';
		}
	}



	// Método para ver si palabra es una palabra reservada
	private boolean esPalabraReservada(String palabra) {
		return palabrasReservadas.containsKey(palabra);
	}

	// Nos devuelve el valor de estado en la matriz
	public int estado(int estado, char c) {
		if (mt.getPar(estado,c) == null) 
			return -1;
		return mt.getEstado(estado, c);
	}

	// Nos devuelve el valor de accion en la matriz
	public Object accion(int estado, char c) {
		Object dato;

		if (mt.getPar(estado,c) == null) {
			return null;
		}

		dato = mt.getAccion(estado, c);

		if (dato == null)
			return 1;
		if (dato instanceof Character)
			return (char) dato;
		else
			return (int) dato;
	}

	// Crea un token con la correspondiente categoria lexica y lo escribe en el
	// fichero de tokens
	private Token genToken(int categoriaLexica, String cadena, String comentario) throws IOException {
		Token token = new Token(categoriaLexica, cadena);
		switch(categoriaLexica) {
		case 1:
			fwTokens.write(token + " //es el identificador: " + comentario + "\n");
			break;
		case 2:
			fwTokens.write(token + " //es el numero: " + comentario + "\n");
			break;
		case 3:
			fwTokens.write(token + " //es la cadena: " + comentario + "\n");
			break;
		case 4:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 5:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 6:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 7:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 8:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 16:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 17:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 18:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 19:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 20:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 21:
			fwTokens.write(token + " //tipo: " + comentario + "\n");
			break;
		case 100:
			fwTokens.write(token + " //Es un token de error que nos sirve para seguir compilando " + comentario + "\n");
			break;
		default:
			fwTokens.write(token + " //Palabra reservada: " + comentario + "\n");
			break;
		}
		this.estado = 0; // Reseteamos el estado para la siguiente palabra
		return token;
	}

	// Leemos de la linea el caracter de la posicion posCaracter
	private int leer() throws IOException {
		return br.read();
	}
}