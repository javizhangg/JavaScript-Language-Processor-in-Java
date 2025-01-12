package pdl;

class Error {
	private int codError;
	private int linea;
	private String mensaje;
	StringBuilder st = new StringBuilder();
	String error;
	
	// Constructor de un error lexico
	public Error(int codError, int linea, String mensaje) {
		this.codError = codError;
		this.linea = linea;
		this.mensaje = mensaje;
	}
	public Error(int codError, int linea) {
		this.codError = codError;
		this.linea = linea;
	}
	
	public void getError() {
		switch (codError) {
		//ERRORES LÉXICOS
		case 100 -> {
			error = st.append("- Error Léxico: No se puede empezar leyendo un *. En la linea: ").append(linea).toString();
			System.out.println(error);
		}
		case 101 -> {
			error = st.append("- Error Léxico: No se puede empezar leyendo un  _. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 102 -> {
			error = st.append("- Error Léxico: Se esperaba otra '&'. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 103 -> {
			error = st.append("- Error Léxico: Se esperaba un '='. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 104 -> {
			error = st.append("- Error Léxico: Se esperaba un '*' para abrir un comentario. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 105 -> {
			error = st.append("- Error Léxico: Cadena no cerrada. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 106 -> {
			error = st.append("- Error Léxico: Se ha leido un caracter erroneo: ").append(mensaje).append(". En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 107 -> {
			error = st.append("- Error Léxico: El numero " + mensaje + ". Supera el maximo entero valido (32767). En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 108 -> {
			error = st.append("- Error Léxico: La cadena " + mensaje + ". Supera el maximo de 64 caracteres. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		//ERRORES SINTÁCTICOS
		case 200 -> {
			error = st.append("- Error Sintáctico: Error en el sistema de produccion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;		
		}
		case 201 -> {
			error = st.append("- Error Sintáctico: Error al realizar emparejado. Probablemente es debido a errores anteriores.").append(" En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 202 -> {
			error = st.append("- Error Sintáctico: Se esperaba empezar una secuencia o leer fín de fichero. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 203 -> {
			error = st.append("- Error Sintáctico: Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 208 -> {
			error = st.append("- Error Sintáctico: Falta una sentencia simple o falta el return. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 209 -> {
			error = st.append("- Error Sintáctico: Se esperaba los siguientes simbolos: =, |=, (. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 210 -> {
			error = st.append("- Error Sintáctico: Error en la expresion del return. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 212 -> {
			error = st.append("- Error Sintáctico: Falta cerrar el parentesis. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 213 -> {
			error = st.append("- Error Sintáctico: Se esperaba el tipo de la variable o el tipo de retorno de funcion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 214 -> {
			error = st.append("- Error Sintáctico: Se esperaba la especificación de los parámetros si los hubiera, en otro caso, void. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 215 -> {
			error = st.append("- Error Sintáctico: Se esperaba nombre de variable o las palabras 'var', 'output', 'input', 'if', 'do' o 'return'. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 216 -> {
			error = st.append("- Error Sintáctico: Falta una sentencia simple o falta el return. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 217 -> {
			error = st.append("- Error Sintáctico: Se esperaba el tipo de retorno de la funcion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 218 -> {
			error = st.append("- Error Sintáctico: Error al definir los parametros de la funcion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 219 -> {
			error = st.append("- Error Sintáctico: No se permiten definir funciones anidadas. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		//ERRORES SEMÁNTICOS
		case 307 -> {
		    error = st.append("- Error Semántico: No se puede utilizar output con una variable booleana. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 309 -> {
		    error = st.append("- Error Semántico: Se esperaba que la expresion despues del '=' fuera del mismo tipo que la variable, pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 310 -> {
		    error = st.append("- Error Semántico: Se esperaba que la expresion despues del '|=' fuera del mismo tipo que la variable, pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 313 -> {
		    error = st.append("- Error Semántico: La expresion del if se esperaba que fuera de tipo 'boolean', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 314 -> {
		    error = st.append("- Error Semántico: Se esperaba que la expresión adentro del while fuera de tipo 'boolean', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 315 -> {
		    error = st.append("- Error Semántico: El identificador ya ha sido declarado previamente. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 316 -> {
		    error = st.append("- Error Semántico: El tipo de retorno no coincide con el tipo declarado en la definición de la función. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 317 -> {
		    error = st.append("- Error Semántico: Falta una sentencia 'return' en la función: ").append(mensaje).append(". En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 318 -> {
			error = st.append("- Error Semántico: Se está intentando llamar a una función no declarada: ").append(mensaje).append(". En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 319 -> {
			error = st.append("- Error Semántico: El numero de parametros al llamar la funcion no coincide con el de la funcion: ").append(mensaje).append(". En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 320 -> {
			error = st.append("- Error Semántico: " +mensaje).append("En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 321 -> {
			error = st.append("- Error Semántico: El identificador: " + mensaje  + ", no ha sido encontrado. En la línea: " + linea).toString();
		    System.out.println(error);
		    break;
		}
		case 322 -> {
			error = st.append("- Error Semántico: La funcion: " + mensaje  + ", no ha sido encontrada. En la línea: " + linea).toString();
		    System.out.println(error);
		    break;
		}
		case 323 -> {
			error = st.append("- Error Semántico: La funcion: " + mensaje  + ", ha sido declarado ya anteriormente. En la línea: " + linea).toString();
		    System.out.println(error);
		    break;
		}
		}
	}
}
