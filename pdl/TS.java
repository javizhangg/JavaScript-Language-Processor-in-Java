package pdl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TS {
	public Map<String,Simbolo> tablaSimbolo;
	public static int Desplazamiento;
	public static int numParametros;
	public TS() {
		numParametros=0;
		Desplazamiento=0;
		this.tablaSimbolo = new LinkedHashMap<>();
	}
	
	public boolean estaSimbolo(String lexema) {
		return tablaSimbolo.containsKey(lexema);
	}
	//Función auxiliar, que devuelve el simbolo dado el lexema correspondiente al simbolo buscado.
	public Simbolo getSimbolo(String lexema) {
		return tablaSimbolo.get(lexema);
	}

	//Función auxiliar, que inserta en la TS el simbolo
	public void InsertarTS(String lexema) throws IOException {
//		posEnTablaSimbolo.put(lexema, contsimb);
		tablaSimbolo.put(lexema, new Simbolo(lexema));
//		contsimb++;
	}
	
	public void AddTipoTS(String lexema,Tipo tipo) {
		Simbolo simbolo = tablaSimbolo.get(lexema);
		simbolo.setTipo(tipo);
	}
	
	

}

