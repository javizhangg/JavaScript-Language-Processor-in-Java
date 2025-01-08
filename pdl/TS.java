package pdl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class TS {
	public Map<String,Simbolo> tablaSimbolo;
	private String lastsimbolo;
	public TS() {
		tablaSimbolo = new LinkedHashMap<>();
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
		Simbolo simbolo = new Simbolo(lexema);
		lastsimbolo=lexema;
		
		//Esta linea soluciona el permite el uso de variables globales
		if(!tablaSimbolo.containsKey(lexema))
		tablaSimbolo.put(lexema, simbolo);
		
	}
	
	public void AddTipoTS(String lexema,Tipo tipo) {
		Simbolo simbolo = tablaSimbolo.get(lexema);
		simbolo.setTipo(tipo);
	}

	
	
	

}

