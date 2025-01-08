package pdl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TS {
	public Map<String,Simbolo> tablaSimbolo;
	int contsimb;
	private String lastsimbolo;
	public TS() {
		this.tablaSimbolo = new LinkedHashMap<>();
		contsimb = 0;
	}
	
	//Devuelve el ultimo simbolo que en caso de entrar en una funcion sera el simbolo de la funcion 
	public String getLastSimbolo() {
		return lastsimbolo;
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
		Simbolo simbolo = new Simbolo(lexema,contsimb);
		lastsimbolo=lexema;
//		posEnTablaSimbolo.put(lexema, contsimb);
		tablaSimbolo.put(lexema, simbolo);
		
		contsimb++;
	}
	
	public int getCont() {
		return contsimb;
	}
	
	public void AddTipoTS(String lexema,Tipo tipo) {
		Simbolo simbolo = tablaSimbolo.get(lexema);
		simbolo.setTipo(tipo);
	}

	
	
	

}

