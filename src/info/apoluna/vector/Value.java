package info.apoluna.vector;

public class Value {
	private int na;
	private int ne;
	private int le;
	private int step;
	private double[] x;
	private double[] y;
	private int[][] ie;
	private double[] nodeVectorValueX;
	private double[] nodeVectorValueY;
	private double[] nodeScalarValue1;
	private double[] elemScalarValue1;

	public double[] getElemScalarValue1() {
		return elemScalarValue1;
	}

	public void setElemScalarValue1(double[] elemScalarValue1) {
		this.elemScalarValue1 = elemScalarValue1;
	}

	private double minNodeScalarValue1;
	private double maxNodeScalarValue1;

	public double getMinElemScalarValue1() {
		return minElemScalarValue1;
	}

	public void setMinElemScalarValue1(double minElemScalarValue1) {
		this.minElemScalarValue1 = minElemScalarValue1;
	}

	public double getMaxElemScalarValue1() {
		return maxElemScalarValue1;
	}

	public void setMaxElemScalarValue1(double maxElemScalarValue1) {
		this.maxElemScalarValue1 = maxElemScalarValue1;
	}

	private double minElemScalarValue1;
	private double maxElemScalarValue1;

	public double getMinNodeScalarValue1() {
		return minNodeScalarValue1;
	}

	public void setMinNodeScalarValue1(double minNodeScalarValue1) {
		this.minNodeScalarValue1 = minNodeScalarValue1;
	}

	public double getMaxNodeScalarValue1() {
		return maxNodeScalarValue1;
	}

	public void setMaxNodeScalarValue1(double maxNodeScalarValue1) {
		this.maxNodeScalarValue1 = maxNodeScalarValue1;
	}

	public int getNa() {
		return na;
	}

	public void setNa(int na) {
		this.na = na;
	}

	public int getNe() {
		return ne;
	}

	public void setNe(int ne) {
		this.ne = ne;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}

	public int[][] getIe() {
		return ie;
	}

	public void setIe(int[][] ie) {
		this.ie = ie;
	}

	public double[] getNodeVectorValueX() {
		return nodeVectorValueX;
	}

	public void setNodeVectorValueX(double[] nodeVectorValueX) {
		this.nodeVectorValueX = nodeVectorValueX;
	}

	public double[] getNodeVectorValueY() {
		return nodeVectorValueY;
	}

	public void setNodeVectorValueY(double[] nodeVectorValueY) {
		this.nodeVectorValueY = nodeVectorValueY;
	}

	public double[] getNodeScalarValue1() {
		return nodeScalarValue1;
	}

	public void setNodeScalarValue1(double[] nodeScalarValue1) {
		this.nodeScalarValue1 = nodeScalarValue1;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getLe() {
		return le;
	}

	public void setLe(int le) {
		this.le = le;
	}
}
