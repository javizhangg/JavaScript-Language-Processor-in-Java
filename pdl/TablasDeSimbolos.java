package pdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TablasDeSimbolos {
	public static  Map<Integer,TS> gestorTS;
	int numeroTabla;
	FileWriter fw; 
	static boolean esGlobal;
	
	public static TS tablaSimboloL = new TS();
	public static TS tablaSimboloG = new TS();
	public TablasDeSimbolos(FileWriter fw){
		gestorTS = new HashMap<>();
		this.fw = fw;
		numeroTabla = 1;
	}

	//Función para añadir tablas locales 
	public void añadirTablaLocalTS() {
		
		gestorTS.put(1, tablaSimboloL);
		numeroTabla++;
		esGlobal = false;
		
	}
	public boolean hayTabla() {
		if(gestorTS.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	//Función para la tabla global
	public void añadirTablaGlobalTS() {
		gestorTS.put(0, tablaSimboloG);
		esGlobal = true;
	
	}
	
	//Función que devuelve la tabla TS del ambito especificado: 0 -> Global, 1 -> Local
	//Y lo elimina del gestor de TS
	public void getTablaTS(int ambito) throws IOException {
		if(ambito ==0) {
		imprimirTabla(gestorTS.get(0));
		System.out.print("se imprime" + ambito);
		gestorTS.remove(ambito);
		esGlobal = true;
		}else {
			System.out.print("se imprime" + ambito);
			gestorTS.remove(ambito);
			esGlobal = true;
		}
	}
	

	public void imprimirTabla(TS tablaSimbolo) throws IOException {
		if(esGlobal) {
		fw.write("CONTENIDOS DE LA TABLA GLOBAL#" + numeroTabla + " :" + "\n");
		}
		else {
			fw.write("CONTENIDOS DE LA TABLA#" + numeroTabla + " :" + "\n");
		}
		
		for (String nombre : tablaSimbolo.tablaSimbolo.keySet()) {
			Simbolo simbolo = tablaSimbolo.getSimbolo(nombre);
			System.out.print(simbolo.getLexema());

			// Imprimir línea del lexema
			fw.write("* LEXEMA : '" + nombre + "'" + "\n");
			
			// Imprimir línea de atributos
			fw.write("  Atributos :"+ simbolo.getTipo().getTipo() + simbolo.getDireccionMemoria()+ "\n");

			fw.write("  --------- ---------- " + "\n");
		}
		
	}
	
}
