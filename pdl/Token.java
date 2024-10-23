package pdl;

class Token{
	private final int codigo;
	private final String atributo;


	//Constructor de la clase Token
	public Token(int codigo,String atributo) { 
		this.codigo = codigo;
		this.atributo = atributo;
	}
 
	//Imprime <codigo,atributo>
	public String toString() {                
		return "<" + codigo + "," + atributo + ">";
	}

	public int getCodigo() {
		return codigo;
	}

	public String getAtributo() {
		return atributo;
	}

}
