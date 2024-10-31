//package pdl;
//
//import java.io.IOException;
//
//public class Compilador {
//	static AnalizadorLexico al;
//	Compilador(String nombreArchivoEntrada, String nombreArchivoSalidaTokens,String nombreArchivoSalidaTS) throws IOException{
//		al = new AnalizadorLexico("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\prueba.txt",
//				"C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTokens",
//				"C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTS");
//	}
//	public static void main (String [] args) throws IOException {
//		Compilador comp = new Compilador("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\prueba.txt",
//				"C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTokens",
//				"C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTS");
//		System.out.println(al.getToken());
//	}
//	
//}
