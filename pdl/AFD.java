package pdl;

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
class Pair {
    private Integer estado;    // El estado puede ser null en caso de error
    private Object accion;     // La acción puede ser un Character o un Integer

    // Constructor para estado y acción (Character)
    Pair(Integer estado, Character accion) {
        this.estado = estado;
        this.accion = accion;
    }

    // Constructor para error (null estado, Integer acción)
    Pair(Integer error) {
        this.estado = null;    // Estado es null en caso de error
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
//CLASE AFD ---------------------------------------------------------------
class AFD{
	//Pasamos estado->pasamos  caracter->elegimos Estado o Accion

	ArrayList<String> palabrasReservadas; 
	ArrayList<Integer> estadosFinales;
	BufferedReader br;
	
	
	ArrayList<Map<Character,Pair>> mt ;
	//Variable que guarda el estado actual
	int estado;
	//Variable que nos dice la linea actual
	int posicionDeLinea;
	Character [] chars= {'a','b','"','+','=','&','|','(',')',',',';','{','}','/','*','_'};
	//Constructor de AFD que inicializa el set y el array de las palabras reservadas y recibe las lineas del fichero fuente
	//Constructor de AFD que inicializa el set y el array de las palabras reservadas y recibe las lineas del fichero fuente
	public AFD(BufferedReader br) throws IOException {
		this.estado = 0;
		this.posicionDeLinea = 0;
		
		this.mt = new ArrayList<>();
		this.matriz();
		

	
		this.palabrasReservadas = new ArrayList<String>(Arrays.asList("for","var","int","boolean","string","void","output","input","if","else","then","do","while","function","return"));
		//        this.estadosFinales = new ArrayList<Integer>(Arrays.asList(1,10,1));
		this.br = br;
	}

	public void matriz() throws IOException {
		try {
			FileReader fr = new FileReader("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\Matriz.txt");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String linea = null;
		
		while ((linea = br.readLine()) != null) {
			Map<Character,Pair> colE = new HashMap<Character,Pair>();
			Map<Character,Pair> colA= leerCSV(linea,colE);
			mt.add(colA);
		}
	}


	public Map<Character, Pair> leerCSV(String linea, Map<Character, Pair> col) {
	    // Dividir la línea usando ';' como delimitador
	    String[] arraylinea = linea.split(";");

	    int n = 0;

	    // Ajustar el bucle para evitar índices fuera de rango y cadenas vacías
	    for (int i = 1; i < arraylinea.length; i += 2) {
	        // Verificar si las celdas contienen datos
	        if (!arraylinea[i].isEmpty() && !arraylinea[i - 1].isEmpty()) {
	            char actual = arraylinea[i].charAt(0);  // Obtener el carácter
	            int estado = Integer.parseInt(arraylinea[i - 1]);  // Convertir el estado a entero
	            
	            // Añadir al mapa
	            col.put(chars[n], new Pair(estado, actual));
	            
	            
	            // Imprimir para seguimiento
	            System.out.println("Funcion: " + actual + ", Estado : " + estado);
	        } else {
	        	  // Obtener el carácter
	            int error = Integer.parseInt(arraylinea[i ]);  // Convertir el estado a entero
	            
	        	col.put(chars[n], new Pair(error));
	            // Manejar el caso donde la cadena está vacía
	        	System.out.println("Funcion: " + null + ", Error: " + error);
	        }
	        n++;
	    }
	    
	    return col;
	}


		// public void imprimirMapa() throws IOException {
		// 	this.matriz();
			
			
		// 	System.out.println(mt.get(3).get('b').getAccion());
		// 	System.out.println(mt.get(3).get('b').getEstado());
			
			
		// }
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
			if(estado == 0 && lexema.isEmpty() && valor == 0){
				c = leer();
			}

			if(c!=-1) {
				//Si no hemos llegado al final, convertimos el dato leido de c y lo convertimos en char car
				car = (char)c;
				//System.out.print(car + "\n");
			}
			else{
				break;
			}				

			if(car== ' ') {
				c=leer();
				//System.out.print("hay un espacio");
				continue;

			}

			if(car  == '\r') {

				//System.out.println("se ha leido \r");
				if(c == '\n') {
					//System.out.println("se ha leido \n");
					posicionDeLinea++;

					//                                        c = leer();

				}
			}



			accion = accion(estado,identificar(car));
			if(){
				continue;
			}
			//System.out.println(accion);
			estado = estado(estado,identificar(car));
			//System.out.println(estado);
			//										System.out.print(car); 
			if(estado == -1 ) {
				genError(105, valor);
			}
			else {
				switch(accion) {
				case 'A': //
					lexema.append(car);
					c = leer();
					break;

				case 'B':
					auxLexema = lexema.toString();

					auxLexema.trim();
					//						System.out.println(auxLexema);
					if(esPalabraReservada(auxLexema)) {
						genToken(20,auxLexema);
						estado = 0;
						lexema.delete(0, auxLexema.length());

					}
					else {
						palabrasReservadas.add(auxLexema);
						genToken(21,String.valueOf(palabrasReservadas.indexOf(auxLexema)));
						estado=0;
						lexema.delete(0, auxLexema.length());
					}
					break;
				case 'C':
					if (identificar(car)=='b'){
						lexema.append(car);
						c = leer();
					}
					break;

				case 'D':
					if(identificar(car)=='a' ||identificar(car)=='b'|| identificar(car)=='_'){
						lexema.append(car); 
						c = leer();
					}
					break;

				case 'E':
					if(!(identificar(car)=='a' ||identificar(car)=='b'|| identificar(car)=='_')){
						auxLexema = lexema.toString();
						palabrasReservadas.add(auxLexema);
						genToken(21,String.valueOf(palabrasReservadas.indexOf(auxLexema)));
						estado=0;
					}
					break;

				case 'F':
					if (identificar(car)=='('){
						genToken(16, "");
						estado=0;
					}
					break;

				case 'G':
					if (identificar(car)==')'){
						genToken(17, "");
						estado=0;
					}
					break;

				case'H': //leer digito

					valor=(car-48);
					//System.out.print("ESTOYS EN H: "+ car + " el valor es: "+ valor + "\n");
					c=leer();
					break;

				case 'I':
					if(identificar(car)=='b'){
						valor=valor*10+(c-48); 
						//System.out.print("ESTOYS EN I: "+ car + " el valor es: "+  valor+ "\n");
						c = leer();
					}
					break;
				case 'J':
					if (!(identificar(car)=='b')){
						System.out.print(valor);
						genToken(2, String.valueOf(valor));

						valor=0;
						estado=0;
					}
					break;

				case 'K':
					genToken(18, "");
					estado=0;
					break;

				case 'L':
					genToken(19, "");
					estado=0;
					break;

				case 'M':
					lexema.append('"');
					c=leer();
					break;

				case 'N':
					if(car!='"'){
						lexema.append(car);
						c=leer();
						break;
					}

				}
			}

		}

		return token;
	}

	//Devuelve a si es letra, y devuelve b si es numero......
	//Devuelve el caracter c en caso contrario
	private char identificar(char c) {
		switch (c) {
		case '"': return '"'; 
		case '+': return '+';
		case '=': return '=';
		case '&': return '&';
		case '|': return '|';
		case '(': return '(';
		case ')': return ')';
		case ',': return ',';
		case ';': return ';';
		case '{': return '{';
		case '}': return '}';
		case '_': return '_';
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
	//Método para ver si palabra es una palabra reservada
	private boolean esPalabraReservada(String palabra) {
		return palabrasReservadas.contains(palabra);
	}

	
	//Genera e imprime en la salida de err el error lexico detectado
	private void genError(int codError, int linea) {
		Error error;
		switch(codError) {
		case 100 -> {
                    error = new Error("No se puede empezar con el caracter '*'", linea);
                    System.err.println(error);
                }
			case 101 -> {
                            error = new Error("No se puede empezar con el caracter '_'", linea);
                            System.err.println(error);
                }
			case 102 -> {
                            error = new Error("se esperaba caracter '&'", linea);
                            System.err.println(error);
                }
			case 103 -> {
                            error = new Error("se esperaba caracter '='", linea);
                            System.err.println(error);
                }
			case 104 -> {
                            error = new Error("se esperaba caracter '*'", linea);
                            System.err.println(error);
                }
		}
            	} 

	//Nos devuelve el valor de estado en la matriz 
	public int estado(int estado,char c) {
		if(mt.get(estado).get(c) == null)
			return -1;
		return mt.get(estado).get(c).getEstado();
	}

	//Nos devuelve el valor de accion en la matriz 
	public Object accion(int estado,char c) {
		Object dato = mt.get(estado).get(c);
		if(dato==null)
			return 1;
		if(dato instanceof Character)
			return (char)dato;
		else 
			return (int)dato;
		}
	}

	//TODO Crea un token con la correspondiente categoria lexica y lo escribe en el fichero de tokens
	private Token genToken(int categoriaLexica, String cadena) {
		Token token = null;
		if(categoriaLexica >0) {

			//Si es una categoriaLexica que no necesita atributos
			token = new Token(categoriaLexica,cadena);
			//System.out.println((posicionDeLinea + 1) +  ":" + token);
		}
		estado =0;
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