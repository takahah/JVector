package info.apoluna.vector;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VectorConfDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NumberFormat format = new DecimalFormat("0.00000");
	JLabel l1 = new JLabel("Magnification");
	JLabel l2 = new JLabel("Arrow Size (Length)");
	JLabel l3 = new JLabel("Arrow Size (Angle)");

	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	JTextField t3 = new JTextField();

	JButton ok = new JButton("Ok");
	JButton cancel = new JButton("Cancel");

	public VectorConfDialog(Frame owner) {
		super(owner, "Vector Magnification Settings", true);

		setLayout(new GridLayout(4, 2));

		add(l1);
		add(t1);
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

	public void setVisible(boolean visi) {
		DrawConfig conf = DrawConfig.getInstance();
		t1.setText(format.format(conf.getVecterMagnification()));
		t2.setText(format.format(conf.getVectorArrowLength()));
		t3.setText(format.format(conf.getVectorArrowAngle()));

		super.setVisible(visi);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Ok".equals(cmd)) {
			double mag = 0.0;
			double len = 0.0;
			double angle = 0.0;
			try {
				mag = format.parse(t1.getText()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				len = format.parse(t2.getText()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				angle = format.parse(t3.getText()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			DrawConfig conf = DrawConfig.getInstance();
			conf.setVecterMagnification(mag);
			conf.setVectorArrowLength(len);
			conf.setVectorArrowAngle(angle);
			this.dispose();
			this.getParent().repaint();
		} else if ("Cancel".equals(cmd)) {
			this.dispose();
		}
	}
}
