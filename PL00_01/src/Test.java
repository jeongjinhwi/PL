import java.io.*;

public class Test {

	public static void main(String[] args) {
		// TODO �ڵ� ������ �޼ҵ� ����

		RecursionLinkedList list = new RecursionLinkedList();
		FileReader fr;
		try {
			fr = new FileReader("C:\\Users\\wlsgn\\Desktop\\hw01.txt");	//������ ���
			BufferedReader br = new BufferedReader(fr);
			String inputString = br.readLine();	//txt������ ù��° ���� �д´�.
			for (int i = 0; i < inputString.length(); i++)
				list.add(inputString.charAt(i));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(list.toString());	//txt���� ���
		list.add(3, 'b');	//txt���� 3��° ĭ�� b�߰�.
		System.out.println(list.toString());	//3��° ĭ�� b�߰��� ���� ���
		list.reverse();		//txt���� �Ųٷ� ����
		System.out.println(list.toString());	//txt���� �Ųٷ� ���
	}

}
