package pdl;

//CLASE TOKEN ---------------------------------------------------------------
class Token{
	private final int codigo;
	private final String atributo;

	StringBuilder st = new StringBuilder();

	//Constructor de la clase Token
	public Token(int codigo,String atributo) { 
		this.codigo = codigo;
		this.atributo = atributo;
	}

	//Imprime <codigo,atributo>
	public String toString() {                
		st.append("<").append(codigo).append(",").append(atributo).append(">");
		return st.toString();
	}

	public int getCodigo() {
		return codigo;
	}

	public String getAtributo() {
		return atributo;
	}

}
