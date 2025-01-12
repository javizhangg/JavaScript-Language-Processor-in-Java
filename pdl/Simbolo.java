package pdl;

import java.util.HashMap;
import java.util.Map;

public class Simbolo {
	private String lexema;
	private String etiqueta;
    private Tipo tipo; 
    public Map <Integer,Tipo> tipoParametro;
    private int contp;
    private Tipo tipoDev;
    private int ambito;  //global, local
    private int direccionMemoria;  //desplazamiento
    private int posEnTablaDeSimbolos;
    private int numParametros;
    

    //Constructor que va a llamar el AFD para insertar solamente los lexemas
    public Simbolo(String lexema) {
    	this.lexema = lexema;
        this.contp = 1;
        this.tipoParametro = new HashMap<>(); // Inicialización aquí
    }
    /*--------------------------Variable normal--------------------------------------------*/
    //Nos devuelve el lexema 
    public String getLexema() {
    	return lexema;
    }
    
    //Sirve para Tipo
    public Tipo getTipo() {
        return tipo;
    }
    

   public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
   
   //Sirve para direcion de memoria 
   public int getDireccionMemoria() {
       return direccionMemoria;
   }
   public void setDireccionMemoria(int posmem) {
	   direccionMemoria=posmem;
   }

   /*--------------------------Funciones--------------------------------------------*/
    //Sirve para tipoParamtetro en funciones
    public void setTipoParametro(Tipo tipo){
    	tipoParametro.put(contp, tipo );
    	contp++;
    }
    public Tipo getTipoParametro(int index){
 
    	return tipoParametro.get(index);
    }
    
    //Sirve para tipoDev en funciones
    public void setTipoDev(Tipo tipo){
    	tipoDev =tipo;
    }
    public Tipo GetTipoDev(){
    	return tipoDev;
    }
    
    
    //Sirve para numero de parametros 
    public void setNumPar(int numpar) {
    	posEnTablaDeSimbolos=numpar;
    }
   
    public int getNumPar() {
    	return posEnTablaDeSimbolos;
    }
    
    
    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }
    
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

    //Imprime los parametros necesarios de la función
    public String imprimirContenido() {
        int key = 1; 
        StringBuilder st = new StringBuilder();
        st.append("La funcion: " + getLexema() + ", espera los siguientes párametros: ");
        while (tipoParametro.containsKey(key)) { 
            Tipo valor = tipoParametro.get(key); // 
            st.append("Parámetro: " + key + ", Tipo: " + valor.getTipo() + ". "); 
            key++; // Incrementa para la siguiente clave
        }
        return st.toString();
    }


}

