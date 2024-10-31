package pdl;

import java.io.IOException;

public class AnalizadorSintactico {
		Token sig_token;
		AnalizadorLexico An;
		public AnalizadorSintactico() throws IOException{
			this.An=new AnalizadorLexico();
			this.sig_token=An.getToken();
			}
		public void Pareja(Token token) throws IOException {
			if(sig_token==token) {
				sig_token=An.getToken();
			}else {
				System.out.print("error");
			}
		}
		
		public void main(String[] args) throws IOException {
		
		}
		
}
