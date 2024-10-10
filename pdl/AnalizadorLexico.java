package pdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AnalizadorLexico{
	private File archivoEntrada;
	private File archivoSalida;
	private FileReader fr;
	private static FileWriter fw;
	private static BufferedReader br;
	

	//Constructor del Analizador Lexico con la ruta del fichero fuente
	public AnalizadorLexico(String nombreArchivoEntrada, String nombreArchivoSalida) throws IOException {
		archivoEntrada = new File(nombreArchivoEntrada);
		archivoSalida = new File(nombreArchivoSalida);
		try {
			
			fr = new FileReader(archivoEntrada);
			fw = new FileWriter(archivoSalida);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) throws IOException {
		AnalizadorLexico al = new AnalizadorLexico("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\pdl123\\pdl\\prueba.txt","C:\\Users\\xiaol\\eclipse-workspace\\pdl\\pdl123\\pdl\\FicheroDeTokens");
		AFD afd = new AFD(br,fw);
		afd.getToken();
		fw.close();

	}
}