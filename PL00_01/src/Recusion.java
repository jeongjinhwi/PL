
public class Recusion {

	public static int fibonacci(int n) { // fibonacci �������� n��° ���� ��ȯ�Ѵ�.
		if (n == 1)			//n�� 1�϶� 1�� ��ȯ
			return 1;
		else if (n == 2)	//n�� 2�϶��� 1�� ��ȯ
			return 1;
		else
			return fibonacci(n - 2) + fibonacci(n - 1);	//fibonacci ���� ��Ģ�� ���� n-2������ ������ n-1������ ������ ���Ѵ�.
	}

	public static String recursiveAnt(int n) { // ���̼������� n��° ���� ��ȯ. �ش� �Լ� �ȿ��� n��° ���̼����� ���� ����� makeResult�Լ��� ����
		if(n == 1)	//���̼����� ù��° ���� 1�̴�.
			return "1";
		else	//���̼����� ���������� ���� ������ makeresult�� ���� ���� ���̼���(recursiveAnt)�� �̿��Ѵ�.
			return makeResult(recursiveAnt(n-1));
		
	}
		

	public static String makeResult(String previous) { // ���� ���̼����� ���� �̿��Ͽ� ���� ���̼����� ���� �����. �ݺ�����1ȸ�� ��밡��
		int count = 1;	//���̼����� ���� ���ڸ� ���� ����
		String result = "";	//���̼����� ������� �����ϴ� string
		char ant = previous.charAt(0);	//���� ���̼����� ù��° ���� char�� ����
		for(int i = 1; i<previous.length(); i++){	//���� ���̼��� ũ�⸸ŭ �ݺ���
			if(ant == previous.charAt(i)){	//���� ���̼��� ù��° ���� ���� ���̼����� �ݺ��� �ε��� ��ŭ ������ count�� �ø���.
				count++;
			}else{		//������ ���ڰ� ������ ������ ���̼��� ������� ������ ���� ���ڸ� ���ϰ� �� ������� ������ ���� ������ ũ�⸦ ���Ѵ�.
				result = result+ant;
				result = result+count;
				ant = previous.charAt(i);	//���� ���̼��� ù��° ���� ���� ���̼����� �ݺ��� �ε������� �ִ´�.
				count = 1;	//count�� 1�� �ʱ�ȭ�Ѵ�.
			}
		}
		result = result+ant;	//���̼��� ������� ������ ���� ���ڸ� ���ϰ� �� ������� ������ ���� ������ ũ�⸦ ���Ѵ�.
		result = result+count;
		return result;	//result���� ��ȯ�Ѵ�.
	}

	public static void main(String[] args) {
		System.out.println(fibonacci(10));
		System.out.println(recursiveAnt(10));
	}
}
