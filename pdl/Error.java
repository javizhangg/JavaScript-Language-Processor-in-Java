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
			error = st.append("Error Sintactico: Error en el sistema de produccion. Se esperaba los siguientes tokens: <22, > <9, > <25, > <14, > <15, > <28, > <27, > <26, > <29, >. En la linea: ").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		case 203 -> {
			error = st.append("Error Sintactico: Error en el sistema de produccion. Se esperaba los siguientes tokens: <6, > <17, > <18, > <19, >").append(linea).append("\n") .toString();
			System.out.println(error);
			break;
		}
		}
	}

}
