package info.apoluna.vector;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

public class Contour {
	public double min;
	public double max;
	private int lines;
	private double diff;
	DrawPanel parent;

	public Contour(DrawPanel parent) {
		this.parent = parent;
	}

	private void SearchContour(Graphics2D g2, double[] coordx, double[] coordy,
			double[] value, int[] elIdx) {
		int j;
		int i0, i1, i2, lowLine, highLine, idum;
		double fm;
		double p, q;
		double point1x, point2x;
		double point1y, point2y;

		int coldef = 1022 / (lines - 1);

		boolean drawColor = DrawConfig.getInstance().isColorContour()
				&& DrawConfig.getInstance().isDrawColor();

		i0 = elIdx[0];
		i1 = elIdx[1];
		i2 = elIdx[2];

		//
		if (value[i0] > value[i1]) {
			idum = i0;
			i0 = i1;
			i1 = idum;
		}
		if (value[i1] > value[i2]) {
			idum = i1;
			i1 = i2;
			i2 = idum;
		}
		if (value[i0] > value[i1]) {
			idum = i0;
			i0 = i1;
			i1 = idum;
		}
		//
		lowLine = (int) ((value[i0] - min) / diff);
		highLine = (int) ((value[i2] - min) / diff);

		if (lowLine >= lines)
			return;
		if (highLine <= 0)
			return;
		if (highLine > lines)
			highLine = lines;
		if (lowLine <= 0)
			lowLine = 0;

		if (lowLine != highLine) {
			double nw0 = value[i0];
			double nw1 = value[i1];
			double nw2 = value[i2];
			double _x0 = coordx[i0];
			double _y0 = coordy[i0];
			double _x1 = coordx[i1];
			double _y1 = coordy[i1];
			double _x2 = coordx[i2];
			double _y2 = coordy[i2];
			for (j = lowLine + 1; j <= highLine; j++) {
				fm = j * diff + min;
				q = (fm - nw0) / (nw2 - nw0);
				p = 1.0d - q;
				point1x = (q * _x2 + p * _x0);
				point1y = (q * _y2 + p * _y0);
				if (value[i1] < fm) {
					q = (fm - nw1) / (nw2 - nw1);
					p = 1.0d - q;
					point2x = (q * _x2 + p * _x1);
					point2y = (q * _y2 + p * _y1);
				} else {
					q = (fm - nw0) / (nw1 - nw0);
					p = 1.0d - q;
					point2x = (q * _x1 + p * _x0);
					point2y = (q * _y1 + p * _y0);
				}
				if (drawColor) {
					g2.setColor(parent.pallet[(j - 1) * coldef]);
				}
				g2.draw(new Line2D.Double(point1x, point1y, point2x, point2y));
			}
		}
	}

	public void paintContour(Graphics2D g2) {
		DrawConfig conf = DrawConfig.getInstance();
		min = conf.getContourMinimum();
		max = conf.getContourMaximum();
		lines = conf.getContourLines();

		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		float width = conf.getContourLineWidth();
		BasicStroke wideStroke = new BasicStroke(width);
		g2.setStroke(wideStroke);
		g2.setColor(conf.getContourColor());
		g2.setPaintMode();
		Value value = ValueHolder.getValue();

		int ne = value.getNe();
		double[] x = value.getX();
		double[] y = value.getY();
		int[][] ie = value.getIe();
		double[] p = value.getNodeScalarValue1();

		// min = value.getMinNodeScalarValue1();
		// max = value.getMaxNodeScalarValue1();
		diff = (max - min) / lines;

		int le = ValueHolder.getValue().getLe();
		if (le == 4) {
			for (int i = 0; i < ne; i++) {
				double xx = 0.0;
				double yy = 0.0;
				double pp = 0.0;
				double coordx[] = new double[5];
				double coordy[] = new double[5];
				double vv[] = new double[5];
				for (int k = 0; k < le; k++) {
					int inode = ie[i][k];
					xx += coordx[k + 1] = x[inode];
					yy += coordy[k + 1] = y[inode];
					pp += vv[k + 1] = p[inode];
				}

				coordx[0] = xx * 0.25;
				coordy[0] = yy * 0.25;
				vv[0] = pp * 0.25;

				int ii[] = new int[3];
				for (int j = 1; j < (le + 1); j++) {
					ii[0] = 0;
					ii[1] = j;
					ii[2] = j + 1;
					if (ii[2] == (le + 1))
						ii[2] = 1;

					SearchContour(g2, coordx, coordy, vv, ii);
				}
			}
		} else {
			for (int i = 0; i < ne; i++) {
				double coordx[] = new double[4];
				double coordy[] = new double[4];
				double vv[] = new double[5];
				for (int k = 0; k < le; k++) {
					int inode = ie[i][k];
					coordx[k + 1] = x[inode];
					coordy[k + 1] = y[inode];
					vv[k + 1] = p[inode];
				}

				int ii[] = new int[3];
				ii[0] = 0;
				ii[1] = 1;
				ii[2] = 2;

				SearchContour(g2, coordx, coordy, vv, ii);
			}
		}
	}

	public void paintContourElement(Graphics2D g2) {
		DrawConfig conf = DrawConfig.getInstance();
		min = conf.getContourMinimum();
		max = conf.getContourMaximum();
		lines = conf.getContourLines();

		// g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);
		float width = conf.getContourLineWidth();
		BasicStroke wideStroke = new BasicStroke(width);
		g2.setStroke(wideStroke);
		g2.setColor(conf.getContourColor());
		g2.setPaintMode();
		Value value = ValueHolder.getValue();

		int ne = value.getNe();
		double[] x = value.getX();
		double[] y = value.getY();
		int[][] ie = value.getIe();
		double[] p = value.getNodeScalarValue1();

		// min = value.getMinNodeScalarValue1();
		// max = value.getMaxNodeScalarValue1();
		diff = (max - min) / lines;
		g2.setPaint(Color.RED);
		int coldef = 1022 / (lines - 1);

		int le = ValueHolder.getValue().getLe();
		for (int i = 0; i < ne; i++) {
			GeneralPath gp = new GeneralPath();
			double prex = x[ie[i][0]];
			double prey = y[ie[i][0]];

			double ave = 0.0;
			for (int j = 1; j < le; j++) {
				double xxj = x[ie[i][j]];
				double yyj = y[ie[i][j]];

				ave += p[ie[i][j]];
				gp.append(new Line2D.Double(prex, prey, xxj, yyj), true);
				prex = xxj;
				prey = yyj;
			}
			gp.closePath();

			ave /= le;
			int lowLine = (int) ((ave - min) / diff);
			g2.setColor(parent.pallet[(lowLine - 1) * coldef]);

			g2.fill(gp);

		}
	}

	public void paint(Graphics2D g2) {
		if (DrawConfig.getInstance().isElementContour()) {
			paintContourElement(g2);
		} else {
			paintContour(g2);
		}
	}
}
