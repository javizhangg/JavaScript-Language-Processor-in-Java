package pdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TS {
//	private Map<String,Integer> posEnTablaSimbolo;
	public Map<String,Simbolo> tablaSimbolo;
	int contsimb; 
	int numeroTabla;
	FileWriter fw;
	StringBuilder st;
	public TS(int ambito,FileWriter fw) {
		this.numeroTabla = numeroTabla;
		this.fw = fw;
//		posEnTablaSimbolo=new HashMap<>();
		tablaSimbolo = new LinkedHashMap<>();
		st = new StringBuilder();
		contsimb=0;
	}

	public Simbolo getSimbolo(String lexema) {
//		return posEnTablaSimbolo.get(lexema);
		return tablaSimbolo.get(lexema);
	}

	public void InsertarTS(String lexema,Simbolo simbolo) throws IOException {
//		posEnTablaSimbolo.put(lexema, contsimb);
		tablaSimbolo.put(lexema, simbolo);
//		contsimb++;
	}
	
	public void AddTipoTS(String lexema,Tipo tipo) {
		Simbolo simbolo = tablaSimbolo.get(lexema);
		simbolo.setTipo(tipo);
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
			fw.write("  Atributos :" + "\n");

			fw.write("  --------- ---------- " + "\n");
		}
	}

}

