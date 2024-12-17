package pdl;

public class Simbolo {
	private String lexema;
	private String etiqueta;
    private Tipo tipo; 
    private Tipo tipoParametro;
    private int ambito;  //global, local
    private int direccionMemoria;  //desplazamiento
    private int posEnTablaDeSimbolos;
    private int numParametros;
    

    // Constructor variables que no sean funciones
    public Simbolo(String lexema,Tipo tipo, int ambito,int posEnTablaDeSimbolos, int direccionMemoria) {
    	this.lexema = lexema;
        this.tipo = tipo;
        this.ambito = ambito;
        this.posEnTablaDeSimbolos = posEnTablaDeSimbolos;
        this.direccionMemoria = direccionMemoria;
    }
    
 // Constructor funciones
    public Simbolo(String lexema,Tipo tipo, int ambito,int posEnTablaDeSimbolos, int direccionMemoria,int numParametros, Tipo tipoParametro, String etiqueta) {
    	this.lexema = lexema;
        this.tipo = tipo;
        this.ambito = ambito;
        this.posEnTablaDeSimbolos = posEnTablaDeSimbolos;
    }

   
    // Getters y setters
    public String getTipo() {
        return tipo;
    }

   public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public int getDireccionMemoria() {
        return direccionMemoria;
    }


    @Override
    public String toString() {
        return "";
    }
}

