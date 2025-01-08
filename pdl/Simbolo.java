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
    private int contSimb;
    

    //Constructor que va a llamar el AFD para insertar solamente los lexemas
    public Simbolo(String lexema,int contSimb) {
    	this.lexema = lexema;
        this.contp = 0;
        this.tipoParametro = new HashMap<>(); // Inicialización aquí
        this.contSimb = contSimb;
    }
    /*--------------------------Variable normal--------------------------------------------*/
    //Nos devuelve el lexema 
    public String getLexema() {
    	return lexema;
    }
    
    public int getContSimb() {
    	return contSimb;
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
    public Tipo getTipoParametro(){
    	Tipo tipo=null;
    	if(contp >=0) {
    	tipo = tipoParametro.get(contp);
    	tipoParametro.remove(contp);
    	contp--;
    	}
    	return tipo;
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

