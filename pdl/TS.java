package pdl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TS {
	private Map<String,Integer> tablaSimbolo;
	int contsimb;
	
	public TS() {
		tablaSimbolo=new HashMap<>();
		contsimb=0;
		
		
		
	}
	
	public boolean Contiene(String lexema) {
		return tablaSimbolo.containsKey(lexema);
	}
	
	public int get(String lexema) {
		return tablaSimbolo.get(lexema);
	}
	
	public void InsertarTS(String lexema) throws IOException {
		tablaSimbolo.put(lexema, contsimb);
		contsimb++;
	}
	
}
