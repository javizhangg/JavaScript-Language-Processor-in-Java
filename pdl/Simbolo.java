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

    


    @Override
    public String toString() {
        return "";
    }
}

