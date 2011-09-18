package info.apoluna.vector;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.net.URLDecoder;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Color pallet[] = new Color[1024];
	private static int action = DnDConstants.ACTION_COPY_OR_MOVE;
	private static AffineTransform ZOOM_INIT = AffineTransform
			.getScaleInstance(1.0, 1.0);
	private AffineTransform move;
	private AffineTransform zoom = ZOOM_INIT;

	public DrawPanel() {
		init();
		new DropTarget(this, action, dropTargetListener);
		MouseAdapter ma = new MouseAdapter() {
			Point from;
			double x;
			double y;
			double zoomvalue = 1.0;

			public void mousePressed(MouseEvent e) {
				Cursor c = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
				Component p = (Component) e.getSource();
				p.setCursor(c);

				from = e.getPoint();
			}

			public void mouseDragged(MouseEvent e) {
				Point to = e.getPoint();

				x += to.getX() - from.getX();
				y += to.getY() - from.getY();

				move = AffineTransform.getTranslateInstance(x, y);
				from = to;
				repaint();
			}

			public void mouseReleased(MouseEvent e) {
				Cursor c = Cursor.getDefaultCursor();
				Component p = (Component) e.getSource();
				p.setCursor(c);

				boolean r = SwingUtilities.isRightMouseButton(e);
				if (r) {
					x = 0;
					y = 0;
					zoomvalue = 1.0;
					move = null;
					zoom = ZOOM_INIT;
					repaint();
					return;
				}
				Point to = e.getPoint();

				x += to.getX() - from.getX();
				y += to.getY() - from.getY();

				move = AffineTransform.getTranslateInstance(x, y);
				repaint();
			}

			public void mouseWheelMoved(MouseWheelEvent e) {
				zoomvalue += e.getWheelRotation() * 0.1;
				DrawConfig instance = DrawConfig.getInstance();
				instance.setZoom(zoomvalue);
				zoom = AffineTransform.getScaleInstance(zoomvalue, zoomvalue);
				repaint();
			}
		};
		addMouseMotionListener(ma);
		addMouseListener(ma);
		addMouseWheelListener(ma);
	}

	private DropTargetListener dropTargetListener = new DropTargetListener() {

		public void drop(DropTargetDropEvent e) {
			e.acceptDrop(action);

			Transferable tr = e.getTransferable();

			boolean gotData = false;
			try {
				String data = null;
				if (e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					// linux
					data = (String) tr.getTransferData(DataFlavor.stringFlavor);
					gotData = (data != null);

					String filename = URLDecoder.decode(data, "utf-8");
					Loader loader = new Loader();
					Value v = loader.load(filename.replaceAll("file://", "")
							.trim());
					ValueHolder.setValue(v);
					DrawConfig.getInstance().setContourMinimum(
							v.getMinNodeScalarValue1());
					DrawConfig.getInstance().setContourMaximum(
							v.getMaxNodeScalarValue1());
				} else if (e
						.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					// windows
					List<File> files = (List<File>) tr
							.getTransferData(DataFlavor.javaFileListFlavor);
					Loader loader = new Loader();
					Value v = loader.load(files.get(0));
					ValueHolder.setValue(v);
					DrawConfig.getInstance().setContourMinimum(
							v.getMinNodeScalarValue1());
					DrawConfig.getInstance().setContourMaximum(
							v.getMaxNodeScalarValue1());
				}

				repaint();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				e.dropComplete(gotData);
			}
		}

		// @Override
		public void dragOver(DropTargetDragEvent dtde) {
		}

		// @Override
		public void dragExit(DropTargetEvent dte) {
		}

		// @Override
		public void dragEnter(DropTargetDragEvent dtde) {
		}

		// @Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
		}
	};

	private void init() {
		setColorPallet();
	}

	private void setTrans(Graphics2D g2) {
		Value value = ValueHolder.getValue();

		double vx = value.getMaxX() - value.getMinX();
		double vy = value.getMaxY() - value.getMinY();
		int w = this.getWidth();
		int h = this.getHeight();
		
		double wx = w / vx;
		double wy = h / vy;
		
		double ww = 0;
		if (wx > wy) {
			ww = wy;
		} else {
			ww = wx;
		}

		//
		ww -= ww * 0.05;

		AffineTransform t2 = AffineTransform.getTranslateInstance(10, h - 10);
		if (move != null) {
			t2.concatenate(move);
		}

		AffineTransform t = AffineTransform.getScaleInstance(ww, -ww);
		t2.concatenate(t);
		t2.concatenate(zoom);

		g2.setTransform(t2);

	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// backup
		AffineTransform aT = g2.getTransform();

		setTrans(g2);

		DrawConfig ins = DrawConfig.getInstance();

		if (ins.isMesh()) {
			Mesh mesh = new Mesh();
			mesh.paint(g2);
		}
		if (ins.isVector()) {
			Vector v = new Vector();
			v.paint(g2);
		}
		if (ins.isContour()) {
			Contour c = new Contour(this);
			c.paint(g2);
		}

		g2.setTransform(aT);
	}

	private void setColorPallet() {
		int i, ii;
		int red, green, blue;

		for (i = 0; i < 256; i++) {
			red = 0;
			green = i;
			blue = 255;
			pallet[i] = new Color(red, green, blue);
			ii = 256 + i;
			red = 0;
			green = 255;
			blue = 255 - i;
			pallet[ii] = new Color(red, green, blue);
			ii = 2 * 256 + i;
			red = i;
			green = 255;
			blue = 0;
			pallet[ii] = new Color(red, green, blue);

			ii = 3 * 256 + i;
			red = 255;
			green = 255 - i;
			blue = 0;
			pallet[ii] = new Color(red, green, blue);
		}
	}

	public void update() {
		this.repaint();
	}
}
