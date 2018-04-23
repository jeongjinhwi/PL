import ast.*;
import ast.Node.Type;
import compile.*;
public class Nodedata {	//201402429 정진휘
	public static int max(Node node){
		//최대값을 리턴하도록 작성 
		//value와 next 값 중 큰 값을 리턴 밑으로 가는거 value 다음으로 가는거 next
		int result = Integer.MIN_VALUE;	//리턴값에 int형 정수 최소값을 설정
		while(node != null){	//노드가 null이 아닐때 까지 반복문
			if(node instanceof ListNode){	//ListNode와 node의 참조변수가 같은지 검사 true일경우 밑의 조건문 실행
				if(max(((ListNode)node).value) > result){	//Listnode의 value와 result를 비교하여 value가 더 큰경우
					result = max(((ListNode)node).value);	//재귀를 이용하여 result에 Listnode의 value를 넣어 값을 바꾼다.
				}
			}else if(node instanceof IntNode){	//IntNode와 node의 참조변수가 같은지 검사 true일 경우 밑의 조건문 실행
				if(((IntNode)node).value > result){	//Intnode의 value와 result를 비교하여 value가 더 큰경우
					result = ((IntNode)node).value;	//result에 value를 넣어 값을 바꾼다.
				}
			}
			node = node.getNext();	//조건문이 지나면 반복문안에서 다음 노드로 이동한다.
		}
		return result;	//결과값 반환
	}
	
	public static int sum(Node node){
		int result = 0;
		//노드 value의 총합을 반환
		//value와 next의 총 합을 리턴하면됨
		while(node != null){	//노드가 null이 아닐때 까지 반복문
			if(node instanceof ListNode){	//ListNode와 node의 참조변수가 같은지 검사 true일 경우 밑의 조건문 실행
				result = result + sum(((ListNode)node).value);	//result에 result와 재귀를 이용하여 Listnode의 value를 더해준다.
			}else if(node instanceof IntNode){	//IntNode와 node의 참조변수가 같은지 검사후 true일 경우 밑의 조건문 실행
				result = result + ((IntNode)node).value;	//result에 result와 IntNode의 value를 더해준다.
			}
			node = node.getNext();	//조건문이 지나면 반복문안에서 다음 노드로 이동한다.
		}
		return result;	//결과값 반환
	}
	
	public static void main(String... args){
		Node node = TreeFactory.createtTree("( ( 3 ( ( 10 ) ) 6 ) 4 1 ( ) -2 ( ) )");	//TreeFactory.createTree를 이용하여 Tree를 생성해준다.
		System.out.println("최대값 : "+max(node));	//max값 출력
		System.out.println("총합 : "+sum(node));	//sum값 출력
	}
}
