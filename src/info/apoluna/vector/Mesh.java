package info.apoluna.vector;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Mesh {

	public Mesh() {
	}

	public void paint(Graphics2D g2) {
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		float width = DrawConfig.getInstance().getMeshLineWidth();
		BasicStroke wideStroke = new BasicStroke(width);
		g2.setStroke(wideStroke);
		g2.setPaintMode();
		g2.setColor(DrawConfig.getInstance().getMeshColor());
		Value value = ValueHolder.getValue();

		int ne = value.getNe();
		double[] x = value.getX();
		double[] y = value.getY();
		int[][] ie = value.getIe();
		for (int i = 0; i < ne; i++) {
			Rectangle2D.Double shape = new Rectangle2D.Double();
			for (int j = 0; j < 4; j++) {
				int idx = ie[i][j];
				shape.add(x[idx], y[idx]);
			}
			g2.draw(shape);
		}
		// Rectangle2D.Double shape = new Rectangle2D.Double();
		// g2.setColor(Color.red);
		// shape.x = -0.5;
		// shape.y = -0.5;
		// shape.width = 1;
		// shape.height = 1;
		// g2.draw(shape);
		// shape = new Rectangle2D.Double();
		// g2.setColor(Color.cyan);
		// shape.x = -0.4;
		// shape.y = -0.4;
		// shape.width = 0.1;
		// shape.height = 0.1;
		// g2.draw(shape);
		// Line2D.Double ss = new Line2D.Double(0.1, 0.1, 0.1, 0.2);
		// g2.setColor(Color.blue);
		// g2.draw(ss);
		//

	}
}
