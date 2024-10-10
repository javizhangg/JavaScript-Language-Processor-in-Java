package pdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TS {
	private Map<String,Integer> posEnTablaSimbolo;
	public Map<String,Simbolo> tablaSimbolo;
	int contsimb;
	int numeroTabla;
	FileWriter fw;
	StringBuilder st;
	public TS(int numeroTabla,FileWriter fw) {
		this.numeroTabla = numeroTabla;
		this.fw = fw;
		posEnTablaSimbolo=new HashMap<>();
		tablaSimbolo = new HashMap<>();
		st = new StringBuilder();
		contsimb=0;
	}

	public boolean Contiene(String lexema) {
		return posEnTablaSimbolo.containsKey(lexema);
	}

	public int get(String lexema) {
		return posEnTablaSimbolo.get(lexema);
	}

	public void InsertarTS(String lexema,Simbolo simbolo) throws IOException {
		posEnTablaSimbolo.put(lexema, contsimb);
		tablaSimbolo.put(lexema, simbolo);
		contsimb++;
	}
	
	public void imprimirTabla() throws IOException {
		if(numeroTabla == 1)
		fw.write("CONTENIDOS DE LA TABLA GLOBAL#" + numeroTabla + " :" + "\n");
		else
			fw.write("CONTENIDOS DE LA TABLA#" + numeroTabla + " :" + "\n");
		
		for (String nombre : tablaSimbolo.keySet()) {
			Simbolo simbolo = tablaSimbolo.get(nombre);

			// Imprimir línea del lexema
			fw.write("* LEXEMA : '" + nombre + "'" + "\n");

			// Imprimir línea de atributos
			fw.write("  Atributos :");

			fw.write("  --------- ---------- " + "\n");
		}
	}

}

