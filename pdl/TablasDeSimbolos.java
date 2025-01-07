package pdl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TablasDeSimbolos {
	public static  Map<Integer,TS> gestorTS;
	int numeroTabla;
	FileWriter fw; 
	static boolean esGlobal;

	
	public static TS tablaSimboloG = new TS();
	public TablasDeSimbolos(FileWriter fw){
		gestorTS = new HashMap<>();
		this.fw = fw;
		numeroTabla = 0;
	}

	//Función para añadir tablas locales 
	public void añadirTablaLocalTS() {
		gestorTS.put(1, new TS());
		esGlobal = false;
	}

	//lo implementamos para obtener el simbolo de la funcion 
	public Simbolo getFuncion() {
		Simbolo simbolo;
		String ultimoSimbolo =tablaSimboloG.getLastSimbolo();

		simbolo = tablaSimboloG.getSimbolo(ultimoSimbolo);

		return simbolo;
	}
	//Funcion para obtener el simbolo, dandoigual si es del global o del local
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
	public void añadirTablaGlobalTS() {
		gestorTS.put(0, tablaSimboloG);
		esGlobal = true;

	}

	//Función que devuelve la tabla TS del ambito especificado: 0 -> Global, 1 -> Local
	//Y lo elimina del gestor de TS
	public void getTablaTS(int ambito) throws IOException {
		//No hace hacer if ambito es 0 o cosas así, esto arregla ademas el error de que solo se imprimiera la tabla global.
		imprimirTabla(gestorTS.get(ambito));
		System.out.print("se imprime" + ambito);
		gestorTS.remove(ambito);

		//Solo incrementamos esta variable cada vez que llamemos getTablaTS
		//Esto arregla el error del numero incorrecto
		numeroTabla++;
		esGlobal = true;
	}


	public void imprimirTabla(TS tablaSimbolo) throws IOException {
		if(esGlobal) {
			fw.write("CONTENIDOS DE LA TABLA GLOBAL#" + numeroTabla + " :" + "\n");
		}
		else {
			fw.write("CONTENIDOS DE LA TABLA LOCAL#" + numeroTabla + " :" + "\n" );
		}

		for (String nombre : tablaSimbolo.tablaSimbolo.keySet()) {
			Simbolo simbolo = tablaSimbolo.getSimbolo(nombre);

			fw.write("* LEXEMA : '" + nombre + "'" + "\n");
			StringBuilder atributos = new StringBuilder("  Atributos :" + "\n");
			if (simbolo.getTipo() != null && simbolo.getDireccionMemoria() >= 0 && simbolo.GetTipoDev()!=null) {
				atributos.append("      Tipo=").append(simbolo.getTipo().getTipo()).append("\n");
			}
			else if (simbolo.getTipo() != null && simbolo.getDireccionMemoria() >= 0) {
				atributos.append("      Tipo=").append(simbolo.getTipo().getTipo()).append("\n");
				atributos.append("      DireccionMemoria=").append(simbolo.getDireccionMemoria()).append("\n");
			}

			if (simbolo.getNumPar() >= 0 && simbolo.GetTipoDev()!=null ) {
				atributos.append("      NumParametros=").append(simbolo.getNumPar()).append("\n");
				atributos.append("      TipoDev=").append(simbolo.GetTipoDev().getTipo()).append("\n");
				atributos.append("      TipoParametro=");
				for(Tipo tipo : simbolo.tipoParametro.values() ) {
					atributos.append(tipo.getTipo()).append(" , ");
				}
				atributos.append("\n");

			}

			// Imprimir línea de atributos
			fw.write(atributos.toString() + "\n");

			fw.write("  --------- ---------- " + "\n");
		}

	}

}
