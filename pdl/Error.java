package pdl;

//CLASE ERROR ------------------------------------------------------------------
class Error {
	private String mensaje;
	private int linea;
	private char caracter;
	StringBuilder st = new StringBuilder();
	// Constructor de un error lexico

	public Error(String mensaje, char caracter,  int linea) {
		this.mensaje = mensaje;
		this.linea = linea;
		this.caracter=caracter;
	}

	public String toString() {
		return st.append("Se ha producido un error lexico en la linea: ").append(linea).append(" , por el motivo: ")
				.append(mensaje).append(caracter).toString();
	}

}
