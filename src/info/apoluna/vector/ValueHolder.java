package info.apoluna.vector;

public class ValueHolder {
	private static Value value = new Value();

	public static Value getValue() {
		return value;
	}

	public static void setValue(Value v) {
		value = v;
	}
}
