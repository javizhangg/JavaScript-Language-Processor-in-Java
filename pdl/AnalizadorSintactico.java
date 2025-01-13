package pdl;

import static pdl.TablasDeSimbolos.gestorTS;

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
	private boolean zona_funcion;
	private boolean returnDetectado;
	
	private int num_parametros;
	private int cont_parametros;


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
		sig_token = al.getToken(false);

		this.gestorTablas = new TablasDeSimbolos(fwTS);
		this.As = new AnalizadorSemantico(gestorTablas);
		num_parametros = 0;
		cont_parametros=1;
		zona_declarada=false;
		zona_parametro = false;
		zona_funcion = false;
		returnDetectado = false;
	}

	public void empareja(int idToken,boolean valorZonaDeclarada) throws IOException {
		if(sig_token.getCodigo()==idToken) {
			sig_token=al.getToken(valorZonaDeclarada);
		}
		else 
			new Error(201,al.getLinea()).getError();;
	}

	//nos serviara para añadir el tiporev en una funcion 
	public void AddFuncionTS(String id, Tipo tipo) {
		if (gestorTablas.getGestorTS().get(0).estaSimbolo(id)) {
			gestorTablas.getGestorTS().get(0).getSimbolo(id).setTipoDev(tipo);
			//Las funciones son de tipo Función
			gestorTablas.getGestorTS().get(0).getSimbolo(id).setTipo(new Tipo("función"));
			simboloDeFuncion = gestorTablas.getFuncion();
			simboloDeFuncion.setEtiqueta("Et"+id+"01");
		}else {
			new Error(312,al.getLinea(),id);
		}
	}
	
	//Funcion para añadir tipo a un id y verifica si existe o no, si no existe se añade y despues de añadirlo se indica el desplazamiento correspondiente
	public void AddTipoTS(String id, Tipo tipo) {
		boolean encontrado = false;
		if (gestorTablas.getGestorTS().containsKey(1)) {
			if (gestorTablas.getGestorTS().get(1).estaSimbolo(id)) {

				//Esto permite añadir los tipos de los parametros pasados a la funcion
				if(zona_parametro) {
					simboloDeFuncion.setTipoParametro(tipo);
				}
				gestorTablas.getGestorTS().get(1).getSimbolo(id).setTipo(tipo);

				//El tamaño está guardado en el tipo
				gestorTablas.getGestorTS().get(1).getSimbolo(id).setDireccionMemoria(despL);
				despL+=tipo.getTam();
				encontrado = true;
			} 
		}
		if (!encontrado && gestorTablas.getGestorTS().get(0).estaSimbolo(id)) {
			gestorTablas.getGestorTS().get(0).getSimbolo(id).setTipo(tipo);

			//El tamaño está guardado en el tipo
			gestorTablas.getGestorTS().get(0).getSimbolo(id).setDireccionMemoria(despG);
			despG+=tipo.getTam();

		} 

	}

	//Devuelve el tipo de una función
	public Tipo BuscaFuncionTipoTS(String id) {
		return gestorTablas.getGestorTS().get(0).getSimbolo(id).GetTipoDev();
	}
	
	//Devuelve el tipo de una función
		public Simbolo BuscaFuncionTS(String id) {
			return gestorTablas.getGestorTS().get(0).getSimbolo(id);
		}
		
	//Devuelve el tipo de una variable
	public Tipo BuscaTipoTS(String id) throws IOException {
		Tipo tipo = new Tipo();
	    if (gestorTablas.getGestorTS().containsKey(1) && gestorTablas.getGestorTS().get(1).estaSimbolo(id) && !gestorTablas.getGestorTS().get(0).estaSimbolo(id)) {
	    	if (gestorTablas.getGestorTS().get(1).getSimbolo(id).getTipo() == null) {
	    		gestorTablas.getGestorTS().get(1).tablaSimbolo.remove(id);
	    		gestorTablas.getGestorTS().get(0).InsertarTS(id);
	            tipo.setTam(1);
	            tipo.setTipo("int");
	            AddTipoTS(id, tipo);
	            tipo = gestorTablas.getGestorTS().get(0).getSimbolo(id).getTipo();
	        }else {
	        	tipo = gestorTablas.getGestorTS().get(1).getSimbolo(id).getTipo();	
	        }
	    } else if (gestorTablas.getGestorTS().get(0).estaSimbolo(id)) {
	        if (gestorTablas.getGestorTS().get(0).getSimbolo(id).getTipo() == null) {
	            tipo.setTam(1);
	            tipo.setTipo("int");
	            AddTipoTS(id, tipo);
	            tipo = gestorTablas.getGestorTS().get(0).getSimbolo(id).getTipo();
	        }else {
	        	tipo = gestorTablas.getGestorTS().get(0).getSimbolo(id).getTipo();
	        }
	    } else {
	    	new Error(311,al.getLinea(),id);
	    	tipo.setTipo("error");
	    }
	    return tipo;
	}
	public Tipo ExistiaTipoTS(String id) {
		Tipo tipo = new Tipo();
		if (gestorTablas.getGestorTS().containsKey(1) && gestorTablas.getGestorTS().get(1).estaSimbolo(id)) {
			tipo = gestorTablas.getGestorTS().get(1).getSimbolo(id).getTipo();
		} else if (gestorTablas.getGestorTS().get(0).estaSimbolo(id)) {
			tipo = gestorTablas.getGestorTS().get(0).getSimbolo(id).getTipo();
		} else {
			return null;
		}
		return tipo;
	}

