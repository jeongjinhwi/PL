import java.io.*;

public class Test {

	public static void main(String[] args) {
		// TODO 자동 생성된 메소드 스텁

		RecursionLinkedList list = new RecursionLinkedList();
		FileReader fr;
		try {
			fr = new FileReader("C:\\Users\\wlsgn\\Desktop\\hw01.txt");	//파일의 경로
			BufferedReader br = new BufferedReader(fr);
			String inputString = br.readLine();	//txt파일의 첫번째 줄을 읽는다.
			for (int i = 0; i < inputString.length(); i++)
				list.add(inputString.charAt(i));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(list.toString());	//txt파일 출력
		list.add(3, 'b');	//txt파일 3번째 칸에 b추가.
		System.out.println(list.toString());	//3번째 칸에 b추가한 값을 출력
		list.reverse();		//txt파일 거꾸로 설정
		System.out.println(list.toString());	//txt파일 거꾸로 출력
	}

}
