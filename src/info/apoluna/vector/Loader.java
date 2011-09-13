package info.apoluna.vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Loader {

	public Loader() {
	}

	public Value load(String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			System.err.println("not found!->[" + filename + "]");
		}
		if (!f.canRead()) {
			System.err.println("can't read!->" + filename);
		}

		return load(f);
	}

	public Value load(File file) {
		Value value = new Value();

		int na;
		int ne;
		int le;
		double[] x;
		double[] y;
		int[][] ie;
		double[] nodeVectorValueX;
		double[] nodeVectorValueY;
		double[] nodeScalarValue1;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String first;
			first = br.readLine();
			StringTokenizer t = new StringTokenizer(first);

			na = Integer.parseInt(t.nextToken());
			ne = Integer.parseInt(t.nextToken());
			le = Integer.parseInt(t.nextToken());
			x = new double[na];
			y = new double[na];
			ie = new int[ne][5];
			for (int i = 0; i < na; i++) {
				String line = br.readLine();
				StringTokenizer tt = new StringTokenizer(line);
				// String[] xy = line.split(" ");
				x[i] = Double.parseDouble(tt.nextToken());
				y[i] = Double.parseDouble(tt.nextToken());
			}
			for (int i = 0; i < ne; i++) {
				String line = br.readLine();
				StringTokenizer tt = new StringTokenizer(line);
				// String[] iee = line.split(" ");
				ie[i][0] = Integer.parseInt(tt.nextToken());
				ie[i][1] = Integer.parseInt(tt.nextToken());
				ie[i][2] = Integer.parseInt(tt.nextToken());
				if (le == 4) {
					ie[i][3] = Integer.parseInt(tt.nextToken());
				}
			}
			nodeVectorValueX = new double[na];
			nodeVectorValueY = new double[na];
			nodeScalarValue1 = new double[na];
			double minSv1 = Double.MAX_VALUE;
			double maxSv1 = Double.MIN_VALUE;
			for (int i = 0; i < na; i++) {
				String line = br.readLine();
				StringTokenizer tt = new StringTokenizer(line);
				// String[] xy = line.split(" ");
				nodeVectorValueX[i] = Double.parseDouble(tt.nextToken());
				nodeVectorValueY[i] = Double.parseDouble(tt.nextToken());
				nodeScalarValue1[i] = Double.parseDouble(tt.nextToken());
				if (minSv1 > nodeScalarValue1[i]) {
					minSv1 = nodeScalarValue1[i];
				}
				if (maxSv1 < nodeScalarValue1[i]) {
					maxSv1 = nodeScalarValue1[i];
				}
			}

			double maxEv1 = Double.MIN_VALUE;
			double minEv1 = Double.MAX_VALUE;

			value.setNa(na);
			value.setNe(ne);
			value.setLe(le);
			value.setX(x);
			value.setY(y);
			value.setIe(ie);
			value.setNodeVectorValueX(nodeVectorValueX);
			value.setNodeVectorValueY(nodeVectorValueY);
			value.setNodeScalarValue1(nodeScalarValue1);
			value.setMaxNodeScalarValue1(maxSv1);
			value.setMinNodeScalarValue1(minSv1);
			value.setMaxElemScalarValue1(maxEv1);
			value.setMinElemScalarValue1(minEv1);

			return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
