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
		}

	}
}
