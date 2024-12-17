package pdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TablasDeSimbolos {
	public Map<Integer,TS> gestorTS;
	int numeroTabla;
	FileWriter fw; 
	boolean esGlobal;
	public TablasDeSimbolos(FileWriter fw){
		gestorTS = new HashMap<>();
		this.fw = fw;
		numeroTabla = 1;
	}

	//Función para añadir tablas locales 
	public void añadirTablaLocalTS(TS tablaSimbolo) {
		gestorTS.put(1, tablaSimbolo);
		numeroTabla++;
		esGlobal = false;
	}
	
	//Función para la tabla global
	public void añadirTablaGlobalTS(TS tablaSimbolo) {
		gestorTS.put(0, tablaSimbolo);
		esGlobal = true;
	}
	
	//Función que devuelve la tabla TS del ambito especificado: 0 -> Global, 1 -> Local
	//Y lo elimina del gestor de TS
	public void getTablaTS(int ambito) throws IOException {
		imprimirTabla(gestorTS.get(ambito));
		gestorTS.remove(ambito);
		esGlobal = true;
	}
	

	public void imprimirTabla(TS tablaSimbolo) throws IOException {
		if(esGlobal)
		fw.write("CONTENIDOS DE LA TABLA GLOBAL#" + numeroTabla + " :" + "\n");
		else
			fw.write("CONTENIDOS DE LA TABLA#" + numeroTabla + " :" + "\n");
		
		for (String nombre : tablaSimbolo.tablaSimbolo.keySet()) {
			Simbolo simbolo = tablaSimbolo.getSimbolo(nombre);

			// Imprimir línea del lexema
			fw.write("* LEXEMA : '" + nombre + "'" + "\n");

			// Imprimir línea de atributos
			fw.write("  Atributos :" + "\n");

			fw.write("  --------- ---------- " + "\n");
		}
	}
	
}
