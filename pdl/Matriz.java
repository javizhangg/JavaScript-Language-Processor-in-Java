package pdl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Matriz {
	private ArrayList<Map<Character, Pair>> mt;
	private BufferedReader br;
	String directorio;
	private Character[] chars;
	
	Matriz(String directorio) throws IOException{
		this.directorio=directorio;
		this.chars = new Character[] { 'a', 'b', '"', '+', '=', '&', '|', '(', ')', ',', ';', '{', '}', '/', '*', '_', ' ',
				'\r' ,'c'};
		this.mt = new ArrayList<>();
		this.matriz();
	}
	// METODO 1: PARA INICIALIZAR LA MATRIZ DE TRANSICIONES
	public void matriz() throws IOException {
		try {
			FileReader fr = new FileReader(directorio);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String linea = null;

		while ((linea = br.readLine()) != null) {
			Map<Character, Pair> colE = new HashMap<Character, Pair>();
			Map<Character, Pair> colA = leerCSV(linea, colE);
			mt.add(colA);
		}
	}
	// METODO 2: PARA INICIALIZAR LA MATRIZ DE TRANSICIONES
		public Map<Character, Pair> leerCSV(String linea, Map<Character, Pair> col) {
			// Dividir la línea usando ';' como delimitador
			String[] arraylinea = linea.split(";");
			int n = 0;
			// Ajustamos el bucle para evitar índices fuera de rango y cadenas vacías
			for (int i = 1; i < arraylinea.length; i += 2) {
				// Verificamos si las celdas contienen datos
				if (!arraylinea[i].isEmpty() && !arraylinea[i - 1].isEmpty()) {
					char actual = arraylinea[i].charAt(0); // Obtenemos el carácter
					int estado = Integer.parseInt(arraylinea[i - 1]); // Convertimos el estado a entero

					// Añadimos al mapa
					col.put(chars[n], new Pair(estado, actual));
				} else {
					// Obtener el carácter
					int error = Integer.parseInt(arraylinea[i]); // Convertir el estado a entero

					// Manejar el caso donde la cadena está vacía
					col.put(chars[n], new Pair(error));
				}
				n++;
			}
			return col;
		}
		public Pair getPar(int estado, char c){
			return mt.get(estado).get(c);
		}
		public Object getAccion(int estado, char c){
			return mt.get(estado).get(c).getAccion();
		}
		public Integer getEstado(int estado, char c){
			return mt.get(estado).get(c).getEstado();
		}
		
		
}
