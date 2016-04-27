public class LinkedStack extends AbstractStack {
	private Node head;

	/*
	 * java ���������� ����������� �� ��������� �������������, ���� ��� ��
	 * ������ ������ public LinkedStack(){ }
	 */
	public void push(Object element) {
		head = new Node(element, head);
		size++;
	}

	protected Object popImpl() {
		Object result = head.value;
		head = head.next;
		return result;
	}

	private static class Node {
		private final Object value;
		private final Node next;

		// final ���� ������������������� ����������
		// ����� ���� ��� ����� ������ ������

		public Node(Object v, Node n) {
			value = v;
			next = n;
		}
	}
}
