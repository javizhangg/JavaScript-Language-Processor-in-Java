package pdl123.pdl;

public class Simbolo {
	private String lexema;
    private String tipo;  
    private String ambito;  //global, local
    private int direccionMemoria;  //desplazamiento

    // Constructor
    public Simbolo(String lexema) {
    	this.lexema = lexema;
//        this.tipo = tipo;
//        this.ambito = ambito;
    }

//    // Getters y setters
//    public String getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(String tipo) {
//        this.tipo = tipo;
//    }
//
//    public String getAmbito() {
//        return ambito;
//    }
//
//    public void setAmbito(String ambito) {
//        this.ambito = ambito;
//    }

//    public int getDireccionMemoria() {
//        return direccionMemoria;
//    }


    @Override
    public String toString() {
        return "";
    }
}

