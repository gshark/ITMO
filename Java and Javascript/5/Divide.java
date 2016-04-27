public class Divide extends BinaryOperation {
	public Divide(Expression3 first, Expression3 second) {
		super(first, second);
	}
	public int evaluate(int x, int y, int z){
		//assert(second.evaluate(x, y, z) == 0);
		return first.evaluate(x, y, z) / second.evaluate(x, y, z);
	}
}
