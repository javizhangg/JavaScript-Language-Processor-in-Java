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
	public static FileWriter fwTokens;
	public static FileWriter fwTS;
	public static BufferedReader br;
	private Token token;
	public AFD afdtoken;

	//Constructor del Analizador Lexico con la ruta del fichero fuente
	public AnalizadorLexico() throws IOException {
		archivoEntrada = new File(System.getProperty("user.dir")  + "/src/pdl/prueba.txt");
		archivoSalidaTokens = new File(System.getProperty("user.dir")  + "/src/pdl/FicheroDeTokens");
		archivoSalidaTS = new File(System.getProperty("user.dir")  + "/src/pdl/FicheroDeTS");
		try {
			fr = new FileReader(System.getProperty("user.dir")  + "/src/pdl/prueba.txt");
			fwTokens = new FileWriter(System.getProperty("user.dir")  + "/src/pdl/FicheroDeTokens");
			fwTS = new FileWriter(System.getProperty("user.dir")  + "/src/pdl/FicheroDeTS");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.afdtoken=new AFD(br,fwTokens,fwTS);
	}
	public Token getToken(boolean valorZonaDeclarada) throws IOException {
		this.token = afdtoken.getToken(valorZonaDeclarada);
		
		
		return this.token;
	}
	public int getLinea() {
		return afdtoken.posicionDeLinea;
	}
	

	//MAIN para probar el analizador Lexico
	public static void main (String[] args) throws IOException {
		AnalizadorLexico al = new AnalizadorLexico();
	
		while(al.afdtoken.c != -1) {
		al.getToken(false);
		System.out.println("Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + " ult: " + al.afdtoken.ultimaint);
		}
		fwTokens.close();
		fwTS.close();

	} 
}