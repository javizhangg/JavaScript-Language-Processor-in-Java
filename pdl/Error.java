package pdl;

class Error {
	private String mensaje;
	private int linea;
	StringBuilder st = new StringBuilder();

	// Constructor de un error lexico
	public Error(String mensaje, int linea) {
		this.mensaje = mensaje;
		this.linea = linea;
	}

	public String toString() {
		return st.append("Se ha producido un error lexico en la linea: ").append(linea).append(" ,por el motivo: ")
				.append(mensaje).toString();
	}

}
