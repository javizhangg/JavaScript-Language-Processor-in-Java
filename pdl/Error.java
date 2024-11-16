package pdl;

class Error {
	private int codError;
	private int linea;
	StringBuilder st = new StringBuilder();
	String error;
	// Constructor de un error lexico
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
			error = st.append("Error Lexico: No se puede empezar leyendo un *. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
		}
		case 101 -> {
			error = st.append("Error Lexico: No se puede empezar leyendo un  _. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 102 -> {
			error = st.append("Error Lexico: En este estado no se puede leer otro caracter distinto a &. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 103 -> {
			error = st.append("Error Lexico: En este estado no se puede leer otro caracter distinto a =. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 104 -> {
			error = st.append("Error Lexico: En este estado no se puede leer otro caracter distinto a *. Linea: ").append(linea).append("\n").toString();
		
			System.out.println(error);
			break;
		}
		case 105 -> {
			error = st.append("Error Lexico: Cadena no cerrada. Linea: ").append(linea).append("\n").toString();

			System.out.println(error);
			break;
		}
		case 106 -> {
			error = st.append("Error Lexico: Se ha leido un caracter erroneo. Linea:").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 107 -> {
			error = st.append("Error Lexico: Supera el maximo entero valido. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 108 -> {
			error = st.append("Error Lexico: Supera el maximo de 64 caracteres. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 109 ->{
			error = st.append("Error Lexico: Comentario no cerrado. Linea: ").append(linea).append("\n").toString();
		}
		case 200 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;		
		}
		case 201 -> {
			error = st.append("Error Sintactico: Error al realizar emparejado. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 202 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (P). Se esperaba los siguientes tokens: <if, > <var, > <do, > <output, > <input, > <return, > <function, > <while, > <eof, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 203 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (E'). Se esperaba los siguientes tokens: <&&, > <), > <,, > <;, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 204 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (R'). Se esperaba los siguientes tokens: <==, > <&&, > <), > <,, > <;, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 205 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (U'). Se esperaba los siguientes tokens: <+, > <==, > <&&, > <), > <,, > <;, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 206 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (V). Se esperaba los siguientes tokens: <identificador, > <numero, > <cadena, > <(, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 207 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (V'). Se esperaba los siguientes tokens: <+, > <==, > <&&, > <(, > <), > <,, > <;, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 208 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (S). Se esperaba los siguientes tokens: <identificador, > <output, > <input, > <return, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 209 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (S'). Se esperaba los siguientes tokens: <=, > <|=, > <(, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 210 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (X). Se esperaba los siguientes tokens: <identificador, > <numero, > <cadena, > <(, > <;, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 211 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (L). Se esperaba los siguientes tokens: <identificador, > <numero, > <cadena, > <(, > <), >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 212 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (Q). Se esperaba los siguientes tokens: <), > <,, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 213 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (T). Se esperaba los siguientes tokens: <int, > <boolean, > <string, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 214 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (A). Se esperaba los siguientes tokens: <int, > <boolean, > <string, > <void, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 215 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (B). Se esperaba los siguientes tokens: <identificador, > <var, > <output, > <input, > <if, > <do, > <return, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 216 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (C). Se esperaba los siguientes tokens: <identificador, > <var, > <output, > <input, > <), > <} , > <if, > <do, > <return, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 217 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (H). Se esperaba los siguientes tokens: <int, > <boolean, > <string, > <void, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 218 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion (K). Se esperaba los siguientes tokens: <), > <,, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		
	}

}
}