//	/* 
//	 * La función P2 se encarga de inicializar el análisis del programa. 
//	 * 1. Crea la tabla global de símbolos llamando a `CrearTSGlobal`.
//	 * 2. Inicializa el desplazamiento global (`despG`) a 0, utilizado para asignar direcciones de memoria a las variables globales.
//	 * 3. Llama a la función `P`, que procesa las reglas del programa según la gramática definida.
//	 * 4. Finalmente, imprime la tabla de símbolos global con `LiberaTablaTS(0)`.
//	 */
//	public void P2() throws IOException {
//		gestorTablas.CrearTSGlobal();
//		despG=0;
//		P();
//		gestorTablas.LiberaTablaTS(0);
//	}
	/* 
	 * La función P analiza las reglas principales del programa: 
	 * 1. Si el token actual pertenece a la regla `B`, procesa `B` y llama recursivamente a `P`.
	 * 2. Si pertenece a la regla `F`, procesa `F` y llama recursivamente a `P`.
	 * 3. Si encuentra el token 29, termina la recursión (lambda).
	 * 4. Si no coincide con ninguna regla, lanza un error.
	 */
	public void P() throws IOException {
		if(!gestorTablas.getGestorTS().containsKey(0)) {
		gestorTablas.CrearTSGlobal();
		despG=0;
		}
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
			gestorTablas.LiberaTablaTS(0);
		}
		else {
			new Error(202,al.getLinea()).getError();
		}

	}
	
	/* 
	 * La función E analiza expresiones según la gramática:
	 * 1. Llama a la función R para obtener el tipo de la subexpresión inicial.
	 * 2. Llama a E2, pasando el tipo heredado de R.
	 * 3. Si el tipo devuelto por E2 no es "error", lo retorna.
	 * 4. Si hay un error de tipo en la expresión, lanza un error y retorna "error".
	 */
	public Tipo E() throws IOException {
		Tipo tipo =new Tipo();
		out.print(4 + " ");
		Tipo R_tipo = R();
		Tipo E2_tipo = E2(R_tipo);
		if(!E2_tipo.getTipo().equals("error")){
			return E2_tipo;
		}else {
			tipo.setTipo("error");
			return tipo;
		}
	}

	/* 
	 * La función E2 analiza las expresiones continuadas (`E'`):
	 * 1. Si el token actual pertenece a `E'`:
	 *    - Consume el token con `empareja`.
	 *    - Llama a R para obtener el tipo de la subexpresión derecha.
	 *    - Llama recursivamente a E2 para procesar el resto.
	 *    - Verifica que el tipo heredado sea compatible con el tipo retornado por R.
	 *    - Si son compatibles, establece el tipo como "boolean" y lo retorna.
	 *    - Si no son compatibles, lanza un error y retorna "error".
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `E'`, retorna el tipo heredado (lambda).
	 * 3. Si el token no pertenece a `E'` ni a su FOLLOW, lanza un error y retorna "error".
	 */
	public Tipo E2(Tipo tipoHeredado) throws IOException {
		Tipo tipo = new Tipo();
		if(first.first.get("E'").contains(sig_token.getCodigo())) {
			out.print(5 + " ");
			empareja(sig_token.getCodigo(),zona_declarada);
			Tipo R_tipo = R();
			Tipo E2_tipo = E2(R_tipo);
			if (tipoHeredado.getTipo().equals(R_tipo.getTipo())) {
				tipo.setTam(1);
				tipo.setTipo("boolean");
				return tipo;
			} else {
				tipo.setTipo("error");
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
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función R analiza las reglas relacionadas con expresiones relacionales:
	 * 1. Llama a la función U para procesar la primera parte de la expresión.
	 * 2. Llama a R2, pasando el tipo heredado de U.
	 * 3. Si el tipo devuelto por R2 no es "error", lo retorna.
	 * 4. Si hay un error en la expresión relacional, lanza un error y retorna "error".
	 */
	public Tipo R() throws IOException {
		Tipo tipo=new Tipo();
		out.print( 7+" ");
		Tipo U_tipo = U();
		Tipo R2_tipo = R2(U_tipo);
		if(!R2_tipo.getTipo().equals("error")) {
			return R2_tipo;
		}else {
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función R2 analiza las reglas para continuar expresiones relacionales (`R'`):
	 * 1. Si el token actual pertenece a `R'`:
	 *    - Consume el token con `empareja`.
	 *    - Llama a U para procesar la parte derecha de la expresión.
	 *    - Llama recursivamente a R2 para procesar el resto.
	 *    - Verifica que el tipo heredado sea compatible con el tipo devuelto por U.
	 *    - Si son compatibles, retorna "boolean".
	 *    - Si no son compatibles, lanza un error y retorna "error".
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `R'`, retorna el tipo heredado (lambda).
	 * 3. Si el token no pertenece a `R'` ni a su FOLLOW, lanza un error y retorna "error".
	 */
	public Tipo R2(Tipo tipoHeredado) throws IOException {
		Tipo tipo =new Tipo();
		if(first.first.get("R'").contains(sig_token.getCodigo())) {
			out.print( 8+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			Tipo U_tipo = U();
			Tipo R2_tipo = R2(U_tipo);
			if (tipoHeredado.getTipo().equals(U_tipo.getTipo())) {
				tipo.setTam(1);
				tipo.setTipo("boolean");
				return tipo;
			} else {
				tipo.setTipo("error");
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
			new Error(203,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}

	/* 
	 * La función U analiza expresiones aritméticas o unitarias:
	 * 1. Llama a la función V para procesar la primera parte de la expresión.
	 * 2. Llama a U2, pasando el tipo heredado de V.
	 * 3. Si el tipo devuelto por U2 no es "error", lo retorna.
	 * 4. Si hay un error en la expresión, lanza un error y retorna "error".
	 */
	public Tipo U() throws IOException {
		Tipo tipo=new Tipo();
		out.print( 10+" ");
		Tipo V_tipo = V();
		Tipo U2_tipo = U2(V_tipo);
		if(!U2_tipo.getTipo().equals("error")) {
			return U2_tipo;
		}else{
			tipo.setTipo("error");
			return tipo;
		}

	}
	
	/* 
	 * La función U2 analiza expresiones continuadas (`U'`):
	 * 1. Si el token actual pertenece a `U'` (como operadores +):
	 *    - Consume el token con `empareja`.
	 *    - Llama a V para procesar la siguiente parte de la expresión.
	 *    - Llama recursivamente a U2 para procesar el resto.
	 *    - Verifica que el tipo heredado sea compatible con el tipo devuelto por V.
	 *    - Si son compatibles, establece el tipo como "int" y lo retorna.
	 *    - Si no son compatibles, lanza un error y retorna "error".
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `U'`, retorna el tipo heredado (lambda).
	 * 3. Si el token no pertenece a `U'` ni a su FOLLOW, lanza un error y retorna "error".
	 */
	public Tipo U2(Tipo tipoHeredado) throws IOException {
		Tipo tipo =new Tipo ();
		if(first.first.get("U'").contains(sig_token.getCodigo())) { // tokens +
			out.print( 11+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			Tipo V_tipo = V();
			Tipo U2_tipo = U2(V_tipo);
			if (tipoHeredado.getTipo().equals(V_tipo.getTipo())) {
				tipo.setTam(1);
			    tipo.setTipo("int");
				return tipo;
			} else {
				tipo.setTipo("error");
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
			new Error(203,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función V analiza valores individuales o expresiones simples:
	 * 1. Si el token es un identificador (`id`):
	 *    - Consume el token.
	 *    - Llama a V2 para procesar cualquier extensión del identificador.
	 *    - Retorna el tipo procesado por V2.
	 * 2. Si el token es un paréntesis de apertura `(`:
	 *    - Consume el token.
	 *    - Llama a E para evaluar la expresión dentro de los paréntesis.
	 *    - Consume el paréntesis de cierre `)` y retorna el tipo de E.
	 * 3. Si el token es un entero (`2`):
	 *    - Consume el token, establece el tipo como `int` y retorna.
	 * 4. Si el token es una cadena (`3`):
	 *    - Consume el token, establece el tipo como `string` y retorna.
	 * 5. Si el token es un operador específico (`18`):
	 *    - Consume el token y el siguiente identificador.
	 *    - Llama a V2 para procesar cualquier extensión del identificador.
	 *    - Retorna el tipo procesado por V2.
	 * 6. Si ningún caso coincide, lanza un error y retorna "error".
	 */
	public Tipo V() throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo()== 1) { //token id
			out.print( 13+" ");
			String id = sig_token.getAtributo();
			empareja(sig_token.getCodigo(),zona_declarada);
			Tipo V2_tipo = V2(id);
			return V2_tipo;
		}
		else if(sig_token.getCodigo()==16) {
			out.print( 14+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			Tipo E_tipo = E();
			empareja(17,zona_declarada); // token )
			return E_tipo;
		}
		else if(sig_token.getCodigo()==2) {
			out.print( 15+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			tipo.setTam(1);
			tipo.setTipo("int");
			return tipo;
		}
		else if(sig_token.getCodigo()==3) {
			out.print( 16+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			tipo.setTam(64);
			tipo.setTipo("string");
			return tipo;
		}
		else if(sig_token.getCodigo()==18){
			out.print( 17+" ");
			empareja(18,zona_declarada);
			String id = sig_token.getAtributo();
			empareja(1,zona_declarada);
			Tipo V2_tipo  =V2(id);
			return V2_tipo;
		}
		else { 
			new Error(203,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función V2 analiza extensiones de identificadores:
	 * 1. Si el token es un paréntesis de apertura `(`:
	 *    - Consume el token.
	 *    - Llama a L para procesar una lista de argumentos.
	 *    - Consume el paréntesis de cierre `)`.
	 *    - Si el tipo retornado por L es "error", lo retorna.
	 *    - Si el identificador es de tipo "funcion", obtiene y retorna su tipo de retorno con `BuscaFuncionTipoTS`.
	 *    - Si no es una función, obtiene y retorna su tipo con `BuscaTipoTS`.
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `V'`:
	 *    - Retorna directamente el tipo del identificador obtenido con `BuscaTipoTS`.
	 * 3. Si ningún caso coincide, lanza un error y retorna "error".
	 */
	public Tipo V2(String id) throws IOException{
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() ==  16) { //token (
			out.print( 18+" ");
			empareja(16,zona_declarada);
			Tipo L_tipo = L(BuscaFuncionTS(id));
			empareja(17,zona_declarada); //token )
			if(L_tipo.getTipo().equals("error")) {
				return tipo;
			}
			else if(BuscaTipoTS(id).getTipo().equals("función")){
				tipo = BuscaFuncionTipoTS(id);
			}
			else {
				new Error(308,al.getLinea(),id).getError();
				tipo.setTipo("error");
			}
			return tipo;
		}
		else if(first.follow.get("V'").contains(sig_token.getCodigo())) //FOLLOW V'
		{
			out.print( 19+" ");
			tipo = BuscaTipoTS(id);
			return tipo;
			//LAMBDA
		}
		else {
			new Error(203,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}

	}
	
	/* 
	 * La función S analiza sentencias del programa:
	 * 1. Si el token es un identificador (`id`):
	 *    - Obtiene el identificador.
	 *    - Llama a S2 para procesar la sentencia asociada al identificador.
	 *    - Retorna el tipo procesado por S2.
	 * 2. Si el token es `output` (14):
	 *    - Consume el token.
	 *    - Llama a E para procesar la expresión.
	 *    - Consume el token de cierre (`;`).
	 *    - Si el tipo devuelto por E es "error", lanza un error y retorna "error".
	 *    - De lo contrario, retorna el tipo.
	 * 3. Si el token es `input` (15):
	 *    - Consume el token.
	 *    - Obtiene el identificador.
	 *    - Consume el token de cierre (`;`).
	 *    - Si el tipo del identificador es "error", lanza un error.
	 *    - Retorna el tipo del identificador.
	 * 4. Si el token es `return` (28):
	 *    - Consume el token.
	 *    - Llama a X para procesar el valor de retorno.
	 *    - Consume el token de cierre (`;`).
	 *    - Si el tipo devuelto por X coincide con el tipo de retorno esperado de la función, lo retorna.
	 *    - Si no coinciden, lanza un error y retorna "error".
	 * 5. Si ningún caso coincide, lanza un error y retorna "error".
	 */
	public Tipo S() throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() == 1) {
			out.print(20 + " ");
			String id=sig_token.getAtributo();
			empareja(1,zona_declarada);
			tipo = S2(id);
			return tipo;
		}
		else if(sig_token.getCodigo() == 14) {
			out.print(21 + " ");
			empareja(14,zona_declarada);
			tipo = E();
			if(tipo.getTipo().equals("boolean")  ) {
				new Error(300,al.getLinea()).getError();
				tipo.setTipo("error");
			}
			empareja(19,zona_declarada);
				return tipo;
		}
		else if(sig_token.getCodigo() == 15) {
			out.print(22 + " ");
			empareja(15,zona_declarada);
			String id=sig_token.getAtributo();
			empareja(1,zona_declarada);
			empareja(19,zona_declarada);
			if(BuscaTipoTS(id).getTipo().equals("error") ) {
				new Error(308,al.getLinea()).getError();
				tipo.setTipo("error");
				return tipo;
			}
			else {
				return BuscaTipoTS(id);
			}

		}
		if(sig_token.getCodigo() == 28) { //token return
			out.print(23 + " ");
			empareja(28,zona_declarada);

			Tipo X_tipo = X();

			returnDetectado = true;
			if(X_tipo.getTipo().equals(simboloDeFuncion.GetTipoDev().getTipo())) {
			}
			else {
				new Error(306,al.getLinea()).getError();
				tipo.setTipo("error");
				X_tipo =  tipo;
			}
			empareja(19,zona_declarada);
			return X_tipo;
		}
		else {
			new Error(208,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función S2 analiza sentencias relacionadas con un identificador (`id`):
	 * 1. Si el token es `=` (7):
	 *    - Consume el token.
	 *    - Llama a E para procesar la expresión del lado derecho.
	 *    - Consume el token de cierre (`;`).
	 *    - Verifica que el tipo del identificador sea compatible con el tipo de la expresión.
	 *    - Si no son compatibles, lanza un error y retorna "error".
	 *    - Si son compatibles, retorna el tipo de la expresión.
	 * 2. Si el token es `|=` (8):
	 *    - Consume el token.
	 *    - Llama a E para procesar la expresión del lado derecho.
	 *    - Consume el token de cierre (`;`).
	 *    - Verifica que el tipo del identificador sea compatible con el tipo de la expresión.
	 *    - Si no son compatibles, lanza un error y retorna "error".
	 *    - Si son compatibles, retorna el tipo de la expresión.
	 * 3. Si el token es `(` (16):
	 *    - Consume el token.
	 *    - Llama a L para procesar la lista de argumentos.
	 *    - Consume el token de cierre (`)`).
	 *    - Consume el token de cierre (`;`).
	 *    - Obtiene el tipo de retorno de la función con `BuscaFuncionTipoTS`.
	 *    - Si la función no existe o su tipo es "error", lanza un error y retorna "error".
	 *    - Retorna el tipo de la función.
	 * 4. Si ningún caso coincide, retorna directamente el tipo del identificador con `BuscaTipoTS`.
	 */
	public Tipo S2(String id) throws IOException {
		Tipo tipo =new Tipo();
		if(sig_token.getCodigo() == 7) { //Token =
			out.print( 24+" ");
			empareja(7,zona_declarada);
			Tipo E_tipo = E();
			if(!BuscaTipoTS(id).getTipo().equals(E_tipo.getTipo())) {
				new Error(301,al.getLinea()).getError();
				tipo.setTipo("error");
				E_tipo = tipo;
			}
			empareja(19,zona_declarada);
			return E_tipo;
		}
		else if(sig_token.getCodigo() == 8) { //Token |=
			out.print( 25+" ");
			empareja(8,zona_declarada);
			tipo = E();
			empareja(19,zona_declarada);	
			if(!BuscaTipoTS(id).getTipo().equals(tipo.getTipo())) {
				new Error(302,al.getLinea()).getError();
				tipo.setTipo("error");
				return tipo;
			}
			return tipo;
		}
		else if(sig_token.getCodigo() == 16) {
			out.print( 26+" ");
			empareja(16,zona_declarada);
			Tipo funcion_tipo = BuscaFuncionTipoTS(id); // Obtiene tipo de retorno
			
	        if (funcion_tipo == null || funcion_tipo.getTipo().equals("error")) {
	            new Error(308, al.getLinea()).getError(); // Error si la función no existe
	            tipo.setTipo("error");
	            funcion_tipo= tipo;
	        }
			Tipo L_tipo = L(BuscaFuncionTS(id));
			empareja(17,zona_declarada);
			empareja(19,zona_declarada);
			
	        // Validación adicional si L_tipo no coincide con los parámetros esperados (implementación pendiente)
	        return funcion_tipo;
		}else {
			return BuscaTipoTS(id);
		}
	}
	
	/* 
	 * La función X analiza expresiones opcionales:
	 * 1. Si el token actual pertenece al conjunto FIRST de `X`:
	 *    - Llama a E para evaluar la expresión.
	 *    - Retorna el tipo de la expresión evaluada.
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `X`:
	 *    - Retorna "void" (lambda) indicando que no hay expresión.
	 * 3. Si el token no pertenece ni a FIRST ni a FOLLOW de `X`:
	 *    - Lanza un error y retorna "error".
	 */
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
			tipo.setTipo("void");
			return tipo;
		}
		else {
			new Error(205,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función L analiza listas de expresiones:
	 * 1. Si el token actual pertenece al conjunto FIRST de `L`:
	 *    - Llama a E para evaluar la primera expresión de la lista.
	 *    - Si la evaluación devuelve "error", lanza un error y retorna "error".
	 *    - Llama a Q para procesar el resto de la lista, heredando el tipo de E.
	 *    - Retorna el tipo procesado por Q.
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `L`:
	 *    - Retorna "void" (lambda) indicando que no hay expresiones en la lista.
	 * 3. Si el token no pertenece ni a FIRST ni a FOLLOW de `L`:
	 *    - Lanza un error y retorna "error".
	 */
	public  Tipo L(Simbolo Funcion) throws IOException {
		Tipo tipo = new Tipo();
		if(first.first.get("E").contains(sig_token.getCodigo())) {

			out.print( 30+" ");
			cont_parametros=1;
			Tipo E_tipo = E(); // Evalúa la primera expresión
			if(Funcion.getTipo() == null || Funcion.getTipo().equals("error")) {
			}
			else if(!Funcion.getTipoParametro(cont_parametros).getTipo().equals(E_tipo.getTipo())) {
				new Error(310, al.getLinea(),Funcion.imprimirContenido()).getError();
			}
			Tipo Q_tipo = Q(E_tipo,Funcion); // Procesa el resto de la lista
			if(Funcion.getNumPar()!=cont_parametros && Funcion.getTipo()!=null) {
				new Error(309, al.getLinea(),Funcion.getLexema()).getError();
			}
			cont_parametros=1;
	        if (E_tipo.getTipo().equals("error")) {
	            new Error(311, al.getLinea()).getError(); // Error en la expresión
	            tipo.setTipo("error");
	            Q_tipo =tipo;
	        }
	        return Q_tipo;
		}
		else if(first.follow.get("L").contains(sig_token.getCodigo())) //FOLLOW L
		{
			out.print( 31+" ");
			tipo.setTipo("void");
			return tipo;
		}	
		else {
			new Error(203,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	
	/* 
	 * La función Q analiza el resto de las listas de expresiones:
	 * 1. Si el token actual es una coma (18):
	 *    - Consume el token.
	 *    - Llama a E para evaluar la siguiente expresión en la lista.
	 *    - Si la evaluación devuelve "error", lanza un error y retorna "error".
	 *    - Llama recursivamente a Q para procesar el resto de la lista, heredando el tipo de E.
	 *    - Retorna el tipo procesado por la llamada recursiva.
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `Q`:
	 *    - Retorna directamente el tipo heredado (lambda) indicando que no hay más expresiones.
	 * 3. Si el token no pertenece ni a una coma ni al conjunto FOLLOW de `Q`:
	 *    - Lanza un error y retorna "error".
	 */
	public Tipo  Q(Tipo tipoHeredado,Simbolo Funcion ) throws IOException {
		Tipo tipo=new Tipo();
		if(sig_token.getCodigo() == 18) {
			
			out.print( 32+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			Tipo E_tipo = E();
			cont_parametros++;
			if(Funcion.getTipo() == null || Funcion.getTipo().equals("error") || Funcion.getNumPar()<cont_parametros) {
			}
			else if(!Funcion.getTipoParametro(cont_parametros).getTipo().equals(E_tipo.getTipo())) {
				new Error(310, al.getLinea(), Funcion.getLexema()).getError();
			}
			Tipo Q_tipo = Q(E_tipo,Funcion); // Procesa el resto de la lista
			
			
			if (E_tipo.getTipo().equals("error")) {
	            tipo.setTipo("error");
	            Q_tipo= tipo;
	        }
	        
	        return Q_tipo;
		}
		else if(first.follow.get("Q").contains(sig_token.getCodigo())) //FOLLOW Q
		{
			out.print( 33+" ");
			return tipoHeredado;
			//LAMBDA

		}
		else {
			new Error(206,al.getLinea()).getError();
			tipo.setTipo("error");
			return tipo;
		}
	}
	
	/* 
	 * La función T analiza tipos de datos:
	 * 1. Si el token actual es `int` (10):
	 *    - Consume el token.
	 *    - Establece el tipo como "int" y su tamaño como 2.
	 * 2. Si el token actual es `boolean` (11):
	 *    - Consume el token.
	 *    - Establece el tipo como "boolean" y su tamaño como 2.
	 * 3. Si el token actual es `string` (12):
	 *    - Consume el token.
	 *    - Establece el tipo como "string" y su tamaño como 128.
	 * 4. Si el token no coincide con ningún tipo válido:
	 *    - Lanza un error y establece el tipo como "error".
	 * 5. Retorna el tipo procesado.
	 */
	public Tipo T() throws IOException {  // ;tipo
		Tipo tipo = new Tipo();
		if(sig_token.getCodigo() == 10) { //token int 
			out.print( 34+" ");
			empareja(10, zona_declarada);	
			tipo.setTipo("int");
			tipo.setTam(1);
		}
		else if (sig_token.getCodigo() == 11) { //token boolean 
			out.print( 35+" ");
			empareja(11,zona_declarada);
			tipo.setTipo("boolean");
			tipo.setTam(1);
		}
		else if(sig_token.getCodigo() == 12) { //token string 
			out.print( 36+" ");
			empareja(12,zona_declarada);
			tipo.setTipo("string");
			tipo.setTam(64);
		}
		else {
			new Error(207,al.getLinea()).getError();
			tipo.setTipo("error");
		}

		return tipo;
	}
	
	/* 
	 * La función A analiza declaraciones de argumentos o tipos:
	 * 1. Si el token actual pertenece al conjunto FIRST de `T` (tipos):
	 *    - Llama a T para procesar el tipo.
	 *    - Si está en una zona de declaración, incrementa el contador de parámetros (`num_parametros`).
	 *    - Agrega el tipo del identificador a la tabla de símbolos con `AddTipoTS`.
	 *    - Consume el identificador (`id`).
	 *    - Llama a K para procesar argumentos adicionales, heredando el tipo actual.
	 * 2. Si el token actual es `void` (13):
	 *    - Consume el token.
	 *    - Establece el tipo como "void" y su tamaño como 0.
	 * 3. Si el token no coincide con ningún caso válido:
	 *    - Lanza un error y establece el tipo como "error".
	 * 4. Retorna el tipo procesado.
	 */
	public Tipo A() throws IOException { // ; tipo
		Tipo tipo = new Tipo(); 
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			out.print( 37+" ");
			tipo = T();

			if(zona_declarada) {
				num_parametros+=1;
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1,zona_declarada);
			tipo = K();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print( 38+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			tipo.setTipo("void");
			tipo.setTam(0);
		}
		else {
			new Error(208,al.getLinea()).getError();
			tipo.setTipo("error");
		}

		return tipo;
	}
	
	/* 
	 * La función B analiza bloques o declaraciones dentro del programa:
	 * 1. Si el token es `if` (22):
	 *    - Consume el token.
	 *    - Consume el paréntesis de apertura `(`.
	 *    - Llama a E para evaluar la condición del `if`.
	 *    - Verifica que el tipo de la condición sea "boolean". Si no, lanza un error.
	 *    - Consume el paréntesis de cierre `)` y procesa la sentencia asociada con `S`.
	 * 2. Si el token pertenece al conjunto FIRST de `S` (sentencias):
	 *    - Llama a S para procesar la sentencia.
	 * 3. Si el token es `var` (9):
	 *    - Establece que está en una zona de declaración (`zona_declarada = true`).
	 *    - Consume el token.
	 *    - Llama a T para procesar el tipo de la variable.
	 *    - Verifica si el identificador ya existe en la tabla de símbolos. Si existe, lanza un error.
	 *    - Agrega el tipo del identificador a la tabla de símbolos con `AddTipoTS`.
	 *    - Consume el identificador y el token de cierre (`;`).
	 *    - Marca el final de la zona de declaración (`zona_declarada = false`).
	 * 4. Si el token es `do` (25):
	 *    - Consume el token y el delimitador de bloque `{`.
	 *    - Llama a C para procesar las sentencias dentro del bloque.
	 *    - Consume el delimitador de cierre `}`.
	 *    - Procesa la condición con `E` y verifica que sea "boolean". Si no, lanza un error.
	 * 5. Si ningún caso coincide:
	 *    - Establece el tipo como "error" y lanza un error.
	 * 6. Retorna el tipo procesado.
	 */
	public Tipo B() throws IOException {
		Tipo tipo = new Tipo(); 
		if(sig_token.getCodigo() == 22) { //token if

			out.print( 39+" ");
			empareja(22,zona_declarada);
			empareja(16,zona_declarada);
			Tipo E_tipo = E();
			if (!E_tipo.getTipo().equals("boolean")) {
				tipo.setTipo("error");
				new Error(303, al.getLinea()).getError();
			}
			empareja(17,zona_declarada);
			tipo = S();
		}
		else if(first.first.get("S").contains(sig_token.getCodigo())) { //tokens id output input return
			out.print( 40+" ");
			tipo = S();
			
		}
		else if(sig_token.getCodigo() == 9) { //token var
			zona_declarada = true;
			out.print( 41+" ");
			empareja(9,zona_declarada);
			tipo = T();
			if(ExistiaTipoTS(sig_token.getAtributo())!=null) {
				new Error(305, al.getLinea()).getError();
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1,zona_declarada);
			empareja(19,zona_declarada);
			zona_declarada = false;
		}
		else if(sig_token.getCodigo() == 25) { //token do
			out.print( 42+" ");
			empareja(25,zona_declarada);
			empareja(20,zona_declarada);
			C();
			empareja(21,zona_declarada);
			empareja(26,zona_declarada);
			empareja(16,zona_declarada);
			tipo = E();
			if (!tipo.getTipo().equals("boolean")) {
				tipo.setTipo("error");
				new Error(304, al.getLinea()).getError();
			}
			empareja(17,zona_declarada);
			empareja(19,zona_declarada);
		}
		else {
			tipo.setTipo("error");
			new Error(209,al.getLinea()).getError();
		}
		
		return tipo;
	}
	
	/* 
	 * La función C analiza conjuntos de bloques o sentencias:
	 * 1. Si el token actual pertenece al conjunto FIRST de `B` (bloques):
	 *    - Llama a B para procesar el bloque actual.
	 *    - Llama recursivamente a C para procesar el siguiente bloque o sentencia.
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `C`:
	 *    - Realiza una acción lambda (no hace nada) y continúa.
	 * 3. Si el token no pertenece ni a FIRST ni a FOLLOW de `C`:
	 *    - Si el token es un delimitador inesperado (`27`), lanza un error específico.
	 *    - Si no, lanza un error genérico indicando que el token no pertenece a `C`.
	 * 4. Retorna el tipo procesado.
	 */
	public Tipo C() throws IOException {
		Tipo tipo = new Tipo();
		if(first.first.get("B").contains(sig_token.getCodigo())) {
			out.print(43+ " ");
			tipo = B();
			C();
		}
		else if(first.follow.get("C").contains(sig_token.getCodigo())) //FOLLOW C
			out.print(44+" ");
		//LAMBDA
		else {
			if(sig_token.getCodigo()==27) {
				tipo.setTipo("error");
				new Error(213,al.getLinea()).getError();
			}
			else {
				tipo.setTipo("error");
				new Error(210,al.getLinea()).getError();
			}
		}

		return tipo;
	}
	
	/* 
	 * La función F analiza declaraciones de funciones:
	 * 1. Establece que está en una zona de declaración (`zona_declarada = true`).
	 * 2. Consume el token correspondiente a `function` (27).
	 * 3. Llama a H para procesar el tipo de retorno de la función.
	 * 4. Obtiene el nombre de la función y lo agrega a la tabla de símbolos global con `AddFuncionTS`.
	 * 5. Si la función no es `void`, activa la bandera `zona_funcion` para indicar que requiere un `return`.
	 * 6. Consume el identificador (nombre de la función).
	 * 7. Crea una nueva tabla local para los parámetros y las variables locales de la función.
	 * 8. Consume el paréntesis de apertura `(`.
	 * 9. Llama a A para procesar los parámetros de la función.
	 * 10. Cierra la zona de parámetros y actualiza el número de parámetros en la tabla de símbolos.
	 * 11. Consume el paréntesis de cierre `)` y el delimitador de bloque `{`.
	 * 12. Llama a C para procesar el cuerpo de la función.
	 * 13. Consume el delimitador de cierre `}`.
	 * 14. Imprime la tabla de símbolos local de la función.
	 */
	public void F() throws IOException {
		Tipo tipo =new Tipo();
		zona_declarada=true;
		String lexemafuncion;
		out.print( 45+" ");
		empareja(27,zona_declarada);
		tipo = H();
		lexemafuncion=sig_token.getAtributo();
		if(gestorTablas.gestorTS.get(0).getSimbolo(lexemafuncion).getTipo()!=null) {
			new Error(313, al.getLinea(), BuscaFuncionTS(lexemafuncion).getLexema()).getError();
		}
		AddFuncionTS(lexemafuncion,tipo);
		empareja(1,zona_declarada);
		
		//Una funcion que no es void necesita hacer un return
		if(!simboloDeFuncion.GetTipoDev().getTipo().equals("void")) {
			zona_funcion = true;
		}
		
		gestorTablas.CrearTSLocal();
		despL = 0;
		empareja(16,zona_declarada);

		zona_parametro = true;
		A();
		zona_parametro = false;
		zona_declarada=false;
		gestorTablas.getSimboloGL(lexemafuncion).setNumPar(num_parametros);
		num_parametros=0;


		empareja(17,zona_declarada);
		empareja(20,zona_declarada);
		C();
		empareja(21,zona_declarada);
		
		gestorTablas.LiberaTablaTS(1);
	}
	
	/* 
	 * La función H analiza el tipo de retorno de una función:
	 * 1. Si el token actual pertenece al conjunto FIRST de `T` (tipos):
	 *    - Llama a T para procesar el tipo de retorno.
	 * 2. Si el token actual es `void` (13):
	 *    - Consume el token y establece el tipo como "void".
	 * 3. Si el token no coincide con ningún caso válido:
	 *    - Lanza un error y establece el tipo como "error".
	 * 4. Retorna el tipo procesado.
	 */
	public Tipo H() throws IOException { //; tipo
		Tipo tipo =new Tipo ();
		if(first.first.get("T").contains(sig_token.getCodigo())) {
			out.print(46+" ");
			tipo=T();
		}
		else if(sig_token.getCodigo() == 13) {
			out.print(47+" ");
			empareja(13,zona_declarada);
			tipo.setTipo("void");
		}
		else {
			new Error(211,al.getLinea()).getError();
			tipo.setTipo("error");
		}

		return tipo;
	}
	
	/* 
	 * La función K analiza listas de parámetros adicionales en declaraciones de funciones:
	 * 1. Si el token actual es una coma (18):
	 *    - Consume el token.
	 *    - Llama a T para procesar el tipo del siguiente parámetro.
	 *    - Si está en una zona de declaración, incrementa el contador de parámetros (`num_parametros`).
	 *    - Agrega el tipo del parámetro a la tabla de símbolos con `AddTipoTS`.
	 *    - Consume el identificador (`id`) del parámetro.
	 *    - Llama recursivamente a K para procesar parámetros adicionales.
	 * 2. Si el token actual pertenece al conjunto FOLLOW de `K` (como el paréntesis de cierre `)`):
	 *    - Realiza una acción lambda (no hace nada).
	 * 3. Si el token no pertenece ni a una coma ni al conjunto FOLLOW de `K`:
	 *    - Lanza un error y establece el tipo como "error".
	 * 4. Retorna el tipo procesado.
	 */
	public Tipo K() throws IOException {
		Tipo tipo = new Tipo ();
		if(sig_token.getCodigo() == 18) {
			out.print(48+" ");
			empareja(sig_token.getCodigo(),zona_declarada);
			tipo = T();
			if(zona_declarada) {
				num_parametros+=1;
			}
			AddTipoTS(sig_token.getAtributo(),tipo);
			empareja(1,zona_declarada);
			tipo = K();
		}
		else if(first.follow.get("K").contains(sig_token.getCodigo())) //FOLLOW K token )
			out.print(49+" ");
		else {
			new Error(212,al.getLinea()).getError();
			tipo.setTipo("error");
		}
		return tipo;
	}

	public static void main(String[] args) throws IOException {
		AnalizadorSintactico as = new AnalizadorSintactico();
		as.P();

		as.out.flush();
		as.out.close(); 
		as.fwParse.close();
		as.fwTS.close();
		as.al.fwTokens.close();
		as.al.fwTS.close();
	}
}
