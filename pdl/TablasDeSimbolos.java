package pdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TablasDeSimbolos {
	public static Map<Integer,TS> gestorTS;
	int numeroTabla;
	FileWriter fw; 
	boolean esGlobal;

	public Map<Integer,TS> getGestorTS() {
		return gestorTS;
	}
	
//	public static TS tablaSimboloG = new TS();
	public TablasDeSimbolos(FileWriter fw){
		gestorTS = new HashMap<>();
		this.fw = fw;
		numeroTabla = 2;
	}

	//Función para añadir tablas locales 
	public void CrearTSLocal() {
		gestorTS.put(1, new TS());
		esGlobal = false;
	}

	//lo implementamos para obtener el simbolo de la funcion 
	public Simbolo getFuncion() {
		String ultimoSimbolo =gestorTS.get(0).getLastSimbolo();
		return gestorTS.get(0).getSimbolo(ultimoSimbolo);
	}
	public String getlastsimbolo() {
		String ultimoSimbolo =gestorTS.get(1).getLastSimbolo();
		return ultimoSimbolo;
	}
	//Funcion para obtener el simbolo, dando igual si es del global o del local
	public Simbolo getSimboloGL(String lexSimbolo) {
		Simbolo simbolo;
		if(gestorTS.containsKey(1)) {
			if(gestorTS.get(1).tablaSimbolo.containsKey(lexSimbolo)) {
				simbolo=gestorTS.get(1).getSimbolo(lexSimbolo);
			}else {
				simbolo=gestorTS.get(0).getSimbolo(lexSimbolo);
			}
		}else {
			simbolo=gestorTS.get(0).getSimbolo(lexSimbolo);
		}
		return simbolo;
	}


	//Sirve para comprobar la existencia de tablas
	public boolean hayTabla() {
		if(gestorTS.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

	//Función para la tabla global
	public void CrearTSGlobal() {
		gestorTS.put(0, new TS());
		esGlobal = true;

	}

	//Función que devuelve la tabla TS del ambito especificado: 0 -> Global, 1 -> Local
	//Y lo elimina del gestor de TS
	public void LiberaTablaTS(int ambito) throws IOException {
		//No hace hacer if ambito es 0 o cosas así, esto arregla ademas el error de que solo se imprimiera la tabla global.
		imprimirTabla(gestorTS.get(ambito));
		gestorTS.remove(ambito);

		//Solo incrementamos esta variable cada vez que llamemos getTablaTS
		//Esto arregla el error del numero incorrecto
		numeroTabla++;
		esGlobal = true;
	}


	public void imprimirTabla(TS tablaSimbolo) throws IOException {
		boolean primeraInstancia = false;
		int cont = 1;
		if(esGlobal) {
			fw.write("CONTENIDOS DE LA TABLA GLOBAL #" + 1 + " :" + "\n");
		}
		else {
			fw.write("CONTENIDOS DE LA TABLA LOCAL #" + numeroTabla + " :" + "\n" );
		}

		for (String nombre : tablaSimbolo.tablaSimbolo.keySet()) {
			Simbolo simbolo = tablaSimbolo.getSimbolo(nombre);

			fw.write("* LEXEMA: '" + nombre + "'" + "\n");
			StringBuilder atributos = new StringBuilder("  Atributos :" + "\n");
			if (simbolo.getTipo() != null && simbolo.getDireccionMemoria() >= 0 && simbolo.GetTipoDev()!=null) {
				atributos.append("      + Tipo: " + "'"+ simbolo.getTipo().getTipo() + "'" + "\n");
			}
			else if (simbolo.getTipo() != null && simbolo.getDireccionMemoria() >= 0) {
				atributos.append("      + Tipo: " + "'" + simbolo.getTipo().getTipo()+ "'" + "\n");
				atributos.append("      + Despl: ").append(simbolo.getDireccionMemoria()).append("\n");
			}

			if (simbolo.getNumPar() >= 0 && simbolo.GetTipoDev()!=null ) {
				atributos.append("      + numParam: ").append(simbolo.getNumPar()).append("\n");
				for(Tipo tipo : simbolo.tipoParametro.values() ) {
					atributos.append("      		+ TipoParam" +cont+ ": " + "'" + tipo.getTipo() + "'" +  "\n");
					cont++;
				}
				atributos.append("      + TipoRetorno: " + "'" + simbolo.GetTipoDev().getTipo() + "'" +"\n");
				atributos.append("      + EtiqFuncion: " + "'" + simbolo.getEtiqueta() + "'" +"\n");
			}

			// Imprimir línea de atributos
			fw.write(atributos.toString());
			fw.write("  --------- ---------- " + "\n");
		}
	}

}
