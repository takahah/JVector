package info.apoluna.vector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Vector {

	public Vector() {
	}

	public void paint(Graphics2D g2) {
		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		double len = DrawConfig.getInstance().getVecterMagnification();
		float width = DrawConfig.getInstance().getVectorLineWidth();
		BasicStroke wideStroke = new BasicStroke(width);
		g2.setStroke(wideStroke);
		g2.setPaintMode();
		boolean drawColor = DrawConfig.getInstance().isDrawColor();
		if (drawColor) {
			g2.setColor(DrawConfig.getInstance().getVectorColor());
		} else {
			g2.setColor(Color.black);
		}
		Value value = ValueHolder.getValue();

		int na = value.getNa();
		double[] x = value.getX();
		double[] y = value.getY();
		double[] gv1 = value.getNodeVectorValueX();
		double[] gv2 = value.getNodeVectorValueY();
		for (int i = 0; i < na; i++) {
			double theta = putVecAng(gv1[i], gv2[i]);
			drawVector(g2, x[i], y[i], x[i] + (gv1[i] * len), y[i]
					+ (gv2[i] * len), theta);
		}
	}

	void drawVector(Graphics2D g2, double x, double y, double x2, double y2,
			double theta) {
		// Line2D.Double shape = new Line2D.Double(x, y, x2, y2);
		// g2.draw(shape);
		dr(g2, x, y, x2, y2, theta);
	}

	double putVecAng(double x, double y) {
		double dl, theta;
		dl = Math.sqrt(x * x + y * y);
		if (dl == 0)
			return 9999.0;
		theta = Math.acos(x / dl);
		if (y < 0)
			theta = Math.PI * 2.0 - theta;
		return theta;
	}

	class Point {
		public double x;
		public double y;
	}

	private void dr(Graphics2D g2, double nodex, double nodey, double vecx,
			double vecy, double theta) {
		double len = DrawConfig.getInstance().getVectorArrowLength();
		double angle = DrawConfig.getInstance().getVectorArrowAngle();

		Point rcord = new Point();
		Point rcord2 = new Point();
		Point ex = new Point();
		Point ex2 = new Point();
		if (theta < 1000.0) {
			rcord.x = len * Math.cos(Math.PI - angle);
			rcord2.x = rcord.x;
			rcord.y = len * Math.sin(Math.PI - angle);
			rcord2.y = -rcord.y;

			double cos_theta = Math.cos(theta);
			double sin_theta = Math.sin(theta);

			ex.x = (cos_theta * rcord.x - sin_theta * rcord.y);
			ex.y = (sin_theta * rcord.x + cos_theta * rcord.y);
			ex2.x = (cos_theta * rcord2.x - sin_theta * rcord2.y);
			ex2.y = (sin_theta * rcord2.x + cos_theta * rcord2.y);

			ex.x += vecx;
			ex.y += vecy;
			ex2.x += vecx;
			ex2.y += vecy;

			Line2D.Double shape = new Line2D.Double(nodex, nodey, vecx, vecy);
			g2.draw(shape);
			shape = new Line2D.Double(vecx, vecy, ex.x, ex.y);
			g2.draw(shape);
			shape = new Line2D.Double(vecx, vecy, ex2.x, ex2.y);
			g2.draw(shape);
		}
	}
}
