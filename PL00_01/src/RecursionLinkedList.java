

public class RecursionLinkedList {
	private Node head;
	private static char UNDEF = Character.MIN_VALUE;
	
	private void linkFirst(char element){	//���Ӱ� ������ ��带 ����Ʈ�� ó������ ����
		head = new Node(element, head);
	}

	private void linkLast(char element, Node x){	//����1. �־��� Node x�� ���������� ����� Node�� �������� ���Ӱ� ������ ��带 ����
		if(x.next == null){				//���� ��尡 null�̸� ��� ���� ����
			x.next = new Node(element,null);
		}
		else{		//null�� �ƴϸ� x.next�� �̿��Ͽ� null�� ���ö� ���� ���
			linkLast(element, x.next);
		}
	}
	private void linkNext(char element, Node pred){	//���� Node�� ���� Node�� ���Ӱ� ������ ��带 ����
		Node next = pred.next;			//��������� ������带 ���Ӱ� ������ ��忡 ����
		pred.next = new Node(element, next);
	}
	private char unlinkFirst(){		//����Ʈ�� ù��° ���� (����)����
		Node x = head;	
		char element = x.item;
		head = head.next;
		x.item=UNDEF;
		x.next=null;
		return element;
	}
	private char unlinkNext(Node pred){	//��������� ���� ��忬�� (����)����
		Node x = pred.next;	//���x �� ��������� �������� ����
		Node next = x.next;
		char element = x.item;
		x.item = UNDEF;
		x.next = null;
		pred.next = next;
		return element;
	}
	
	private Node node(int index, Node x){	//����2. x��忡�� index��ŭ ������ node ��ȯ
		if(index == 0){	//index�� 0�̸� x�� ��ȯ
			return x;
		}else{	//index�� 0�� �ƴϸ� index�� �ٿ����� ���� ��� �湮
			index --;
			return node(index,x.next);
		}
	}
	private int length(Node x){		//����3. ���κ��� �������� ����Ʈ�� ��� ���� ��ȯ
		if(x!=null){	//x�� null�� �ƴϸ� ��͸� ����Ͽ� ���� 1�� ������Ų��.
			return length(x.next)+1;
		}else{		//x�� null�� �ƴϸ� 0�� ��ȯ.
			return 0;
		}
	}
	private String toString(Node x){	//����4. ���κ��� �����ϴ� ����Ʈ�� ���� ��ȯ
		if(x != null && x.next == null){	//x�� null�� �ƴϰ� ������尡 null�̸� x�� ���� ���
			return x.item +" ";
		}else if(x != null && x.next != null){	//x�� null�� �ƴϰ� ������嵵 null�� �ƴϸ� ��͸� �̿��Ͽ� ������带 ���ǹ��� ���� �Ǵ��Ͽ� ���
			return x.item + toString(x.next);
		}else{		//x�� null�̸� null��ȯ
			return null;
		}
	}
	
	private void reverse(Node x, Node pred){	//���� ��尡 ���� ������ ����Ʈ�� �������� �Ųٷ� ����
		if(x.next == null){	//���� ��尡 null�̸� ó����带 x�� �����ϰ� ������带 �������� ����
			head = x;
			x.next = pred;
		}else{	//���� ��尡 null�� �ƴϸ� ���ο� ��忡 ������带 �ִ´�. ������忡 ������� ���� �ִ´�. ��͸� �̿��Ͽ� ���� ������ ��带 ������, ������忡 x�� �ִ´�.
			Node next_node = x.next;	//���� ������ ���� ���翡�� ������带 ��Ÿ���� ����̴�.
			x.next = pred;
			reverse(next_node, x);
		}
	}
	public boolean add(char element){	//���Ҹ� ����Ʈ�� �������� �߰�
		if(head == null){
			linkFirst(element);
		}else{
			linkLast(element,head);
		}
		return true;
	}
	public void add(int index, char element){	//���Ҹ� �־��� index ��ġ�� �߰�.
		if(!(index >= 0 && index <= size()))
			throw new IndexOutOfBoundsException(""+index);
		if(index == 0)
			linkFirst(element);
		else
			linkNext(element, node(index-1, head));
	}
	public char get(int index){	//����Ʈ���� index ��ġ�� ���� ��ȯ
		if(!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException(""+index);
		return node(index,head).item;
	}
	/** ����Ʈ���� inex ��ġ�� ���� ����*/
	public char remove(int index) {	//����Ʈ���� index ��ġ�� ���� ����
		if (!(index >= 0 && index < size()))
			throw new IndexOutOfBoundsException("" + index);

		if (index == 0) {
			return unlinkFirst();
		}
		return unlinkNext(node(index - 1, head));
	}

	/** * ����Ʈ�� �Ųٷ� ���� */
	public void reverse() {
		reverse(head, null);
	}

	/** * ����Ʈ�� ���� ���� ��ȯ */
	public int size() {
		return length(head);
	}

	@Override
	public String toString() {
		if (head == null)
			return "[]";

		return "[ " + toString(head) + "]";
	}

	/** * ����Ʈ�� ���� �ڷᱸ�� */
	private static class Node {
		char item;
		Node next;

		Node(char element, Node next) {
			this.item = element;
			this.next = next;
		}
	}

}
