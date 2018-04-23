import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Scanner {

	private int transM[][];	//��ȯ�� �Է°� ����
	private String source;	//�Է°�
	private StringTokenizer st;	//�Էµ� ���� 1�������� �ɰ��� ����

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
	private void initTM() { //�Է¹��� ��ū ����
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

		//��ū�� �� �ִ��� �˻�
		if(!st.hasMoreTokens())
			return null;
		//�� ���� ��ū�� ����
		String temp = st.nextToken();

		Token result = null;
		for(int i = 0; i< temp.length(); i++){
			//���ڿ��� ���ڸ� �ϳ��� ������ ���� �Ǻ�
			stateNew = transM[stateOld][temp.charAt(i)];
			if(stateNew == -1){
				//�Էµ� ������ ���°� reject �̹Ƿ� �����޼��� ����� return ��
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
		//Token ����Ʈ��ȯ, nextToken()�̿� ...
		List<Token> list = new ArrayList<>();	//��ū�� ������ list
		st = new StringTokenizer(source, " ");	//�Է°��� ���� ������ �ɰ���.
		while(st.hasMoreTokens()){	//���ڿ��� ��ū���� �����Ͽ� ��밡���� ��ū�� �������� true
			list.add(nextToken());	//list�� ��ũ�� �߰��ϰ� ������ū���� �Ѿ��.
		}
		return list;	//list��ȯ
	}

	public static void main(String[] args) {
		// TODO �ڵ� ������ �޼ҵ� ����
		//txt file to String
		String source = "banana   267 h cat -3789 7 y2010";	//�Է°�
		Scanner s = new Scanner(source);
		List<Token> tokens = s.tokenize();	//�Է°��� ��ūȭ
		 for(int i = 0; i < tokens.size(); i++) {	//��ū �����ŭ �ݺ����� ���� ��ū ���
	         System.out.println(tokens.get(i));
	      }

	}

}
