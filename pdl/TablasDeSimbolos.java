package pdl;

import java.io.FileWriter;
import java.util.Map;

public class TablasDeSimbolos {
	public Map<Integer,TS> tablasDeSimbolos;
	
	public TablasDeSimbolos(int ambito, FileWriter fw){
		tablasDeSimbolos.put(ambito, new TS(ambito, fw));
	}
	
	public TS getTablaTS(int ambito) {
		return tablasDeSimbolos.get(ambito);
	}
	
	//
	public boolean Contiene(String lexema,int ambito) {
			return 
	}
	
	
	
}
