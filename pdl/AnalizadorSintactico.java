package pdl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class AnalizadorSintactico {
	private File archivoSalidaParse;
	private File archivoSalidaTS;
	public static FileWriter fwParse;
	public static FileWriter fwTS;
	BufferedWriter bw;
	Token sig_token; 
	AnalizadorLexico al;
	PrintWriter out;
	First first = new First();
	
	//Guardamos una referencia al simbolo de la funcion
	Simbolo simboloDeFuncion;
	
	private int despL;
	private int despG;
	
	private boolean zona_declarada;
	private boolean zona_parametro;
	
	private int num_parametros;
	
	
	//Tablas de Simbolos en nuestro analizador Sintactico
	public AnalizadorSemantico As;
	public TablasDeSimbolos gestorTablas;
	
	public AnalizadorSintactico() throws IOException{
//		archivoSalidaParse = new File("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroParse");
//		archivoSalidaTS = new File("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTS");
		  archivoSalidaParse = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
		  archivoSalidaTS = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
		  
		try {
//			fwParse = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
//			fwTS = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			fwParse = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
			fwTS = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			bw=new BufferedWriter(fwParse);
			out = new PrintWriter(bw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.al=new AnalizadorLexico();
		out.print( "Descendente ");
		fwParse.flush();
		sig_token = al.getToken();
		
		this.gestorTablas = new TablasDeSimbolos(fwTS);
		this.As = new AnalizadorSemantico(gestorTablas);
		num_parametros = 0;
		zona_declarada=false;
		zona_parametro = false;
		
		//		System.out.println("Estado: " + al.afdtoken.estado +  " Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);
	}
	public void empareja(int idToken) throws IOException {
		if(sig_token.getCodigo()==idToken) {
			
		
			sig_token=al.getToken();
			//				System.out.println("Estado: " + al.afdtoken.estado +  " Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);

			System.out.println("token siguiente " + sig_token.getCodigo());
		}
		else 
			new Error(201,al.getLinea()).getError();;
//							System.out.println("Token error :" + sig_token.getCodigo() + " se esperaba: " + idToken + ". Linea: " + al.afdtoken.posicionDeLinea);
	}
	
    //nos serviara para añadir el tiporev en una funcion 
	public void AddFuncionTS(String id, Tipo tipo) {
		if (gestorTablas.gestorTS.get(0).estaSimbolo(id)) {
			gestorTablas.gestorTS.get(0).getSimbolo(id).setTipoDev(tipo);
			//Las funciones son de tipo Función
			gestorTablas.gestorTS.get(0).getSimbolo(id).setTipo(new Tipo("Función"));
			simboloDeFuncion = gestorTablas.getFuncion();
			
		}else {
			System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla local.");
		}
	}
	
	
	//Funcion para añadir tipo a un id en caso de que no sea de tipo funcion se necarga de ver si existe o no si no existe se añade y despues de añadirlo se indica el desplazamiento correspondiente
	public void AddTipoTS(String id, Tipo tipo) {
		
		if (gestorTablas.gestorTS.containsKey(1)) {
	        if (gestorTablas.gestorTS.get(1).estaSimbolo(id)) {
	        	
	        	//Esto permite añadir los tipos de los parametros pasados a la funcion
	        	if(zona_parametro) {
	        		simboloDeFuncion.setTipoParametro(tipo);
	    		}
	            gestorTablas.gestorTS.get(1).getSimbolo(id).setTipo(tipo);
	            
	            //El tamaño está guardado en el tipo
	            gestorTablas.gestorTS.get(1).getSimbolo(id).setDireccionMemoria(despL);
		        despL+=tipo.gettam();
	            
	           
	        } else {
	            System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla local.");
	        }
	    } else if (gestorTablas.gestorTS.get(0).estaSimbolo(id)) {
	        gestorTablas.gestorTS.get(0).getSimbolo(id).setTipo(tipo);
	        
	        //El tamaño está guardado en el tipo
	        gestorTablas.gestorTS.get(0).getSimbolo(id).setDireccionMemoria(despG);
	        despG+=tipo.gettam();
	        	
	    } else {
	        System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla global.");
	    }
		
	}
	
	public Tipo BuscaFuncionTipoTS(String id) {
			 return gestorTablas.gestorTS.get(0).getSimbolo(id).GetTipoDev();
	}
	public Tipo BuscaTipoTS(String id) {
		if (gestorTablas.gestorTS.containsKey(1) && gestorTablas.gestorTS.get(1).estaSimbolo(id)) {
	        return gestorTablas.gestorTS.get(1).getSimbolo(id).getTipo();
	    } else if (gestorTablas.gestorTS.get(0).estaSimbolo(id)) {
	        return gestorTablas.gestorTS.get(0).getSimbolo(id).getTipo();
	    } else {
	        System.err.println("Error: Identificador '" + id + "' no encontrado.");
	        return new Tipo("error");
	    }
	}
	
	
	public void P2() throws IOException {
		
		gestorTablas.añadirTablaGlobalTS();
		despG=0;
		P();
		gestorTablas.getTablaTS(0);
	}
	public void P() throws IOException {
		
		
		if(first.first.get("B").contains(sig_token.getCodigo())) {
			out.print(1 + " ");
			B();
			P();
		}
		else if(first.first.get("F").contains(sig_token.getCodigo())) {
			out.print(2 + " ");
			F();
			P();
		}
		else if(sig_token.getCodigo() == 29)  {
			
			out.print(3 + " ");
			
			//LAMBDA
		}
		else {
			new Error(202,al.getLinea()).getError();
		}
			
			
	}

	public Tipo E() throws IOException {
		Tipo tipo=new Tipo();
		out.print(4 + " ");
		Tipo R_tipo = R();
        Tipo E2_tipo = E2();
//        System.out.print("R:"+R_tipo.getTipo());
//        System.out.print("E2:"+E2_tipo.getTipo()+ "\n");
        if (R_tipo.getTipo().equals("boolean") && E2_tipo.getTipo().equals("boolean")) {
        	tipo.puttam(2);
        	tipo.putTipo("boolean");
            return tipo;
        } else {
        	new Error(300,al.getLinea()).getError();
        	tipo.putTipo("error");
        	return tipo;
        }
	}

	public Tipo E2() throws IOException {
		Tipo tipo =new Tipo();
		if(first.first.get("E'").contains(sig_token.getCodigo())) {
			out.print(5 + " ");
			empareja(sig_token.getCodigo());
			Tipo R_tipo = R();
            Tipo E2_tipo = E2();
//            System.out.print("R:"+R_tipo.getTipo());
//            System.out.print("E2:"+E2_tipo.getTipo()+ "\n");
			if (R_tipo.getTipo().equals("boolean") && E2_tipo.getTipo().equals("boolean")) {
	        	tipo.puttam(2);
	        	tipo.putTipo("boolean");
	            return tipo;
	        } else {
	        	new Error(301,al.getLinea()).getError();
	        	tipo.putTipo("error");
	        	return tipo;
	        }
		}
		else if(first.follow.get("E'").contains(sig_token.getCodigo())) //FOLLOW E'
		{
			out.print(6 + " ");
			tipo.puttam(2);
			tipo.putTipo("boolean");
			return tipo;
			//LAMBDA
		}
		else {
			new Error(203,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
			
		
	}

	public Tipo R() throws IOException {
		Tipo tipo=new Tipo();
		out.print( 7+" ");
		 Tipo U_tipo = U();
	     Tipo R2_tipo = R2();
	     
//         System.out.print("U:"+U_tipo.getTipo());
//         System.out.print("R2:"+R2_tipo.getTipo()+ "\n");
         if (U_tipo.getTipo().equals("int") && R2_tipo.getTipo().equals("int")) {
	    	 	tipo.puttam(2);
	    	 	tipo.putTipo("boolean");
	            return tipo;
	     } else {
	        	new Error(302,al.getLinea()).getError();
	        	tipo.putTipo("error");
	        	return tipo;
	     }
	}

	public Tipo R2() throws IOException {
		Tipo tipo =new Tipo();
		if(first.first.get("R'").contains(sig_token.getCodigo())) {
			out.print( 8+" ");
			empareja(sig_token.getCodigo());
			Tipo U_tipo = U();
		    Tipo R2_tipo = R2();
//		    System.out.print("U:"+ U_tipo.getTipo());
//		    System.out.print("R2:"+R2_tipo.getTipo()+ "\n");
		    if (U_tipo.getTipo().equals("int")) {
	    	 	tipo.puttam(2);
	    	 	tipo.putTipo("int");
	            return tipo;
		    } else {
	        	new Error(303,al.getLinea()).getError();
	        	tipo.putTipo("error");
	        	return tipo;
		    }
		}
		else if(first.follow.get("R'").contains(sig_token.getCodigo())) //FOLLOW R'
		{
			out.print( 9+" ");
			tipo.puttam(2);
			tipo.putTipo("int");
			return tipo;
			//LAMBDA
		}
		else {
			new Error(204,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo U() throws IOException {
		Tipo tipo=new Tipo();
		out.print( 10+" ");
		Tipo V_tipo = V();
        Tipo U2_tipo = U2();
//        System.out.print("V:"+V_tipo.getTipo());
//        System.out.print("U2:"+U2_tipo.getTipo()+ "\n");
        if(V_tipo.getTipo().equals(U2_tipo.getTipo())) {
        	return U2_tipo;
        }else{
        	tipo.putTipo("error");
        	new Error(304,al.getLinea()).getError();
        	return tipo;
        }
        
	}

	public Tipo U2() throws IOException {
		Tipo tipo =new Tipo ();
		if(first.first.get("U'").contains(sig_token.getCodigo())) { // tokens +
			out.print( 11+" ");
			empareja(sig_token.getCodigo());
			 Tipo V_tipo = V();
	         Tipo U2_tipo = U2();
//	         System.out.print("V:"+V_tipo.getTipo());
//	         System.out.print("U2:"+U2_tipo.getTipo()+ "\n");
	         if (V_tipo.getTipo().equals("int")) {
	             tipo.puttam(2);
	             tipo.putTipo("int");
	        	 return tipo;
	         } else {
	        	 new Error(305,al.getLinea()).getError();
	        	 tipo.putTipo("error");
	             return tipo;
	         }
		}
		else if(first.follow.get("U'").contains(sig_token.getCodigo())) //FOLLOW U'
		{
			out.print( 12+" ");
			tipo.puttam(2);
			tipo.putTipo("int");
			return tipo;
			//LAMBDA
		}
		else {
			new Error(205,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo V() throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo()== 1) { //token id
			out.print( 13+" ");
			String id = sig_token.getAtributo();
			empareja(sig_token.getCodigo());
			return V2(id);
		}
		else if(sig_token.getCodigo()==16) {
			out.print( 14+" ");
			empareja(sig_token.getCodigo());
			Tipo E_tipo = E();
			empareja(17); // token )
			return E_tipo;
		}
		else if(sig_token.getCodigo()==2) {
			out.print( 15+" ");
			empareja(sig_token.getCodigo());
			tipo.puttam(2);
			tipo.putTipo("int");
			return tipo;
		}
		else if(sig_token.getCodigo()==3) {
			out.print( 16+" ");
			empareja(sig_token.getCodigo());
			tipo.puttam(128);
			tipo.putTipo("string");
			return tipo;
		}
//		else if(sig_token.getCodigo()==18){
//			out.print( 17+" ");
//			empareja(18);
//			empareja(1);
//			V2();
//		}
		else { 
			new Error(206,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo V2(String id) throws IOException{
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() ==  16) { //token (
			out.print( 18+" ");
			empareja(16);
			L();
			empareja(17); //token )
			return BuscaFuncionTipoTS(id);
		}
		else if(first.follow.get("V'").contains(sig_token.getCodigo())) //FOLLOW V'
		{
			out.print( 19+" ");
			
			return BuscaTipoTS(id);
			//LAMBDA
		}
		else {
			new Error(207,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
			
	}

	public void S() throws IOException {
		if(sig_token.getCodigo() == 1) {
			out.print(20 + " ");
			String id=sig_token.getAtributo();
			empareja(1);
			Tipo S2_tipo= S2(id);
			if(!S2_tipo.getTipo().equals(BuscaTipoTS(id).getTipo())) {
				new Error(306,al.getLinea()).getError();
			}
			
		}
		else if(sig_token.getCodigo() == 14) {
			out.print(21 + " ");
			empareja(14);
			Tipo E_tipo=E();
			empareja(19);
			if(!E_tipo.getTipo().equals("string")) {
				new Error(307,al.getLinea()).getError();
			}
		}
		else if(sig_token.getCodigo() == 15) {
			out.print(22 + " ");
			empareja(15);
			String id=sig_token.getAtributo();
			empareja(1);
			empareja(19);
			if(!BuscaTipoTS(id).getTipo().equals("string")) {
				new Error(308,al.getLinea()).getError();
			}
		}
		else if(sig_token.getCodigo() == 28) { //token return
			out.print(23 + " ");
			empareja(28);
			Tipo X_tipo = X();
			empareja(19);
			
		}
		else
			new Error(208,al.getLinea()).getError();
	}

	public Tipo S2(String id) throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() == 7) {
			out.print( 24+" ");
			empareja(7);
			Tipo E_tipo=E();
			empareja(19);
			if(!E_tipo.getTipo().equals("boolean")) {
				new Error(309,al.getLinea()).getError();
			}
			return E_tipo;
		}
		else if(sig_token.getCodigo() == 8) {
			out.print( 25+" ");
			empareja(8);
			Tipo E_tipo=E();
			empareja(19);	
			if(!BuscaTipoTS(id).getTipo().equals(E_tipo.getTipo())) {
				new Error(310,al.getLinea()).getError();
			}
			return E_tipo;
		}
		else if(sig_token.getCodigo() == 16) {
			
			out.print( 26+" ");
			empareja(16);
			L();
			empareja(17);
			empareja(19);	
			tipo.putTipo("void");
			return tipo;
		}else {
			out.print( 27+" ");
			tipo.putTipo("void");
			return tipo;
		}
			
	}

	public Tipo X() throws IOException {
		Tipo tipo =new Tipo();
		if(first.first.get("X").contains(sig_token.getCodigo())) { //tokens ( id ent cad
			out.print( 28+" ");
			Tipo E_tipo = E();
	        return E_tipo;
		}
		else if(first.follow.get("X").contains(sig_token.getCodigo())) //FOLLOW X
		{
			//LAMBDA
			out.print( 29+" ");
			tipo.putTipo("void");
			return tipo;
		}
		
		else {
			new Error(210,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public  Tipo L() throws IOException {
		Tipo tipo = new Tipo();
		if(first.first.get("E").contains(sig_token.getCodigo())) {

			out.print( 30+" ");
			Tipo E_tipo = E();
	        Tipo Q_tipo = Q();
	        if(E_tipo.getTipo().equals(Q_tipo.getTipo())) {
	        	return E_tipo;
	        }else{
	            new Error(311, al.getLinea()).getError();
	            tipo.putTipo("error");
	            return tipo;
	        }
		}
		else if(first.follow.get("L").contains(sig_token.getCodigo())) //FOLLOW L
		{
			out.print( 31+" ");
			tipo.putTipo("void");
			return tipo;
		}	
		else {
			new Error(211,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo  Q() throws IOException {
		Tipo tipo=new Tipo();
		if(sig_token.getCodigo() == 18) {
			out.print( 32+" ");
			empareja(sig_token.getCodigo());
			Tipo E_tipo = E();
	        Tipo Q_tipo = Q();
	        if(E_tipo.getTipo().equals(Q_tipo.getTipo())) {
	        	return E_tipo;
	        }else{
	            new Error(312, al.getLinea()).getError();
	            tipo.putTipo("error");
	            return tipo;
	        }
		}
		else if(first.follow.get("Q").contains(sig_token.getCodigo())) //FOLLOW Q
		{
			out.print( 33+" ");
			tipo.putTipo("void");
			return tipo;
			//LAMBDA
		
		}
		else {
			new Error(212,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo T() throws IOException {  // ;tipo
		Tipo tipo = new Tipo();
		if(sig_token.getCodigo() == 10) { //token int 
			out.print( 34+" ");
			empareja(10);	
			tipo.putTipo("int");
			tipo.puttam(2);
		}
		else if (sig_token.getCodigo() == 11) { //token boolean 
			out.print( 35+" ");
			empareja(11);
			tipo.putTipo("boolean");
			tipo.puttam(2);
		}
		else if(sig_token.getCodigo() == 12) { //token string 
			out.print( 36+" ");
			empareja(12);
			tipo.putTipo("string");
			tipo.puttam(128);
		}
		else {
			new Error(213,al.getLinea()).getError();
		}
		
		return tipo;
	}

	public Tipo A() throws IOException { // ; tipo
		Tipo tipo = new Tipo(); 
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			out.print( 37+" ");
			//			empareja(sig_token.getCodigo());
			tipo=T();
			
			if(zona_declarada) {
				num_parametros+=1;
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1);
			K();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print( 38+" ");
			empareja(sig_token.getCodigo());
			tipo.putTipo("void");
			tipo.puttam(0);
		}
		else 
			new Error(214,al.getLinea()).getError();
		
		return tipo;
	}

	public void B() throws IOException {
		Tipo tipo = new Tipo(); 
		if(sig_token.getCodigo() == 22) { //token if
			
			out.print( 39+" ");
			empareja(22);
			empareja(16);
			Tipo E_tipo=E();
			if (!E_tipo.getTipo().equals("boolean")) {
	            new Error(313, al.getLinea()).getError();
	        }
				empareja(17);
				S();
			
			
			
		}
		else if(first.first.get("S").contains(sig_token.getCodigo())) { //tokens id output input return
			out.print( 40+" ");
			S();
		}
		else if(sig_token.getCodigo() == 9) { //token var
			out.print( 41+" ");
			empareja(9);
			tipo=T();
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1);
			empareja(19);
		}
		else if(sig_token.getCodigo() == 25) { //token do
			out.print( 42+" ");
			empareja(25);
			empareja(20);
			C();
			empareja(21);
			empareja(26);
			empareja(16);
			Tipo E_tipo = E();
			empareja(17);
			empareja(19);
			if (!E_tipo.getTipo().equals("boolean")) {
	            new Error(314, al.getLinea()).getError();
	        }
		}
		else 
			new Error(215,al.getLinea()).getError();
	}

	public void C() throws IOException {
		if(first.first.get("B").contains(sig_token.getCodigo())) {
			out.print(43+ " ");
			B();
			C();
		}
		else if(first.follow.get("C").contains(sig_token.getCodigo())) //FOLLOW C
			out.print(44+" ");
			//LAMBDA
		else {
			if(sig_token.getCodigo()==27) 
				new Error(219,al.getLinea()).getError();
			else 
				new Error(216,al.getLinea()).getError();
		}
	}

	public void F() throws IOException {
		Tipo tipo =new Tipo();
		zona_declarada=true;
		String lexemafuncion;
		out.print( 45+" ");
		empareja(sig_token.getCodigo());
		tipo = H();
		lexemafuncion=sig_token.getAtributo();
		AddFuncionTS(lexemafuncion,tipo);
		
		empareja(1);
		gestorTablas.añadirTablaLocalTS();
		empareja(16);
		
		zona_parametro = true;
		A();
		zona_parametro = false;
		zona_declarada=false;
		gestorTablas.getSimboloGL(lexemafuncion).setNumPar(num_parametros);
		num_parametros=0;
		
		
		empareja(17);
		empareja(20);
		C();
		empareja(21);
		
		gestorTablas.getTablaTS(1);
	}

	public Tipo H() throws IOException { //; tipo
		Tipo tipo =new Tipo ();
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			out.print(46+" ");
			tipo=T();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print(47+" ");
			empareja(13);
			tipo.putTipo("void");
		}
		else 
			new Error(217,al.getLinea()).getError();
		
		return tipo;
	}

	public void K() throws IOException {
		Tipo tipo = new Tipo ();
		if(sig_token.getCodigo() == 18) {
			out.print(48+" ");
			empareja(sig_token.getCodigo());
			tipo = T();
			if(zona_declarada) {
				num_parametros+=1;
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1);
			K();
		}
		else if(first.follow.get("K").contains(sig_token.getCodigo())) //FOLLOW K token )
			out.print(49+" ");
		else 
			new Error(218,al.getLinea()).getError();
	}

	public static void main(String[] args) throws IOException {
		AnalizadorSintactico as = new AnalizadorSintactico();
		as.P2();
		
		as.out.flush();
		as.out.close(); 
		as.fwParse.close();
		as.fwTS.close();
		as.al.fwTokens.close();
		as.al.fwTS.close();
	}
}
