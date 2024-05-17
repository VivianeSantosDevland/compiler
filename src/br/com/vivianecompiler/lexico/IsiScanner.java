package br.com.vivianecompiler.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import br.com.vivianecompiler.exceptions.IsLexicalException;

public class IsiScanner {

	private char[] content;
	private int estado;
	private int position;
	
	//ler um arquivo e colocar o conteúdo dentro de um array de caracteres
	public IsiScanner (String filename) {
		try {
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
			
			System.out.println("---------------------------");
			System.out.println(txtConteudo);
			System.out.println("---------------------------");
			content = txtConteudo.toCharArray();			
			position = 0;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Token nextToken() {
		Token token = null;
		char currentChar;
		String term = "";
		if(isEOF()) {
			return null;
		}
		estado = 0;
		while(true) {
			currentChar = nextChar();
			
			switch (estado) {
			case 0: 
				if(isChar(currentChar)) {
					term += currentChar;
					estado = 1;
				}else if(isDigit(currentChar)) {
					estado = 3;
					term += currentChar;
				}
				else if(isSpace(currentChar)) {
					estado = 0;
				}else if(isOperator(currentChar)) {
					estado = 5;
				}else {
					
					throw new IsLexicalException("Unrecognized Symbol");
				}
			break;
			
			case 1:
				if(isChar(currentChar)|| isDigit(currentChar)) {
					estado = 1;
					term += currentChar;
				}else if(isSpace(currentChar) || isOperator(currentChar)){
					estado = 2;
					term += currentChar;//
				}else {
					throw new IsLexicalException("Malformad identifier");
				}
				break;
			
			case 2:
				back();
				token = new Token();
				token.setType(Token.TK_IDENTFIER);				
				token.setText(term);
				return token;
				
			case 3:
				if(isDigit(currentChar)) {
					estado = 3;
					term += currentChar;
							
				}else if(!isChar(currentChar)) {
					estado = 4;
				}else {
					throw new IsLexicalException("Malformat Number");
					
				}
			break;
			case 4:
				back();
				token = new Token();
				token.setType(Token.TK_NUMBER);
				token.setText(term);
				return token;
			case 5:
				
				term += currentChar;
				token = new Token();
				token.setType(Token.TK_OPERATOR);
				token.setText(term);
						
			}
		}
		
	}
	
	//identificação e validação de cada caracter
	
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
	
	private boolean isOperator(char c) {
		return c == '>' || c == '<' || c == '=' || c == '!';
	}
	
	private boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
	
	private char nextChar() {
		return content[position++];
	}
	
	private boolean isEOF() {
		return position == content.length;

	}
	
	
	private void back() {
		position--;	
	}
	
	
}
