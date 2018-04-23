
public class Recusion {

	public static int fibonacci(int n) { // fibonacci 수열에서 n번째 수를 반환한다.
		if (n == 1)			//n이 1일때 1을 반환
			return 1;
		else if (n == 2)	//n이 2일때도 1을 반환
			return 1;
		else
			return fibonacci(n - 2) + fibonacci(n - 1);	//fibonacci 수열 법칙에 의해 n-2까지의 수열과 n-1까지의 수열을 더한다.
	}

	public static String recursiveAnt(int n) { // 개미수열에서 n번째 값을 반환. 해당 함수 안에는 n번째 개미수열의 값을 만드는 makeResult함수가 존재
		if(n == 1)	//개미수열의 첫번째 줄은 1이다.
			return "1";
		else	//개미수열이 여러가지의 줄을 가지면 makeresult에 의해 이전 개미수열(recursiveAnt)을 이용한다.
			return makeResult(recursiveAnt(n-1));
		
	}
		

	public static String makeResult(String previous) { // 이전 개미수열의 값을 이용하여 다음 개미수열의 값을 만든다. 반복문을1회만 사용가능
		int count = 1;	//개미수열에 나온 숫자를 세는 변수
		String result = "";	//개미수열의 결과값을 저장하는 string
		char ant = previous.charAt(0);	//이전 개미수열의 첫번째 값을 char에 저장
		for(int i = 1; i<previous.length(); i++){	//이전 개미수열 크기만큼 반복문
			if(ant == previous.charAt(i)){	//이전 개미수열 첫번째 값과 이전 개미수열의 반복문 인덱스 만큼 같으면 count를 늘린다.
				count++;
			}else{		//동일한 숫자가 나오지 않으면 개미수열 결과값에 이전에 나온 숫자를 더하고 그 결과값에 이전에 나온 숫자의 크기를 더한다.
				result = result+ant;
				result = result+count;
				ant = previous.charAt(i);	//이전 개미수열 첫번째 값에 이전 개미수열의 반복문 인덱스값을 넣는다.
				count = 1;	//count를 1로 초기화한다.
			}
		}
		result = result+ant;	//개미수열 결과값에 이전에 나온 숫자를 더하고 그 결과값에 이전에 나온 숫자의 크기를 더한다.
		result = result+count;
		return result;	//result값을 반환한다.
	}

	public static void main(String[] args) {
		System.out.println(fibonacci(10));
		System.out.println(recursiveAnt(10));
	}
}
