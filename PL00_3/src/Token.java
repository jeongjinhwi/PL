import java.util.HashMap;
import java.util.Map;

public class Token {
	private final TokenType type;  
	private final String lexme;  
	public enum TokenType {
		INT, 
		ID, QUESTION, 
		TRUE, FALSE, NOT, 
		PLUS, MINUS, TIMES, DIV, 
		LT, GT, EQ, APOSTROPHE, 
		L_PAREN, R_PAREN, 
		DEFINE, LAMBDA, COND, QUOTE, 
		CAR, CDR, CONS, 
		ATOM_Q, NULL_Q, EQ_Q;
		
		static TokenType fromSpecialCharactor(char ch) {
			switch (ch) { // 정규 표현식을 참고하여 ch와 매칭되는 keyword를 반환하는 case문 작성
			case '(':
				return ID;
				
			default:
				throw new IllegalArgumentException("unregistered char: " + ch);
			}
		}
	}

	static Token ofName(String lexme) {  
		TokenType type = KEYWORDS.get(lexme); 
		if ( type != null ) {   
			return new Token(type, lexme); 
			}  
		else if ( lexme.endsWith("?") ) {  
			if ( lexme.substring(0, lexme.length()-1).contains("?") ) {
				throw new ScannerException("invalid ID=" + lexme);   
				}       
			return new Token(TokenType.QUESTION, lexme);  
			}  
		else if ( lexme.contains("?") ) { 
			throw new ScannerException("invalid ID=" + lexme); 
			}
		else {    return new Token(TokenType.ID, lexme);  
		} 
		}  
	Token(TokenType type, String lexme) {
		this.type = type;   this.lexme = lexme; 
		}   
	public TokenType type() { 

		  return this.type; 
		 }  
	public String lexme() {  
		return this.lexme; 
		}  
	@Override  public String toString() { 
		return String.format("%s(%s)", type, lexme); 
		}  
	private static final Map<String,TokenType> KEYWORDS = new HashMap<>();
	static {
		KEYWORDS.put("define", TokenType.DEFINE);  
		KEYWORDS.put("lambda", TokenType.LAMBDA); 
		KEYWORDS.put("cond", TokenType.COND); 
		KEYWORDS.put("quote", TokenType.QUOTE);
		KEYWORDS.put("not", TokenType.NOT); 
		KEYWORDS.put("cdr", TokenType.CDR); 
		KEYWORDS.put("car", TokenType.CAR);  
		KEYWORDS.put("cons", TokenType.CONS);
		KEYWORDS.put("eq?", TokenType.EQ_Q); 
		KEYWORDS.put("null?", TokenType.NULL_Q); 
		KEYWORDS.put("atom?", TokenType.ATOM_Q); 
		} 
}
