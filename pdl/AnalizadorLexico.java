package pdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalizadorLexico{
	private File archivo;
	private FileReader fr;
	private static BufferedReader br;

	//Constructor del Analizador Lexico con la ruta del fichero fuente
	public AnalizadorLexico(String nombreArchivo) {
		archivo = new File(nombreArchivo);
		try {
			FileReader fr = new FileReader(archivo);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) throws IOException {
		AnalizadorLexico al = new AnalizadorLexico("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\prueba.txt");
		AFD afd = new AFD(br);
		afd.getToken();
		// afd.imprimirMapa();
	}
}