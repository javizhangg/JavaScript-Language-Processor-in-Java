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
	//
	//	public String toString() {
	//		return st.append("Se ha producido un error lexico en la linea: ").append(linea).append(" ,por el motivo: ")
	//				.append(mensaje).toString();
	//	}
	public void getError() {
		switch (codError) {
		case 100 -> {
			error = st.append("- Error Lexico: No se puede empezar leyendo un *. En la linea: ").append(linea).toString();
			System.out.println(error);
		}
		case 101 -> {
			error = st.append("- Error Lexico: No se puede empezar leyendo un  _. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 102 -> {
			error = st.append("- Error Lexico: En este estado no se puede leer otro caracter distinto a &. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 103 -> {
			error = st.append("- Error Lexico: En este estado no se puede leer otro caracter distinto a =. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 104 -> {
			error = st.append("- Error Lexico: En este estado no se puede leer otro caracter distinto a *. En la linea: ").append(linea).toString();

			System.out.println(error);
			break;
		}
		case 105 -> {
			error = st.append("- Error Lexico: Cadena no cerrada. En la linea: ").append(linea).toString();

			System.out.println(error);
			break;
		}
		case 106 -> {
			error = st.append("- Error Lexico: Se ha leido un caracter erroneo: ").append(mensaje).append(". En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 107 -> {
			error = st.append("- Error Lexico: Supera el maximo entero valido. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 108 -> {
			error = st.append("- Error Lexico: Supera el maximo de 64 caracteres. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 109 ->{
			error = st.append("- Error Lexico: Comentario no cerrado. En la linea: ").append(linea).toString();
		}
		case 200 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;		
		}
		case 201 -> {
			error = st.append("- Error Sintactico: Error al realizar emparejado. Probablemente es debido a errores anteriores.").append(" En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 202 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (P). Se esperaba empezar una secuencia o leer fín de fichero. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 203 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (E'). Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 204 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (R'). Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 205 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (U'). Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 206 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (V). Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 207 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (V'). Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 208 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (S). Falta una sentencia simple o falta el return. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 209 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (S'). Se esperaba los siguientes simbolos: =, |=, (. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 210 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (X). Error en la expresion del return. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 211 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (L). Error en la expresion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 212 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (Q). Falta cerrar el parentesis. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 213 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (T). Se esperaba el tipo de la variable o el tipo de retorno de funcion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 214 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (A). Se esperaba la especificación de los parámetros si los hubiera, en otro caso, void. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 215 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (B). Se esperaba los siguientes tokens: <identificador, > <var, > <output, > <input, > <if, > <do, > <return, >. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 216 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (C). Falta una sentencia simple o falta el return.. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 217 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (H). Se esperaba el tipo de retorno de la funcion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 218 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (K). Error al definir los parametros de la funcion. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 219 -> {
			error = st.append("- Error Sintactico: Error en el sistema de produccion (F). No se permiten definir funciones anidadas. En la linea: ").append(linea).toString();
			System.out.println(error);
			break;
		}
		case 300 -> {
		    error = st.append("- Error Semántico: En la producción E -> R E2, se esperaba que el tipo de E2 no sea 'error'. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 301 -> {
		    error = st.append("- Error Semántico: En la producción E -> && R E2, se esperaba que tanto R como E2 fueran de tipo 'boolean', pero no lo son. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 302 -> {
		    error = st.append("- Error Semántico: En la producción R -> U R2, se esperaba que los tipos de U y R2 fueran compatibles, pero no lo son. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 303 -> {
		    error = st.append("- Error Semántico: En la producción R2 -> == U R2, se esperaba que el tipo de U fuera 'int', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 304 -> {
		    error = st.append("- Error Semántico: En la producción U -> V U2, se esperaba que los tipos de U2 y V fueran compatibles, pero no lo son. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 305 -> {
		    error = st.append("- Error Semántico: En la producción U2 -> + V U2, se esperaba que V fuera de tipo 'int', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 306 -> {
		    error = st.append("- Error Semántico: En la producción S -> id S2, se esperaba que el tipo del identificador fuera compatible con el tipo de S2, pero no lo son. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 307 -> {
		    error = st.append("- Error Semántico: En la producción S -> output E ;, se esperaba que E fuera de tipo 'string', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 308 -> {
		    error = st.append("- Error Semántico: En la producción S -> input id ;, se esperaba que el identificador fuera de tipo 'string', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 309 -> {
		    error = st.append("- Error Semántico: En la producción S2 -> = E ;, se esperaba que E fuera de tipo 'boolean', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 310 -> {
		    error = st.append("- Error Semántico: En la producción S2 -> |= E ;, se esperaba que E fuera de tipo 'boolean', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 311 -> {
		    error = st.append("- Error Semántico: En la producción L -> E Q, se esperaba que el tipo de E fuera igual al tipo de Q, pero no lo son. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 312 -> {
		    error = st.append("- Error Semántico: En la producción Q -> , E Q, se esperaba que el tipo de E fuera igual al tipo de Q, pero no lo son. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 313 -> {
		    error = st.append("- Error Semántico: En la producción B -> if ( E ) S, se esperaba que E fuera de tipo 'boolean', pero no lo es. En la línea: ").append(linea).toString();
		    System.out.println(error);
		    break;
		}
		case 314 -> {
		    error = st.append("- Error Semántico: En la producción B -> do { C } while ( E ) ;, se esperaba que E fuera de tipo 'boolean', pero no lo es. En la línea: ").append(linea).toString();
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
		}

	}
}
