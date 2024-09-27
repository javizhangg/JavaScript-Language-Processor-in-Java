package pdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorLexico{
	File archivo;
	FileReader fr;
	static BufferedReader br;

	//Guardo las palabras reservadas del lenguaje
	ArrayList<String> palabrasReservadas;

	//Constructor del Analizador Lexico con la ruta del fichero fuente
	public AnalizadorLexico(String nombreArchivo) {
		archivo = new File(nombreArchivo);
		try {
			FileReader fr = new FileReader(archivo);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		palabrasReservadas = new ArrayList<String>(Arrays.asList("var","int","boolean","string","void","output","input","if","else","then","do","while","function","return"));

	}

	public static void main (String[] args) throws IOException {
		AnalizadorLexico al = new AnalizadorLexico("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\prueba.txt");
	
		AFD afd = new AFD(br);
		afd.getToken();
//		afd.imprimirMapa();
	}
}