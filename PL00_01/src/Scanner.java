import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Scanner {

	private int transM[][];	//변환된 입력값 저장
	private String source;	//입력값
	private StringTokenizer st;	//입력된 값을 1개단위로 쪼개는 변수

	public enum TokenType{
		ID(3), INT(2);

		private final int finalState;

		TokenType(int finalState){
			this.finalState = finalState;
		}
	}
	public static class Token {
		public final TokenType type;
		public final String lexme;

		Token(TokenType type, String lexme){
			this.type = type;
			this.lexme = lexme;
		}
		@Override
		public String toString(){
			return String.format("[%s: %s]", type.toString(), lexme);
		}
	}


	public Scanner(String source){
		this.transM = new int[4][128];
		this.source = source == null ? "" : source;
		initTM();
	}
	private void initTM() { //입력받은 토큰 구분
		for(int i = 0; i<4; i++){
			for(int j = 0; j<128; j++){
				if(i == 0){
					if( '0' <= j && j <= '9' ){
						transM[0][j] = 2;
					}else if(j == '-'){
						transM[0][j] = 1;
					}
					else if(('a'<=j && j<='z')||('A'<=j&&j<='Z')){
						transM[0][j] = 3;
					}else{
						transM[0][j] = -1;
					}
				}else if(i == 1){
					if(('a'<=j && j<='z')||('A'<=j&&j<='Z')){
						transM[1][j] = -1;
					}else if(j == '-'){
						transM[1][j] = -1;
					}
					else if('0' <= j && j <= '9'){
						transM[1][j] = 2;
					}else{
						transM[1][j] = -1;
					}
				}else if(i == 2){
					if(('a'<=j && j<='z')||('A'<=j&&j<='Z')){
						transM[2][j] = -1;
					}else if(j == '-'){
						transM[2][j] = -1;
					}
					else if('0' <= j && j <= '9'){
						transM[2][j] = 2;
					}else{
						transM[2][j] = -1;
					}
				}else if(i == 3){
					if(('a'<=j && j<='z')||('A'<=j&&j<='Z')){
						transM[3][j] = 3;
					}else if(j == '-'){
						transM[3][j] = -1;
					}
					else if('0' <= j && j <= '9'){
						transM[3][j] = 3;
					}else{
						transM[3][j] = -1;
					}
				}
			}
		}
		//transM[4][128] = { {...}, {...}, {...}, {...} }; 
		//values of entries:   -1, 0, 1, 2, 3 : next state 
		//TransM[0]['0'] = 2, ..., TransM[0]['9'] = 2, 
		//TransM[0]['-'] = 1, 
		//TransM[0]['a'] = 3, ..., TransM[0]['z'] = 3, 
		//TransM[1]['0'] = 2, ..., TransM[1]['9'] = 2, 
		//TransM[2]['0'] = 2, ..., TransM[1]['9'] = 2, 
		//TransM[3]['A'] = 3, ..., TransM[3]['Z'] = 3, 
		//TransM[3]['a'] = 3, ..., TransM[3]['z'] = 3, 
		//TransM[3]['0'] = 3, ..., TransM[3]['9'] = 3, 
		// ... 
		//     The values of the other entries are all -1. 
	} 
	private Token nextToken(){
		int stateOld = 0, stateNew;

		//토큰이 더 있는지 검사
		if(!st.hasMoreTokens())
			return null;
		//그 다음 토큰을 받음
		String temp = st.nextToken();

		Token result = null;
		for(int i = 0; i< temp.length(); i++){
			//문자열의 문자를 하나씩 가져와 상태 판별
			stateNew = transM[stateOld][temp.charAt(i)];
			if(stateNew == -1){
				//입력된 문자의 상태가 reject 이므로 에러메세지 출력후 return 함
				System.out.println(String.format("acceptState error %s\n", temp));
				return null;
			}
			stateOld = stateNew;
		}
		for(TokenType t:TokenType.values()){
			if(t.finalState == stateOld){
				result = new Token(t, temp);
				break;
			}
		}
		return result;
	}
	public List<Token> tokenize(){
		//Token 리스트반환, nextToken()이용 ...
		List<Token> list = new ArrayList<>();	//토큰을 저장할 list
		st = new StringTokenizer(source, " ");	//입력값을 띄어쓰기 단위로 쪼갠다.
		while(st.hasMoreTokens()){	//문자열을 토큰으로 구분하여 사용가능한 토큰이 더있으면 true
			list.add(nextToken());	//list에 토크을 추가하고 다음토큰으로 넘어간다.
		}
		return list;	//list반환
	}

	public static void main(String[] args) {
		// TODO 자동 생성된 메소드 스텁
		//txt file to String
		String source = "banana   267 h cat -3789 7 y2010";	//입력값
		Scanner s = new Scanner(source);
		List<Token> tokens = s.tokenize();	//입력값을 토큰화
		 for(int i = 0; i < tokens.size(); i++) {	//토큰 사이즈만큼 반복문을 돌려 토큰 출력
	         System.out.println(tokens.get(i));
	      }

	}

}
