package info.apoluna.vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class MainFrame extends JFrame implements ActionListener, Printable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	JCheckBoxMenuItem menuColor;
	JCheckBoxMenuItem menuMonoclome;

	public MainFrame() {
		super("JVector32");

		initMenu();

		mainPanel = new DrawPanel();
		mainPanel.setBackground(java.awt.Color.white);
		add(mainPanel);
	}

	public void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem menuOpen = new JMenuItem("Open");
		JMenuItem menuSave = new JMenuItem("Save");
		menuSave.setEnabled(false);
		JMenuItem menuSaveAs = new JMenuItem("Save As...");
		menuSaveAs.setEnabled(false);
		JMenuItem menuPrintSize = new JMenuItem("Print Size");
		JMenuItem menuPrint = new JMenuItem("Print");
		JMenuItem menuExit = new JMenuItem("Exit");
		JMenu menuEdit = new JMenu("Edit");
		JMenuItem menuCopy = new JMenuItem("Copy");
		menuCopy.setEnabled(false);
		JMenu menuSize = new JMenu("Boundary");
		JMenuItem menuSearch = new JMenuItem("Search");
		menuSearch.setEnabled(false);
		JMenu menuView = new JMenu("View");
		JMenuItem menuVectorMagni = new JMenuItem("Vector Magnification");
		JMenuItem menuContourLines = new JMenuItem("Contour Lines");
		JCheckBoxMenuItem menuMesh = new JCheckBoxMenuItem("Mesh");
		JCheckBoxMenuItem menuVector = new JCheckBoxMenuItem("Vector");
		JCheckBoxMenuItem menuBoundary = new JCheckBoxMenuItem("Boundary");
		JCheckBoxMenuItem menuContour = new JCheckBoxMenuItem("Contour");
		// menuVectorMagni.setEnabled(false);
		// menuContourLines.setEnabled(false);
		menuMesh.setSelected(DrawConfig.getInstance().isMesh());
		menuVector.setSelected(DrawConfig.getInstance().isVector());
		menuBoundary.setSelected(DrawConfig.getInstance().isBoundary());
		menuContour.setSelected(DrawConfig.getInstance().isContour());
		// menuMesh.setEnabled(false);
		// menuVector.setEnabled(false);
		// menuBoundary.setEnabled(false);
		// menuContour.setEnabled(false);
		JMenu menuConfig = new JMenu("Configure");
		menuColor = new JCheckBoxMenuItem("Color");
		menuMonoclome = new JCheckBoxMenuItem("Monoclome");
		JCheckBoxMenuItem menuColorContour = new JCheckBoxMenuItem(
				"Color Contour");
		// JCheckBoxMenuItem menuCreateBitmap = new JCheckBoxMenuItem(
		// "Create Bitmap");
		// JMenuItem menuFont = new JMenuItem("Font");
		menuColor.setSelected(DrawConfig.getInstance().isDrawColor());
		menuMonoclome.setSelected(!DrawConfig.getInstance().isDrawColor());
		menuColorContour.setSelected(DrawConfig.getInstance().isColorContour());
		// menuColor.setEnabled(false);
		// menuMonoclome.setEnabled(false);
		// menuColorContour.setEnabled(false);
		// menuCreateBitmap.setEnabled(false);
		// menuFont.setEnabled(false);
		JMenu menuHelp = new JMenu("Help");

		menuOpen.addActionListener(this);
		menuSave.addActionListener(this);
		menuSaveAs.addActionListener(this);
		menuPrintSize.addActionListener(this);
		menuPrint.addActionListener(this);
		menuExit.addActionListener(this);
		menuCopy.addActionListener(this);
		menuSearch.addActionListener(this);
		menuVectorMagni.addActionListener(this);
		menuContourLines.addActionListener(this);
		menuMesh.addActionListener(this);
		menuVector.addActionListener(this);
		menuBoundary.addActionListener(this);
		menuContour.addActionListener(this);
		menuColor.addActionListener(this);
		menuMonoclome.addActionListener(this);
		menuColorContour.addActionListener(this);
		// menuCreateBitmap.addActionListener(this);
		// menuFont.addActionListener(this);
		menuHelp.addActionListener(this);

		getRootPane().setJMenuBar(menuBar);
		menuBar.add(menuFile);
		menuFile.add(menuOpen);
		menuFile.add(menuSave);
		menuFile.add(menuSaveAs);
		menuFile.add(menuPrintSize);
		menuFile.add(menuPrint);
		menuFile.add(menuExit);
		menuBar.add(menuEdit);
		menuEdit.add(menuCopy);
		menuBar.add(menuSize);
		menuSize.add(menuSearch);
		menuBar.add(menuView);
		menuView.add(menuVectorMagni);
		menuView.add(menuContourLines);
		menuView.add(menuMesh);
		menuView.add(menuVector);
		menuView.add(menuBoundary);
		menuView.add(menuContour);
		menuBar.add(menuConfig);
		menuConfig.add(menuColor);
		menuConfig.add(menuMonoclome);
		menuConfig.add(menuColorContour);
		// menuConfig.add(menuCreateBitmap);
		// menuConfig.add(menuFont);
		menuBar.add(menuHelp);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	JFileChooser filechooser = new JFileChooser();
	Value v = null;

	public void actionPerformed(ActionEvent e) {
		System.out.println("[" + e.getActionCommand() + "]");
		String cmd = e.getActionCommand();
		if ("Open".equals(cmd)) {
			filechooser.addChoosableFileFilter(new FileFilter() {

				@Override
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}

					String ext = getExtension(f);
					if (ext != null) {
						if (ext.equals("inp")) {
							return true;
						}
					}

					return false;
				}

				@Override
				public String getDescription() {
					return "vecファイル形式(*.inp)";
				}

				private String getExtension(File f) {
					String ext = null;
					String filename = f.getName();
					int dotIndex = filename.lastIndexOf('.');

					if ((dotIndex > 0) && (dotIndex < filename.length() - 1)) {
						ext = filename.substring(dotIndex + 1).toLowerCase();
					}

					return ext;
				}
			});
			int selected = filechooser.showOpenDialog(this);
			if (selected == JFileChooser.APPROVE_OPTION) {
				File file = filechooser.getSelectedFile();

				v = new Loader().load(file);
				ValueHolder.setValue(v);
				repaint();
			} else if (selected == JFileChooser.CANCEL_OPTION) {
			} else if (selected == JFileChooser.ERROR_OPTION) {
			}
		} else if ("Vector Magnification".equals(cmd)) {
			VectorConfDialog dialog = new VectorConfDialog(this);
			dialog.setVisible(true);
		} else if ("Contour Lines".equals(cmd)) {
			ContourConfDialog dialog = new ContourConfDialog(this);
			dialog.setVisible(true);
		} else if ("Print".equals(cmd)) {
			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			// aset.add(OrientationRequested.LANDSCAPE);
			aset.add(new Copies(1));
			aset.add(new JobName("JVector32", null));

			/* プリントジョブの生成 */
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.setPrintable(this);

			/* この要求が処理できるプリントサービスを検索する */
			PrintService[] services = PrinterJob.lookupPrintServices();

			if (services.length > 0) {
				System.out
						.println("selected printer: " + services[0].getName());
				try {
					pj.setPrintService(services[0]);
					pj.pageDialog(aset);
					if (pj.printDialog(aset)) {
						pj.print(aset);
					}
				} catch (PrinterException pe) {
					System.err.println(pe);
				}
			}
		} else if ("Mesh".equals(cmd)) {
			DrawConfig.getInstance().setMesh(
					((JCheckBoxMenuItem) e.getSource()).getState());
			repaint();
		} else if ("Vector".equals(cmd)) {
			DrawConfig.getInstance().setVector(
					((JCheckBoxMenuItem) e.getSource()).getState());
			repaint();
		} else if ("Boundary".equals(cmd)) {
			DrawConfig.getInstance().setBoundary(
					((JCheckBoxMenuItem) e.getSource()).getState());
			repaint();
		} else if ("Contour".equals(cmd)) {
			DrawConfig.getInstance().setContour(
					((JCheckBoxMenuItem) e.getSource()).getState());
			repaint();
		} else if ("Color".equals(cmd)) {
			menuColor.setSelected(true);
			menuMonoclome.setSelected(false);
			DrawConfig.getInstance().setDrawColor(true);
			repaint();
		} else if ("Monoclome".equals(cmd)) {
			menuColor.setSelected(false);
			menuMonoclome.setSelected(true);
			DrawConfig.getInstance().setDrawColor(false);
			repaint();
		} else if ("Color Contour".equals(cmd)) {
			DrawConfig.getInstance().setColorContour(
					((JCheckBoxMenuItem) e.getSource()).getState());
			repaint();
		} else if ("Create Bitmap".equals(cmd)) {
		} else if ("Exit".equals(cmd)) {
			this.dispose();
		} else if ("".equals(cmd)) {
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		if (pageIndex == 0) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());

			return Printable.PAGE_EXISTS;
		} else {
			return Printable.NO_SUCH_PAGE;
		}
	}

}
