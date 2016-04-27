public abstract class AbstractStack implements Stack {
	protected int size;

	// protected ����� ������ ������� ������ � ���� ��� �����������

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Object peek() {
		Object result = pop();
		push(result);
		return result;
	}
	
	public Object pop() {
		assert size > 0;
		Object result = popImpl();
		size--;
		return result;
	}
	
	protected abstract Object popImpl();

}
