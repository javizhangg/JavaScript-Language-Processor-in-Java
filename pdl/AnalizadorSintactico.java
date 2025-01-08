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
		archivoSalidaParse = new File("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroParse");
		archivoSalidaTS = new File("C:\\Users\\xiaol\\eclipse-workspace\\PDL\\src\\pdl\\FicheroDeTS");
		//		  archivoSalidaParse = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
		//		  archivoSalidaTS = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");

		try {
			fwParse = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
			fwTS = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			//			fwParse = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroParse");
			//			fwTS = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			bw=new BufferedWriter(fwParse);
			out = new PrintWriter(bw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.al=new AnalizadorLexico();
		out.print( "Descendente ");
		fwParse.flush();
		sig_token = al.getToken(false);

		this.gestorTablas = new TablasDeSimbolos(fwTS);
		this.As = new AnalizadorSemantico(gestorTablas);
		num_parametros = 0;
		zona_declarada=false;
		zona_parametro = false;

		//		////System.out.println("Estado: " + al.afdtoken.estado +  " Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);
	}

	public void empareja(int idToken,boolean valorZonaDeclarada) throws IOException {
		if(sig_token.getCodigo()==idToken) {


			sig_token=al.getToken(valorZonaDeclarada);
			//				////System.out.println("Estado: " + al.afdtoken.estado +  " Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);

//			System.out.println("token siguiente " + sig_token.getCodigo());
		}
		else 
			new Error(201,al.getLinea()).getError();;
			//							////System.out.println("Token error :" + sig_token.getCodigo() + " se esperaba: " + idToken + ". Linea: " + al.afdtoken.posicionDeLinea);
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


	//Funcion para añadir tipo a un id y verifica si existe o no, si no existe se añade y despues de añadirlo se indica el desplazamiento correspondiente
	public void AddTipoTS(String id, Tipo tipo) {
		boolean encontrado = false;
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
				encontrado = true;

			} 
//			else {
//				System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla local.");
//			}
		}
		if (!encontrado && gestorTablas.gestorTS.get(0).estaSimbolo(id)) {
			gestorTablas.gestorTS.get(0).getSimbolo(id).setTipo(tipo);

			//El tamaño está guardado en el tipo
			gestorTablas.gestorTS.get(0).getSimbolo(id).setDireccionMemoria(despG);
			despG+=tipo.gettam();
			
		} 
//		else {
//			System.err.println("Error: El símbolo con id '" + id + "' no existe en la tabla global.");
//		}

	}
	
	//Devuelve el tipo de una función
	public Tipo BuscaFuncionTipoTS(String id) {
		return gestorTablas.gestorTS.get(0).getSimbolo(id).GetTipoDev();
	}
	
	//Devuelve el tipo de una variable
	public Tipo BuscaTipoTS(String id) {
		if (gestorTablas.gestorTS.containsKey(1) && gestorTablas.gestorTS.get(1).estaSimbolo(id)) {
			return gestorTablas.gestorTS.get(1).getSimbolo(id).getTipo();
		} else if (gestorTablas.gestorTS.get(0).estaSimbolo(id)) {
			if(gestorTablas.gestorTS.get(0).getSimbolo(id).getTipo() == null ) {
				System.out.println("ESTOY EN BUSCATIPO");
				Tipo tipo = new Tipo();
				tipo.puttam(2);
				tipo.putTipo("int");
				AddTipoTS(id,tipo);
			}
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
		//		Tipo R_tipo = R();
		//      Tipo E2_tipo = E2(R_tipo);

		tipo = R();
		tipo = E2(tipo);
		System.out.println("Soy E()");
		System.out.println(tipo.getTipo());

		//        System.out.print("R: "+R_tipo.getTipo());
		//        System.out.print(" E2: "+E2_tipo.getTipo()+ "\n");
		if(tipo.getTipo().equals("string")) {
			tipo.puttam(128);
//			tipo.putTipo("string");
			return tipo;
		}
		if(tipo.getTipo().equals("int")) {
			          	 tipo.puttam(2);
			//          	 tipo.putTipo("int");
			return tipo;
		}
		if (tipo.getTipo().equals("boolean")) {
			        	tipo.puttam(2);
			//        	tipo.putTipo("boolean");
			return tipo;
		} else {
			new Error(300,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo E2(Tipo tipoHeredado) throws IOException {
		Tipo tipo =new Tipo();
		if(first.first.get("E'").contains(sig_token.getCodigo())) {
			out.print(5 + " ");
			empareja(sig_token.getCodigo(),false);

			//			Tipo R_tipo = R();
			//            Tipo E2_tipo = E2(R_tipo);

			tipo = R();
			tipo = E2(tipo);
			System.out.println("Soy E2()");
			System.out.println(tipo.getTipo());
			//            System.out.println("Soy E2()");
			//            System.out.print("R:"+R_tipo.getTipo());
			//            System.out.print("E2:"+E2_tipo.getTipo()+ "\n");
			if (tipo.getTipo().equals("boolean")) {
				//	        	tipo.puttam(2);
				//	        	tipo.putTipo("boolean");
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
			return tipoHeredado;
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
		//		 Tipo U_tipo = U();
		//	     Tipo R2_tipo = R2(U_tipo);

		tipo = U();
		tipo = R2(tipo);
		System.out.println("Soy R()");
		System.out.println(tipo.getTipo());
		//	     System.out.println("Soy R()");
		//         System.out.print("U: "+U_tipo.getTipo());
		//         System.out.print(" R2: "+R2_tipo.getTipo()+ "\n");
		if(tipo.getTipo().equals("string")) {
			//        	 tipo.puttam(128);
			//        	 tipo.putTipo("string");
			return tipo;
		}
		if (tipo.getTipo().equals("boolean")) {
			//	    	 	tipo.puttam(2);
			//	    	 	tipo.putTipo("boolean");
			return tipo;
		} 
		if (tipo.getTipo().equals("int")) {
			//	    	 	tipo.puttam(2);
			//	    	 	tipo.putTipo("int");
			return tipo;
		} 
		else {
			new Error(302,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}
	}

	public Tipo R2(Tipo tipoHeredado) throws IOException {
		Tipo tipo =new Tipo();
		if(first.first.get("R'").contains(sig_token.getCodigo())) {
			out.print( 8+" ");
			empareja(sig_token.getCodigo(),false);
			//			Tipo U_tipo = U();
			//		    Tipo R2_tipo = R2(U_tipo);

			tipo = U();
			tipo = R2(tipo);
			System.out.println("Soy R2()");
			System.out.println(tipo.getTipo());
			//		    System.out.println("Soy R2()");
			//		    System.out.print("U:"+ U_tipo.getTipo());
			//		    System.out.print("R2:"+R2_tipo.getTipo()+ "\n");
			if (tipo.getTipo().equals("int")) {
				//	    	 	tipo.puttam(2);
				tipo.putTipo("boolean");
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

			return tipoHeredado;
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
		//		Tipo V_tipo = V();
		////System.out.println("Sig token: " + this.sig_token.getCodigo());
		//        Tipo U2_tipo = U2(V_tipo);
		//        System.out.println("Soy U()");
		//        System.out.print("V: "+V_tipo.getTipo());
		//        System.out.print(" U2: "+U2_tipo.getTipo()+ "\n");

		tipo = V();
		tipo = U2(tipo);
		System.out.println("Soy U()");
		System.out.println(tipo.getTipo());
		if(!tipo.getTipo().equals("error")) {
			return tipo;
		}else{
			tipo.putTipo("error");
			new Error(304,al.getLinea()).getError();
			return tipo;
		}

	}

	public Tipo U2(Tipo tipoHeredado) throws IOException {
		Tipo tipo =new Tipo ();
		if(first.first.get("U'").contains(sig_token.getCodigo())) { // tokens +
			out.print( 11+" ");
			empareja(sig_token.getCodigo(),false);
			//			 Tipo V_tipo = V();
			//	         Tipo U2_tipo = U2(V_tipo);
			//	         System.out.println("Soy U2()");
			//	         System.out.print("V: "+V_tipo.getTipo());
			//	         System.out.print(" U2: "+U2_tipo.getTipo()+ "\n");

			tipo = V();
			tipo = U2(tipo);
			System.out.println("Soy U2()");
			System.out.println(tipo.getTipo());
			if (tipo.getTipo().equals("int")) {
				//	             tipo.puttam(2);
				//	             tipo.putTipo("int");
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
			return tipoHeredado;
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
//						System.out.println("Soy V(sadsd): " + id);
			empareja(sig_token.getCodigo(),false);
			return V2(id);
		}
		else if(sig_token.getCodigo()==16) {
			out.print( 14+" ");
			empareja(sig_token.getCodigo(),false);
			tipo = E();
			empareja(17,false); // token )
			//			System.out.println("Soy V(): " + E_tipo.getTipo());
			return tipo;
		}
		else if(sig_token.getCodigo()==2) {
			out.print( 15+" ");
			empareja(sig_token.getCodigo(),false);
			tipo.puttam(2);
			tipo.putTipo("int");
			//			System.out.println("Soy V(): " + tipo.getTipo());
			return tipo;
		}
		else if(sig_token.getCodigo()==3) {
			out.print( 16+" ");
			empareja(sig_token.getCodigo(),false);
			tipo.puttam(128);
			tipo.putTipo("string");
			//			System.out.println("Soy V(): " + tipo.getTipo());
			return tipo;
		}
		else if(sig_token.getCodigo()==18){
			out.print( 17+" ");
			empareja(18,false);
			String id = sig_token.getAtributo();
			empareja(1,false);
			////System.out.println("Soy V(): " + id);
			return V2(id);
		}
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
			empareja(16,false);
			tipo = L();
			empareja(17,false); //token )
			return tipo;
		}
		else if(first.follow.get("V'").contains(sig_token.getCodigo())) //FOLLOW V'
		{
			out.print( 19+" ");
						System.out.println(":: V2 id: " + id +" " + BuscaTipoTS(id) + " " + BuscaTipoTS(id).getTipo()  );
			return BuscaTipoTS(id);
			//LAMBDA
		}
		else {
			new Error(207,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}

	}

	public Tipo S() throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() == 1) {
			out.print(20 + " ");
			String id=sig_token.getAtributo();
			empareja(1,false);
			tipo = S2(id);
			System.out.println("Soy S(): " + tipo.getTipo());
			if(!tipo.getTipo().equals(BuscaTipoTS(id).getTipo())) {
				new Error(306,al.getLinea()).getError();
				tipo.putTipo("error");
				return tipo;
			}
			else {
				return tipo;
			}

		}
		else if(sig_token.getCodigo() == 14) {
			out.print(21 + " ");
			empareja(14,false);
			tipo = E();
			empareja(19,false);
			if(!tipo.getTipo().equals("string")) {
				new Error(307,al.getLinea()).getError();
				tipo.putTipo("error");
				return tipo;
			}
			else {
				return tipo;
			}
		}
		else if(sig_token.getCodigo() == 15) {
			out.print(22 + " ");
			empareja(15,false);
			String id=sig_token.getAtributo();
			empareja(1,false);
			empareja(19,false);
			if(!BuscaTipoTS(id).getTipo().equals("string")) {
				new Error(308,al.getLinea()).getError();
				tipo.putTipo("error");
				return tipo;
			}
			else {
				return tipo;
			}

		}
		else if(sig_token.getCodigo() == 28) { //token return
			out.print(23 + " ");
			empareja(28,false);
			tipo = X();
			empareja(19,false);
			////System.out.println("Xtipo = " + X_tipo.getTipo() + " FuncionTipo = " + simboloDeFuncion.GetTipoDev().getTipo());
			if(tipo.getTipo().equals(simboloDeFuncion.GetTipoDev().getTipo())) {
				return tipo;
			}
			else {
				new Error(316,al.getLinea()).getError();
				tipo.putTipo("error");
				return tipo;
			}
		}
		else {
			new Error(208,al.getLinea()).getError();
			tipo.putTipo("error");
			return tipo;
		}


	}

	public Tipo S2(String id) throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() == 7) { //Token =
			out.print( 24+" ");
			empareja(7,false);
			tipo = E();
			empareja(19,false);
			
			if(!BuscaTipoTS(id).getTipo().equals(tipo.getTipo())) {
				new Error(309,al.getLinea()).getError();
				tipo.putTipo("error");
				return tipo;
			}
			return tipo;
		}
		else if(sig_token.getCodigo() == 8) { //Token |=
			out.print( 25+" ");
			empareja(8,false);
			tipo = E();
			empareja(19,false);	
			if(!BuscaTipoTS(id).getTipo().equals(tipo.getTipo())) {
				new Error(310,al.getLinea()).getError();
				tipo.putTipo("error");
				return tipo;
			}
			return tipo;
		}
		else if(sig_token.getCodigo() == 16) {
			out.print( 26+" ");
			empareja(16,false);
			tipo = L();
			empareja(17,false);
			empareja(19,false);	
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
			tipo = E();
			return tipo;
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
			tipo = E();
			tipo = Q(tipo);
			if(!tipo.getTipo().equals("error")) {
				return tipo;
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

	public Tipo  Q(Tipo tipoHeredado) throws IOException {
		Tipo tipo=new Tipo();
		if(sig_token.getCodigo() == 18) {
			out.print( 32+" ");
			empareja(sig_token.getCodigo(),false);
			tipo = E();
			tipo = Q(tipo);
			if(!tipo.getTipo().equals("error")) {
				return tipo;
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
			empareja(10, zona_declarada);	
			tipo.putTipo("int");
			tipo.puttam(2);
		}
		else if (sig_token.getCodigo() == 11) { //token boolean 
			out.print( 35+" ");
			empareja(11,zona_declarada);
			tipo.putTipo("boolean");
			tipo.puttam(2);
		}
		else if(sig_token.getCodigo() == 12) { //token string 
			out.print( 36+" ");
			empareja(12,zona_declarada);
			tipo.putTipo("string");
			tipo.puttam(128);
		}
		else {
			new Error(213,al.getLinea()).getError();
			tipo.putTipo("error");
		}

		return tipo;
	}

	public Tipo A() throws IOException { // ; tipo
		Tipo tipo = new Tipo(); 
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			out.print( 37+" ");
			//			empareja(sig_token.getCodigo());
			tipo = T();

			if(zona_declarada) {
				num_parametros+=1;
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1,true);
			tipo = K();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print( 38+" ");
			empareja(sig_token.getCodigo(),true);
			tipo.putTipo("void");
			tipo.puttam(0);
		}
		else {
			new Error(214,al.getLinea()).getError();
			tipo.putTipo("error");
		}

		return tipo;
	}

	public void B() throws IOException {
		Tipo tipo = new Tipo(); 
		if(sig_token.getCodigo() == 22) { //token if

			out.print( 39+" ");
			empareja(22,false);
			empareja(16,false);
			tipo = E();
			//			//System.out.println(E_tipo.getTipo());
			if (!tipo.getTipo().equals("boolean")) {
				new Error(313, al.getLinea()).getError();
			}
			empareja(17,false);
			S();
		}
		else if(first.first.get("S").contains(sig_token.getCodigo())) { //tokens id output input return
			out.print( 40+" ");
			S();
		}
		else if(sig_token.getCodigo() == 9) { //token var
			zona_declarada = true;
			out.print( 41+" ");
			empareja(9,true);
			tipo=T();
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1,true);
			empareja(19,true);
			zona_declarada = false;
		}
		else if(sig_token.getCodigo() == 25) { //token do
			out.print( 42+" ");
			empareja(25,false);
			empareja(20,false);
			C();
			empareja(21,false);
			empareja(26,false);
			empareja(16,false);
			tipo = E();
			empareja(17,false);
			empareja(19,false);
			if (tipo.getTipo().equals("boolean")) {
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
		empareja(27,true);
		tipo = H();
		lexemafuncion=sig_token.getAtributo();
		AddFuncionTS(lexemafuncion,tipo);

		empareja(1,true);
		gestorTablas.añadirTablaLocalTS();
		despL = 0;
		empareja(16,true);

		zona_parametro = true;
		A();
		zona_parametro = false;
		zona_declarada=false;
		gestorTablas.getSimboloGL(lexemafuncion).setNumPar(num_parametros);
		num_parametros=0;


		empareja(17,false);
		empareja(20,false);
		C();
		empareja(21,false);

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
			empareja(13,false);
			tipo.putTipo("void");
		}
		else {
			new Error(217,al.getLinea()).getError();
			tipo.putTipo("error");
		}

		return tipo;
	}

	public Tipo K() throws IOException {
		Tipo tipo = new Tipo ();
		if(sig_token.getCodigo() == 18) {
			out.print(48+" ");
			empareja(sig_token.getCodigo(),true);
			tipo = T();
			if(zona_declarada) {
				num_parametros+=1;
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1,true);
			tipo = K();
		}
		else if(first.follow.get("K").contains(sig_token.getCodigo())) //FOLLOW K token )
			out.print(49+" ");
		else {
			new Error(218,al.getLinea()).getError();
			tipo.putTipo("error");
		}
		return tipo;
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
