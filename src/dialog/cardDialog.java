package dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import standard.GameController;

public class cardDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	cardDialog carddialog;
	GameController gameController;
	JFrame frame;
	JButton ok, cancel;
	boolean result;

	public cardDialog(JFrame parent, GameController gameController) {
		super(parent, "Watch out!!", true);
		this.carddialog = this;
		this.gameController = gameController;
		this.setSize(new Dimension(350, 200));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.ok = new JButton("������ ī�� ������");
		this.cancel = new JButton("���ο� ī�� ������");
	}

	public boolean init() {
		JLabel text1 = new JLabel("ī���� ������ ������ �� �ִ� ������ �ʰ��߽��ϴ�.");
		JLabel text2 = new JLabel("������ �ִ� ī�带 �����ðڽ��ϱ�?");
		JPanel textPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());

		ok.addActionListener(new okbuttonhandler());
		cancel.addActionListener(new cancelbuttonhandler());

		textPanel.add(text1, BorderLayout.NORTH);
		textPanel.add(text2, BorderLayout.SOUTH);
		buttonPanel.add(ok);
		buttonPanel.add(cancel);

		this.getContentPane().add(textPanel, BorderLayout.NORTH);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);

		return result;
	}

	class okbuttonhandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			result = false;
			carddialog.setVisible(false);
			try {
				gameController.board.deleteCards();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	class cancelbuttonhandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			result = true;
			carddialog.setVisible(false);
		}
	}
}
