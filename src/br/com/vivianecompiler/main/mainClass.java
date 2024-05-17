package br.com.vivianecompiler.main;

import br.com.vivianecompiler.exceptions.IsLexicalException;
import br.com.vivianecompiler.lexico.IsiScanner;
import br.com.vivianecompiler.lexico.Token;

public class mainClass {

	public static void main(String[] args) {
		try {
			IsiScanner scan = new IsiScanner("input.isi");
			Token token = null;
			do {
				token = scan.nextToken();		
				
				if(token != null) {
					System.out.println(token);
				}
			}while(token != null);
		} catch (IsLexicalException e) {
			System.out.println("Lexical Error: "+e.getMessage());		
			
				
		}
			catch (Exception e) {
				System.out.println("Generic ERROR");

			}
			
	}

}
