package pdl123.pdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AnalizadorSintactico {
	private File archivoSalidaParse;
	public static FileWriter fwParse;
	BufferedWriter bw;
	Token sig_token; 
	AnalizadorLexico al;
	PrintWriter out;
	First first = new First();
	public AnalizadorSintactico() throws IOException{
			archivoSalidaParse = new File("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\FicheroParse");
//			archivoSalidaParse = new File("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
			try {
				fwParse = new FileWriter("C:\\Users\\xiaol\\eclipse-workspace\\pdl\\src\\pdl123\\pdl\\FicheroParse");
//				fwParse = new FileWriter("C:\\Users\\javi2\\eclipse-workspace\\pdl\\src\\pdl\\FicheroDeTS");
		         bw=new BufferedWriter(fwParse);
		         out = new PrintWriter(bw);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		this.al=new AnalizadorLexico();
		out.print( "Descendente ");
		fwParse.flush();
		sig_token = al.getToken();
//		System.out.println("Estado: " + al.afdtoken.estado +  " Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);
	}
	public void empareja(int idToken) throws IOException {
		if(sig_token.getCodigo()!=29) {
			if(sig_token.getCodigo()==idToken) {
				sig_token=al.getToken();
//				System.out.println("Estado: " + al.afdtoken.estado +  " Leido: " + al.afdtoken.leido + " (" + al.afdtoken.c + ")" +  " esSimbolo: " + al.afdtoken.esSimbolo + " eofLeido: " + al.afdtoken.eofLeido + " ult: " + al.afdtoken.ultimaint);

//				System.out.println("token siguiente " + sig_token.getCodigo());
			}else {
				new Error(201,al.getLinea()).getError();;
			}
		}else {
			System.out.print("ha acabado correctamente");
		}
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
		else if(sig_token.getCodigo() == 29) {
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
		{
			out.print(6 + " ");
			//LAMBDA
		}
		else {
			new Error(203,al.getLinea()).getError();
		}
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
		{
			out.print( 9+" ");
			//LAMBDA
		}
		else {
			new Error(200,al.getLinea()).getError();
		}
	}

	public void U() throws IOException {
		out.print( 10+" ");
		V();
		U2();
	}

	public void U2() throws IOException {
		if(first.first.get("U'").contains(sig_token.getCodigo())) {
			out.print( 11+" ");
			empareja(sig_token.getCodigo());
			V();
			U2();
		}
		else if(first.follow.get("U'").contains(sig_token.getCodigo())) //FOLLOW U'
		{
			out.print( 12+" ");
			//LAMBDA
		}
		else {
			new Error(200,al.getLinea()).getError();
		}
	}

	public void V() throws IOException {
//		System.out.println("sig " + sig_token.getCodigo());
		if(sig_token.getCodigo()== 1) {
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
	}

	public void V2() throws IOException{
		if(sig_token.getCodigo() ==  16) {
			out.print( 17+" ");
			empareja(sig_token.getCodigo());
			C();
			empareja(17); //token )
		}
		else if(first.follow.get("V'").contains(sig_token.getCodigo())) //FOLLOW V'
		{
			out.print( 18+" ");
			//LAMBDA
		}
		else {
			new Error(200,al.getLinea()).getError();
		}
	}

	public void S() throws IOException {
		if(sig_token.getCodigo() == 1) {
			out.print( 19+" ");
			empareja(sig_token.getCodigo());
			S2();
		}
		else if(sig_token.getCodigo() == 14) {
			out.print( 20+" ");
			empareja(sig_token.getCodigo());
			E();
			empareja(19);
		}
		else if(sig_token.getCodigo() == 15) {
			out.print( 21+" ");
			empareja(sig_token.getCodigo());
			empareja(1);
			empareja(19);
		}
		else if(sig_token.getCodigo() == 28) {
			out.print( 22+" ");
			empareja(sig_token.getCodigo());
			X();
			empareja(19);
		}
	}

	public void S2() throws IOException {
		if(sig_token.getCodigo() == 7) {
			out.print( 23+" ");
			empareja(sig_token.getCodigo());
			E();
			empareja(19);
		}
		else if(sig_token.getCodigo() == 8) {
			out.print( 24+" ");
			empareja(sig_token.getCodigo());
			E();
			empareja(19);	
		}
		else if(sig_token.getCodigo() == 16) {
			out.print( 25+" ");
			empareja(sig_token.getCodigo());
			C();
			empareja(17);
			empareja(19);
		}
	}

	public void X() throws IOException {
		if(first.first.get("X").contains(sig_token.getCodigo())) {
			out.print( 26+" ");
			E();
		}
		else if(first.follow.get("X").contains(sig_token.getCodigo())) //FOLLOW X
		{
			//LAMBDA
			out.print( 27+" ");
		}
		else {
			new Error(200,al.getLinea()).getError();;
		}
	}

	public void L() throws IOException {
		if(first.first.get("E").contains(sig_token.getCodigo())) {
			out.print( 28+" ");
			E();
			Q();
		}
		else if(first.follow.get("L").contains(sig_token.getCodigo())) //FOLLOW L
		{
			out.print( 29+" ");
		}
		else {
			new Error(200,al.getLinea()).getError();;
			
		}
	}

	public void Q() throws IOException {
		if(sig_token.getCodigo() == 18) {
			out.print( 30+" ");
			empareja(sig_token.getCodigo());
			E();
			Q();
		}
		else if(first.follow.get("Q").contains(sig_token.getCodigo())) //FOLLOW Q
		{
			out.print( 31+" ");
			//LAMBDA
		}
		else {
			new Error(200,al.getLinea()).getError();
		}
	}

	public void T() throws IOException {
		if(sig_token.getCodigo() == 10) {
			out.print( 32+" ");
			empareja(10);	
		}
		else if (sig_token.getCodigo() == 11) {
			out.print( 33+" ");
			empareja(11);
		}
		else if(sig_token.getCodigo() == 12) {
			out.print( 34+" ");
			empareja(12);
		}
	}

	public void A() throws IOException {
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			out.print( 35+" ");
			empareja(sig_token.getCodigo());
			T();
			empareja(1);
			K();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print( 36+" ");
			empareja(sig_token.getCodigo());
		}
	}

	public void B() throws IOException {
		if(sig_token.getCodigo() == 22) {
			out.print( 37+" ");
			empareja(22);
			empareja(16);
			E();
			empareja(17);
			S();
		}
		else if(first.first.get("S").contains(sig_token.getCodigo())) {
			out.print( 38+" ");
			S();
		}
		else if(sig_token.getCodigo() == 9) {
			out.print( 39+" ");
			empareja(sig_token.getCodigo());
			T();
			empareja(1);
			empareja(19);
		}
		else if(sig_token.getCodigo() == 25) {
			out.print( 40+" ");
			empareja(sig_token.getCodigo());
			empareja(20);
			C();
			empareja(21);
			empareja(26);
			E();
			empareja(17);
			empareja(19);
		}
	}

	public void C() throws IOException {
		if(first.first.get("B").contains(sig_token.getCodigo())) {
			out.print( 41+" ");
			B();
			C();
		}
		else if(first.follow.get("C").contains(sig_token.getCodigo())) //FOLLOW C
		{
			out.print( 42+" ");
			//LAMBDA
		}
		else {
			new Error(200,al.getLinea()).getError();
		}
	}

	public void F() throws IOException {
		out.print( 43+" ");
		empareja(sig_token.getCodigo());
		H();
		empareja(1);
		empareja(16);
		A();
		empareja(17);
		empareja(20);
		C();
		empareja(21);

	}

	public void H() throws IOException {
		if(first.first.get("H").contains(sig_token.getCodigo())) {
			out.print( 44+" ");
			T();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print( 45+" ");
			empareja(13);
		}
	}

	public void K() throws IOException {
		if(sig_token.getCodigo() == 18) {
			out.print( 46+" ");
			empareja(sig_token.getCodigo());
			T();
			empareja(1);
			K();
		}
		else if(first.follow.get("K").contains(sig_token.getCodigo())) //FOLLOW K
		{
			out.print( 47+" ");
		}
		else {
			new Error(200,al.getLinea()).getError();
		}

	}

	public static void main(String[] args) throws IOException {
		AnalizadorSintactico as = new AnalizadorSintactico();
		as.P();

		as.out.flush();
		as.al.fwTokens.close();
		as.al.fwTS.close();
	}

}
