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
	private Token token;
	private AFD afdtoken;

	//Constructor del Analizador Lexico con la ruta del fichero fuente
	public AnalizadorLexico() throws IOException {
		
		archivoEntrada = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\prueba.txt");
		archivoSalidaTokens = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTokens");
		archivoSalidaTS = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
		try {
			
			fr = new FileReader("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\prueba.txt");
			fwTokens = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTokens");
			fwTS = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.afdtoken=new AFD(br,fwTokens,fwTS);
	}
	public Token getToken() throws IOException {

		this.token = getToken();
		return this.token;
		
	}

//	public static void main (String[] args) throws IOException {
//		AnalizadorLexico al = new AnalizadorLexico();
//	
//		
//		al.afdtoken.getToken();
//		al.afdtoken.ImprimirTabla();
//		
//		fwTokens.close();
//		fwTS.close();
//
//	} 
}