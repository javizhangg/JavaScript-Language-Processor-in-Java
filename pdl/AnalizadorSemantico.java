package pdl;

public class AnalizadorSemantico {
	TablasDeSimbolos gestorTablas;
	public AnalizadorSemantico(TablasDeSimbolos gestorTablas) {
		this.gestorTablas = gestorTablas;
	}
	
	//Devuelve la tabla local si existe, en otro caso, devuelve la tabla global
	public TS getTablaActual() {
	    return gestorTablas.gestorTS.containsKey(1) ? gestorTablas.gestorTS.get(1) : gestorTablas.gestorTS.get(0);
	}
	public Simbolo getSimbolo (String lexema) {
		if(gestorTablas.gestorTS.get(0).estaSimbolo(lexema)) {
			return gestorTablas.gestorTS.get(0).getSimbolo(lexema);
		}else if(gestorTablas.gestorTS.get(1).estaSimbolo(lexema)) {
			return gestorTablas.gestorTS.get(1).getSimbolo(lexema);
		}else {
			return null;
		}

	}
	
	
}
