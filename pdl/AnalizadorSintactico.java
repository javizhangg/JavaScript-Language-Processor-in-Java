package pdl;

import java.io.IOException;

public class AnalizadorSintactico {
	Token sig_token;
	AnalizadorLexico An;
	First first = new First();
	public AnalizadorSintactico() throws IOException{
		this.An=new AnalizadorLexico();
		sig_token = An.getToken();
	}
	public void empareja(int idToken) throws IOException {
		if(sig_token.getCodigo()==idToken) {
			sig_token=An.getToken();
			System.out.println("token siguiente " + sig_token.getCodigo());
		}else {
			System.out.println("error en emparejar");
		}
	}

	public void P() throws IOException {
		if(first.first.get("B").contains(sig_token.getCodigo())) {
			System.out.println(1);
			B();
			P();
		}
		else if(first.first.get("F").contains(sig_token.getCodigo())) {
			System.out.println(2);
			F();
			P();
		}
		else if(sig_token.getCodigo() == 26) {
			System.out.println(3);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void E() throws IOException {
		System.out.println(4);
		R();
		E2();
	}

	public void E2() throws IOException {
		if(first.first.get("E'").contains(sig_token.getCodigo())) {
			System.out.println(5);
			empareja(sig_token.getCodigo());
			R();
			E2();
		}
		else if(first.follow.get("E'").contains(sig_token.getCodigo())) //FOLLOW E'
		{
			System.out.println(6);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void R() throws IOException {
		System.out.println(7);
		U();
		R2();
	}

	public void R2() throws IOException {
		if(first.first.get("R'").contains(sig_token.getCodigo())) {
			System.out.println(8);
			empareja(sig_token.getCodigo());
			U();
			R2();
		}
		else if(first.follow.get("R'").contains(sig_token.getCodigo())) //FOLLOW R'
		{
			System.out.println(9);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void U() throws IOException {
		System.out.println(10);
		V();
		U2();
	}

	public void U2() throws IOException {
		if(first.first.get("U'").contains(sig_token.getCodigo())) {
			System.out.println(11);
			empareja(sig_token.getCodigo());
			V();
			U2();
		}
		else if(first.follow.get("U'").contains(sig_token.getCodigo())) //FOLLOW U'
		{
			System.out.println(12);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void V() throws IOException {
		System.out.println("sig " + sig_token.getCodigo());
		if(sig_token.getCodigo()== 1) {
			System.out.println(13);
			empareja(sig_token.getCodigo());
			V2();
		}
		else if(sig_token.getCodigo()==16) {
			System.out.println(14);
			empareja(sig_token.getCodigo());
			E();
			empareja(17); // token )
		}
		else if(sig_token.getCodigo()==2) {
			System.out.println(15);
			empareja(sig_token.getCodigo());
		}
		else if(sig_token.getCodigo()==3) {
			System.out.println(16);
			empareja(sig_token.getCodigo());
		}
	}

	public void V2() throws IOException{
		if(sig_token.getCodigo() ==  16) {
			System.out.println(17);
			empareja(sig_token.getCodigo());
			C();
			empareja(17); //token )
		}
		else if(first.follow.get("V'").contains(sig_token.getCodigo())) //FOLLOW V'
		{
			System.out.println(18);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void S() throws IOException {
		if(sig_token.getCodigo() == 1) {
			System.out.println(19);
			empareja(sig_token.getCodigo());
			S2();
		}
		else if(sig_token.getCodigo() == 14) {
			System.out.println(20);
			empareja(sig_token.getCodigo());
			E();
			empareja(19);
		}
		else if(sig_token.getCodigo() == 15) {
			System.out.println(21);
			empareja(sig_token.getCodigo());
			empareja(1);
			empareja(19);
		}
		else if(sig_token.getCodigo() == 28) {
			System.out.println(22);
			empareja(sig_token.getCodigo());
			X();
			empareja(19);
		}
	}

	public void S2() throws IOException {
		if(sig_token.getCodigo() == 7) {
			System.out.println(23);
			empareja(sig_token.getCodigo());
			E();
			empareja(19);
		}
		else if(sig_token.getCodigo() == 8) {
			System.out.println(24);
			empareja(sig_token.getCodigo());
			E();
			empareja(19);	
		}
		else if(sig_token.getCodigo() == 16) {
			System.out.println(25);
			empareja(sig_token.getCodigo());
			C();
			empareja(17);
			empareja(19);
		}
	}

	public void X() throws IOException {
		if(first.first.get("X").contains(sig_token.getCodigo())) {
			System.out.println(26);
			E();
		}
		else if(first.follow.get("X").contains(sig_token.getCodigo())) //FOLLOW X
		{
			//LAMBDA
			System.out.println(27);
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void L() throws IOException {
		if(first.first.get("E").contains(sig_token.getCodigo())) {
			System.out.println(28);
			E();
			Q();
		}
		else if(first.follow.get("L").contains(sig_token.getCodigo())) //FOLLOW L
		{
			System.out.println(29);
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void Q() throws IOException {
		if(sig_token.getCodigo() == 18) {
			System.out.println(30);
			empareja(sig_token.getCodigo());
			E();
			Q();
		}
		else if(first.follow.get("Q").contains(sig_token.getCodigo())) //FOLLOW Q
		{
			System.out.println(31);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void T() throws IOException {
		if(sig_token.getCodigo() == 10) {
			System.out.println(32);
			empareja(10);	
		}
		else if (sig_token.getCodigo() == 11) {
			System.out.println(33);
			empareja(11);
		}
		else if(sig_token.getCodigo() == 12) {
			System.out.println(34);
			empareja(12);
		}
	}

	public void A() throws IOException {
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			System.out.println(35);
			empareja(sig_token.getCodigo());
			T();
			empareja(1);
			K();
		}
		else if(sig_token.getCodigo() == 13) {
			System.out.println(36);
			empareja(sig_token.getCodigo());
		}
	}

	public void B() throws IOException {
		if(sig_token.getCodigo() == 22) {
			System.out.println(37);
			empareja(22);
			empareja(16);
			E();
			empareja(17);
			S();
		}
		else if(first.first.get("S").contains(sig_token.getCodigo())) {
			System.out.println(38);
			S();
		}
		else if(sig_token.getCodigo() == 9) {
			System.out.println(39);
			empareja(sig_token.getCodigo());
			T();
			empareja(1);
			empareja(19);
		}
		else if(sig_token.getCodigo() == 25) {
			System.out.println(40);
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
			System.out.println(41);
			B();
			C();
		}
		else if(first.follow.get("C").contains(sig_token.getCodigo())) //FOLLOW C
		{
			System.out.println(42);
			//LAMBDA
		}
		else {
			System.out.println("Error en la producción...");
		}
	}

	public void F() throws IOException {
		System.out.println(43);
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
			System.out.println(44);
			T();
		}
		else if(sig_token.getCodigo() == 13) {
			System.out.println(45);
			empareja(13);
		}
	}
	
	public void K() throws IOException {
		if(sig_token.getCodigo() == 18) {
			System.out.println(46);
			empareja(sig_token.getCodigo());
			T();
			empareja(1);
			K();
		}
		else if(first.follow.get("K").contains(sig_token.getCodigo())) //FOLLOW K
		{
			System.out.println(47);
		}
		else {
			System.out.println("Error en la producción...");
		}

	}
	
	public static void main(String[] args) throws IOException {
		AnalizadorSintactico an = new AnalizadorSintactico();
		an.P();
	}

}
