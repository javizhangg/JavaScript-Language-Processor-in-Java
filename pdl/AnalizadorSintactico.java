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
	
	
	private int despL;
	private int despG;
	
	private boolean zona_declarada;
	
	private int num_parametros;
	
	//Tablas de Simbolos en nuestro analizador Sintactico
	public AnalizadorSemantico As;
	public TablasDeSimbolos gestorTablas;
	
	public AnalizadorSintactico() throws IOException{
		//archivoSalidaParse = new File("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroParse");
		  archivoSalidaParse = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
		  archivoSalidaTS = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
		  
		try {
			//fwParse = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
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
		
		zona_declarada=true;
		
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
		}else {
			System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla local.");
		}
	}
	
	
	//Funcion para añadir tipo a un id en caso de que no sea de tipo funcion se necarga de ver si existe o no si no existe se añade y despues de añadirlo se indica el desplazamiento correspondiente
	public void AddTipoTS(String id, Tipo tipo) {
		if(!zona_declarada) {
			num_parametros+=1;
			gestorTablas.getFuncion().setTipo(tipo);//cojemos el ultimo atributo y le añadimos el tipo del parametro
		}
		if (gestorTablas.gestorTS.containsKey(1)) {
	        if (gestorTablas.gestorTS.get(1).estaSimbolo(id)) {
	            gestorTablas.gestorTS.get(1).getSimbolo(id).setTipo(tipo);
	            
	            if(tipo.getTipo()=="string") {
	            	gestorTablas.gestorTS.get(1).getSimbolo(id).setDireccionMemoria(despL);
	            	despL+=64;
	            }else if(tipo.getTipo()=="int") {
	            	gestorTablas.gestorTS.get(1).getSimbolo(id).setDireccionMemoria(despL);
	            	despL+=2;
	            }else if(tipo.getTipo()=="boolean") {
	            	gestorTablas.gestorTS.get(1).getSimbolo(id).setDireccionMemoria(despL);
	            	despL+=2;
	            }
	            
	           
	        } else {
	            System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla local.");
	        }
	    } else if (gestorTablas.gestorTS.get(0).estaSimbolo(id)) {
	        gestorTablas.gestorTS.get(0).getSimbolo(id).setTipo(tipo);
	        if(tipo.getTipo()=="string") {
            	gestorTablas.gestorTS.get(0).getSimbolo(id).setDireccionMemoria(despG);
            	despG+=64;
            }else if(tipo.getTipo()=="int") {
            	gestorTablas.gestorTS.get(0).getSimbolo(id).setDireccionMemoria(despG);
            	despG+=2;
            }else if(tipo.getTipo()=="boolean") {
            	gestorTablas.gestorTS.get(0).getSimbolo(id).setDireccionMemoria(despG);
            	despG+=2;
            }
	        	
	    } else {
	        System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla global.");
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

	public void E() throws IOException {
		out.print(4 + " ");
		R();
		E2();
	}

	public void E2() throws IOException {
		if(first.first.get("E'").contains(sig_token.getCodigo())) {
			out.print(5 + " ");
			empareja(sig_token.getCodigo());
			R();
			E2();
		}
		else if(first.follow.get("E'").contains(sig_token.getCodigo())) //FOLLOW E'
			out.print(6 + " ");
			//LAMBDA
		else 
			new Error(203,al.getLinea()).getError();
	}

	public void R() throws IOException {
		out.print( 7+" ");
		U();
		R2();
	}

	public void R2() throws IOException {
		if(first.first.get("R'").contains(sig_token.getCodigo())) {
			out.print( 8+" ");
			empareja(sig_token.getCodigo());
			U();
			R2();
		}
		else if(first.follow.get("R'").contains(sig_token.getCodigo())) //FOLLOW R'
			out.print( 9+" ");
			//LAMBDA
		else 
			new Error(204,al.getLinea()).getError();
	}

	public void U() throws IOException {
		out.print( 10+" ");
		V();
		U2();
	}

	public void U2() throws IOException {
		if(first.first.get("U'").contains(sig_token.getCodigo())) { // tokens +
			out.print( 11+" ");
			empareja(sig_token.getCodigo());
			V();
			U2();
		}
		else if(first.follow.get("U'").contains(sig_token.getCodigo())) //FOLLOW U'
			out.print( 12+" ");
			//LAMBDA
		else
			new Error(205,al.getLinea()).getError();
	}

	public void V() throws IOException {
		if(sig_token.getCodigo()== 1) { //token id
			out.print( 13+" ");
			empareja(sig_token.getCodigo());
			V2();
		}
		else if(sig_token.getCodigo()==16) {
			out.print( 14+" ");
			empareja(sig_token.getCodigo());
			E();
			empareja(17); // token )
		}
		else if(sig_token.getCodigo()==2) {
			out.print( 15+" ");
			empareja(sig_token.getCodigo());
		}
		else if(sig_token.getCodigo()==3) {
			out.print( 16+" ");
			empareja(sig_token.getCodigo());
		}
		else if(sig_token.getCodigo()==18){
			out.print( 17+" ");
			empareja(18);
			empareja(1);
			V2();
		}
		else 
			new Error(206,al.getLinea()).getError();
	}

	public void V2() throws IOException{
		if(sig_token.getCodigo() ==  16) { //token (
			out.print( 18+" ");
			empareja(16);
			L();
			empareja(17); //token )
		}
		else if(first.follow.get("V'").contains(sig_token.getCodigo())) //FOLLOW V'
			out.print( 19+" ");
			//LAMBDA
		else 
			new Error(207,al.getLinea()).getError();
	}

	public void S() throws IOException {
		if(sig_token.getCodigo() == 1) {
			out.print(20 + " ");
			empareja(1);
			S2();
		}
		else if(sig_token.getCodigo() == 14) {
			out.print(21 + " ");
			empareja(14);
			E();
			empareja(19);
		}
		else if(sig_token.getCodigo() == 15) {
			out.print(22 + " ");
			empareja(15);
			empareja(1);
			empareja(19);
		}
		else if(sig_token.getCodigo() == 28) { //token return
			out.print(23 + " ");
			empareja(28);
			X();
			empareja(19);
		}
		else
			new Error(208,al.getLinea()).getError();
	}

	public void S2() throws IOException {

		if(sig_token.getCodigo() == 7) {
			out.print( 24+" ");
			empareja(7);
			E();
			empareja(19);
		}
		else if(sig_token.getCodigo() == 8) {
			out.print( 25+" ");
			empareja(8);
			E();
			empareja(19);	
		}
		else if(sig_token.getCodigo() == 16) {
			out.print( 26+" ");
			empareja(16);
			L();
			empareja(17);
			empareja(19);	
		}else
			out.print( 27+" ");
	}

	public void X() throws IOException {
		if(first.first.get("X").contains(sig_token.getCodigo())) { //tokens ( id ent cad
			out.print( 28+" ");
			E();
		}
		else if(first.follow.get("X").contains(sig_token.getCodigo())) //FOLLOW X
			//LAMBDA
			out.print( 29+" ");
		else 
			new Error(210,al.getLinea()).getError();;
	}

	public void L() throws IOException {
		if(first.first.get("E").contains(sig_token.getCodigo())) {
			out.print( 30+" ");
			E();
			Q();
		}
		else if(first.follow.get("L").contains(sig_token.getCodigo())) //FOLLOW L
			out.print( 31+" ");
		else 
			new Error(211,al.getLinea()).getError();;
	}

	public void Q() throws IOException {
		if(sig_token.getCodigo() == 18) {
			out.print( 32+" ");
			empareja(sig_token.getCodigo());
			E();
			Q();
		}
		else if(first.follow.get("Q").contains(sig_token.getCodigo())) //FOLLOW Q
			out.print( 33+" ");
			//LAMBDA
		else 
			new Error(212,al.getLinea()).getError();
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
			tipo.puttam(64);
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
			E();
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
			E();
			empareja(17);
			empareja(19);
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
		
		/*Introducimos la zona_declarada a false para que se produzca correctamente la suma de parametros*/
		zona_declarada=false;
		A();
		gestorTablas.getSimboloGL(lexemafuncion).setNumPar(num_parametros);
		num_parametros=0;
		zona_declarada=true;
		
		
		empareja(17);
		empareja(20);
		C();
		empareja(21);
		out.print(5555+" ");
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
