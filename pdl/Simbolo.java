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
    

    //Constructor que va a llamar el AFD para insertar solamente los lexemas
    public Simbolo(String lexema) {
    	this.lexema = lexema;
    }
    
    // Getters y setters
    public Tipo getTipo() {
        return tipo;
    }

   public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
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

