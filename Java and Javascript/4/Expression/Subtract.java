public class Subtract extends BynaryOperation {
	public Subtract(Expression3 first, Expression3 second) {
		super(first, second);
	}
	public double evaluate(double x, double y, double z){
		return first.evaluate(x, y, z) - second.evaluate(x, y, z);
	}
}
