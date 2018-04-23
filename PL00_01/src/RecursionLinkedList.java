

public class RecursionLinkedList {
	private Node head;
	private static char UNDEF = Character.MIN_VALUE;
	
	private void linkFirst(char element){	//새롭게 생성된 노드를 리스트의 처음으로 연결
		head = new Node(element, head);
	}

	private void linkLast(char element, Node x){	//과제1. 주어진 Node x의 마지막으로 연결된 Node의 다음으로 새롭게 생성된 노드를 연결
		if(x.next == null){				//다음 노드가 null이면 노드 새로 생성
			x.next = new Node(element,null);
		}
		else{		//null이 아니면 x.next를 이용하여 null이 나올때 까지 재귀
			linkLast(element, x.next);
		}
	}
	private void linkNext(char element, Node pred){	//이전 Node의 다음 Node로 새롭게 생성된 노드를 연결
		Node next = pred.next;			//이전노드의 다음노드를 새롭게 생성된 노드에 연결
		pred.next = new Node(element, next);
	}
	private char unlinkFirst(){		//리스트의 첫번째 원소 (해제)삭제
		Node x = head;	
		char element = x.item;
		head = head.next;
		x.item=UNDEF;
		x.next=null;
		return element;
	}
	private char unlinkNext(Node pred){	//이전노드의 다음 노드연결 (해제)삭제
		Node x = pred.next;	//노드x 를 이전노드의 다음노드로 설정
		Node next = x.next;
		char element = x.item;
		x.item = UNDEF;
		x.next = null;
		pred.next = next;
		return element;
	}
	
	private Node node(int index, Node x){	//과제2. x노드에서 index만큼 떨어진 node 반환
		if(index == 0){	//index가 0이면 x를 반환
			return x;
		}else{	//index가 0이 아니면 index를 줄여가고 다음 노드 방문
			index --;
			return node(index,x.next);
		}
	}
	private int length(Node x){		//과제3. 노드로부터 끝까지의 리스트의 노드 갯수 반환
		if(x!=null){	//x가 null이 아니면 재귀를 사용하여 값을 1씩 증가시킨다.
			return length(x.next)+1;
		}else{		//x가 null이 아니면 0을 반환.
			return 0;
		}
	}
	private String toString(Node x){	//과제4. 노드로부터 시작하는 리스트의 내용 반환
		if(x != null && x.next == null){	//x가 null이 아니고 다음노드가 null이면 x의 값을 출력
			return x.item +" ";
		}else if(x != null && x.next != null){	//x가 null이 아니고 다음노드도 null이 아니면 재귀를 이용하여 다음노드를 조건문에 의해 판단하여 출력
			return x.item + toString(x.next);
		}else{		//x가 null이면 null반환
			return null;
		}
	}
	
	private void reverse(Node x, Node pred){	//현재 노드가 이전 노드부터 리스트의 끝까지를 거꾸로 만듬
		if(x.next == null){	//다음 노드가 null이면 처음노드를 x로 설정하고 다음노드를 이전노드로 설정
			head = x;
			x.next = pred;
		}else{	//다음 노드가 null이 아니면 새로운 노드에 다음노드를 넣는다. 다음노드에 이전노드 값을 넣는다. 재귀를 이용하여 새로 생성한 노드를 현재노드, 이전노드에 x를 넣는다.
			Node next_node = x.next;	//새로 생성한 노드는 현재에서 다음노드를 나타내는 노드이다.
			x.next = pred;
			reverse(next_node, x);
		}
	}
	public boolean add(char element){	//원소를 리스트의 마지막에 추가
		if(head == null){
			linkFirst(element);
		}else{
			linkLast(element,head);
		}
		return true;
	}
	public void add(int index, char element){	//원소를 주어진 index 위치에 추가.
		if(!(index >= 0 && index <= size()))
			throw new IndexOutOfBoundsException(""+index);
		if(index == 0)
			linkFirst(element);
		else
			linkNext(element, node(index-1, head));
	}
	public char get(int index){	//리스트에서 index 위치의 원소 반환
		if(!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException(""+index);
		return node(index,head).item;
	}
	/** 리스트에서 inex 위치의 원소 삭제*/
	public char remove(int index) {	//리스트에서 index 위치의 원소 삭제
		if (!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException("" + index);

		if (index == 0) {
			return unlinkFirst();
		}
		return unlinkNext(node(index - 1, head));
	}

	/** * 리스트를 거꾸로 만듬 */
	public void reverse() {
		reverse(head, null);
	}

	/** * 리스트의 원소 갯수 반환 */
	public int size() {
		return length(head);
	}

	@Override
	public String toString() {
		if (head == null)
			return "[]";

		return "[ " + toString(head) + "]";
	}

	/** * 리스트에 사용될 자료구조 */
	private static class Node {
		char item;
		Node next;

		Node(char element, Node next) {
			this.item = element;
			this.next = next;
		}
	}

}
