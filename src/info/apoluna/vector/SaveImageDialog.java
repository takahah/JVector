package info.apoluna.vector;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SaveImageDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 3241020361090391585L;
	DrawPanel mainPanel;
	int width = 1920;
	int height = 1080;

	NumberFormat format = new DecimalFormat("####0");

	JLabel l2 = new JLabel("Width");
	JLabel l3 = new JLabel("Height");

	JTextField t2 = new JTextField(String.valueOf(width));
	JTextField t3 = new JTextField(String.valueOf(height));

	JButton ok = new JButton("Ok");
	JButton cancel = new JButton("Cancel");

	public SaveImageDialog(Frame parent, DrawPanel panel) {
		super(parent, "Image", true);

		mainPanel = panel;

		setLayout(new GridLayout(3, 2));

		add(l2);
		add(t2);
		add(l3);
		add(t3);

		add(ok);
		add(cancel);

		setSize(320, 160);
		ok.addActionListener(this);
		cancel.addActionListener(this);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Ok".equals(cmd)) {
			int width = 0;
			int height = 0;
			try {
				width = format.parse(t2.getText()).intValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				height = format.parse(t3.getText()).intValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			saveImage(width, height);
			this.dispose();
		} else if ("Cancel".equals(cmd)) {
			this.dispose();

		}

	}

	private void saveImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setBackground(Color.white);
		g2.clearRect(0, 0, width, height);
		mainPanel.paint(g2, width, height);
		try {
			ImageIO.write(image, "png", new File("image.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
