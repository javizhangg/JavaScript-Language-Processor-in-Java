package pdl;

public class Tipo {
	private int tam;
	private String tipo;
	Tipo(){
		tipo="null";
	}
	Tipo(String tipo){
		this.tipo = tipo;
	}
	public void setTam(int tamano) {
		tam=tamano;
	}
	public int getTam() {
		return tam;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
			
}
