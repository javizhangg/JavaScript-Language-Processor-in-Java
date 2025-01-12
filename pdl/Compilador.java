package pdl;

import java.io.IOException;

public class Compilador {
	
	public static void main (String [] args)  {
		try {
            System.out.println("Iniciando el compilador...");
            
            // Instanciar y ejecutar el analizador sintáctico
            AnalizadorSintactico as = new AnalizadorSintactico();
            as.P();
            as.out.flush();
    		as.out.close(); 
    		as.fwParse.close();
    		as.fwTS.close();
    		as.al.fwTokens.close();
    		as.al.fwTS.close();

            System.out.println("Compilación finalizada correctamente.");

        } catch (IOException e) {
            System.err.println("Error durante la compilación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
	}
	
}
