package pdl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AnalizadorLexico{
	private File archivoEntrada;
	private File archivoSalidaTokens;
	private File archivoSalidaTS;
	
	private FileReader fr;
	private static FileWriter fwTokens;
	private static FileWriter fwTS;
	private static BufferedReader br;
	

	//Constructor del Analizador Lexico con la ruta del fichero fuente
	public AnalizadorLexico(String nombreArchivoEntrada, String nombreArchivoSalidaTokens,String nombreArchivoSalidaTS) throws IOException {
		archivoEntrada = new File(nombreArchivoEntrada);
		archivoSalidaTokens = new File(nombreArchivoSalidaTokens);
		archivoSalidaTS = new File(nombreArchivoSalidaTS);
		try {
			
			fr = new FileReader(archivoEntrada);
			fwTokens = new FileWriter(archivoSalidaTokens);
			fwTS = new FileWriter(nombreArchivoSalidaTS);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) throws IOException {
		AnalizadorLexico al = new AnalizadorLexico("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\prueba.txt",
				"C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTokens",
				"C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTS");
		AFD afd = new AFD(br,fwTokens,fwTS);
		
		afd.getToken();
		afd.ImprimirTabla();
		
		fwTokens.close();
		fwTS.close();

	}
}