public interface Stack {
	public abstract void push(Object e);

	public Object pop();

	public Object peek();

	public int size();

	public boolean isEmpty();
	// public ����� ��������, ������ ����� public ��� ����� ������ ������
	// abstract ���� ����������(������, ��� ����� ��� �����������)
}
