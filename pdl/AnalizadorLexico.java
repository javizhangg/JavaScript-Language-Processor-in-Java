package pdl123.pdl;

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
		
		archivoEntrada = new File("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\prueba.txt");
		archivoSalidaTokens = new File("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\FicheroDeTokens");
		archivoSalidaTS = new File("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\FicheroDeTS");
//		archivoEntrada = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\prueba.txt");
//		archivoSalidaTokens = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTokens");
//		archivoSalidaTS = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
		try {
			fr = new FileReader("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\prueba.txt");
			fwTokens = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\FicheroDeTokens");
			fwTS = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\FicheroDeTS");
//			fr = new FileReader("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\prueba.txt");
//			fwTokens = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTokens");
//			fwTS = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.afdtoken=new AFD(br,fwTokens,fwTS);
	}
	public Token getToken() throws IOException {

		this.token = afdtoken.getToken();
		return this.token;
		
	}
	public int getLinea() {
		return afdtoken.posicionDeLinea;
	}

	public static void main (String[] args) throws IOException {
		AnalizadorLexico al = new AnalizadorLexico();
	
		
		al.getToken();
		System.out.println("Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);
		al.getToken();
		System.out.println("Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);
//		al.getToken();
		System.out.println("Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);
		al.getToken();
		al.getToken();
		fwTokens.close();
		fwTS.close();

	} 
}