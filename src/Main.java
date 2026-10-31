import java.io.*;

import java_cup.runtime.Symbol;
   
public class Main
{


	private static String getTokenName(int sym) {
		switch (sym) {
			case TokenNames.PLUS:        return "PLUS";
			case TokenNames.MINUS:       return "MINUS";
			case TokenNames.TIMES:       return "TIMES";
			case TokenNames.DIVIDE:      return "DIVIDE";
			case TokenNames.LPAREN:      return "LPAREN";
			case TokenNames.RPAREN:      return "RPAREN";
			case TokenNames.INT:         return "INT";
			case TokenNames.ID:          return "ID";
			case TokenNames.EQ:          return "EQ";
			case TokenNames.LT:          return "LT";
			case TokenNames.GT:          return "GT";
			case TokenNames.AND:         return "AND";
			case TokenNames.OR:          return "OR";
			case TokenNames.LBRACK:      return "LBRACK";
			case TokenNames.RBRACK:      return "RBRACK";
			case TokenNames.LBRACE:      return "LBRACE";
			case TokenNames.RBRACE:      return "RBRACE";
			case TokenNames.COMMA:       return "COMMA";
			case TokenNames.DOT:         return "DOT";
			case TokenNames.SEMICOLON:   return "SEMICOLON";
			case TokenNames.ASSIGN:      return "ASSIGN";
			case TokenNames.ARRAY:       return "ARRAY";
			case TokenNames.CLASS:       return "CLASS";
			case TokenNames.EXTENDS:     return "EXTENDS";
			case TokenNames.NEW:         return "NEW";
			case TokenNames.NIL:         return "NIL";
			case TokenNames.IF:          return "IF";
			case TokenNames.ELSE:        return "ELSE";
			case TokenNames.WHILE:       return "WHILE";
			case TokenNames.RETURN:      return "RETURN";
			case TokenNames.TYPE_INT:    return "TYPE_INT";
			case TokenNames.TYPE_STRING: return "TYPE_STRING";
			case TokenNames.TYPE_VOID:   return "TYPE_VOID";
			case TokenNames.STRING:      return "STRING";
			default:                     return "ERROR";
		}
	}
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader fileReader;
		PrintWriter fileWriter;
		String inputFileName = argv[0];
		String outputFileName = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			fileReader = new FileReader(inputFileName);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			fileWriter = new PrintWriter(outputFileName);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(fileReader);

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			boolean firstToken = true;
			while (s.sym != TokenNames.EOF)
			{
				/* if we encounter error the output file should contain only the word ERROR */
				if (s.sym == TokenNames.ERROR)
				{
					fileWriter.close();
					fileWriter = new PrintWriter(outputFileName);
					fileWriter.print("ERROR");
					fileWriter.close();
					l.yyclose();
					return;
				}

				String tokenStr = getTokenName(s.sym);
				if (s.sym == TokenNames.INT || s.sym == TokenNames.ID || s.sym == TokenNames.STRING)
				{
					tokenStr += "(" + s.value + ")";
				}
				tokenStr += "[" + l.getLine() + "," + l.getTokenStartPosition() + "]";
				
				if (!firstToken) {
					fileWriter.print("\n");
				}
				firstToken = false;
				fileWriter.print(tokenStr);
				s = l.next_token();
			}
			l.yyclose();
			fileWriter.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


