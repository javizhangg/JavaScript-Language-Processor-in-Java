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
			error = st.append("No se puede empezar leyendo un *. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
		}
		case 101 -> {
			error = st.append("No se puede empezar leyendo un  _. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 102 -> {
			error = st.append("En este estado no se puede leer otro caracter distinto a &. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 103 -> {
			error = st.append("En este estado no se puede leer otro caracter distinto a =. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 104 -> {
			error = st.append("En este estado no se puede leer otro caracter distinto a *. Linea: ").append(linea).append("\n").toString();
		
			System.out.println(error);
			break;
		}
		case 105 -> {
			error = st.append("Cadena no cerrada. Linea: ").append(linea).append("\n").toString();

			System.out.println(error);
			break;
		}
		case 106 -> {
			error = st.append("Se ha leido un caracter erroneo. Linea:").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 107 -> {
			error = st.append("Supera el maximo entero valido. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 108 -> {
			error = st.append("Supera el maximo de 64 caracteres. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		case 200 -> {
			error = st.append("Error en el sistema de producion. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;		
		}
		case 201 -> {
			error = st.append("Error al realizar emparejado. Linea: ").append(linea).append("\n").toString();
			System.out.println(error);
			break;
		}
		}
	}

}
