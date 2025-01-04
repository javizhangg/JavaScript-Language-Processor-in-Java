package pdl;

public class Tipo {
	private int tam;
	private String tipo;
	Tipo(){
		tipo="null";
	}
	public void puttam(int tamano) {
		tam=tamano;
	}
	public int gettam() {
		return tam;
	}
	public void putTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
			
}
