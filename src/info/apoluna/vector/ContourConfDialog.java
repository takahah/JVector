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

public class ContourConfDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	NumberFormat format = new DecimalFormat("0.00000");

	JLabel l1 = new JLabel("Contour Lines");
	JLabel l2 = new JLabel("Minimum");
	JLabel l3 = new JLabel("Maximum");

	JTextField t1 = new JTextField();
	JTextField t2 = new JTextField();
	JTextField t3 = new JTextField();

	JButton ok = new JButton("Ok");
	JButton cancel = new JButton("Cancel");

	public ContourConfDialog(Frame parent) {
		super(parent, "Contour Settings", true);

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
		t1.setText(String.valueOf(conf.getContourLines()));
		t2.setText(format.format(conf.getContourMinimum()));
		t3.setText(format.format(conf.getContourMaximum()));

		super.setVisible(visi);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if ("Ok".equals(cmd)) {
			int lines = 0;
			double minimum = 0.0;
			double maximum = 0.0;
			lines = Integer.parseInt(t1.getText());
			try {
				minimum = format.parse(t2.getText()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			try {
				maximum = format.parse(t3.getText()).doubleValue();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			DrawConfig conf = DrawConfig.getInstance();
			conf.setContourLines(lines);
			conf.setContourMinimum(minimum);
			conf.setContourMaximum(maximum);
			this.dispose();
			this.getParent().repaint();
		} else if ("Cancel".equals(cmd)) {
			this.dispose();
		}
	}
}
