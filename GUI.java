import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {

	private JTextArea codeText;

	public GUI() {
		JFrame window = new JFrame("Language of the Gods Decoder");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = window.getContentPane();
		contentPane.add(
				new JLabel(
						"Enter the encoded message. Separate strokes with spaces, and words/pictures with new lines."),
				BorderLayout.PAGE_START);
		codeText = new JTextArea();
		JScrollPane textScrollPane = new JScrollPane(codeText);
		contentPane.add(textScrollPane, BorderLayout.CENTER);
		JButton button = new JButton("Decode");
		button.addActionListener(this);
		contentPane.add(button, BorderLayout.PAGE_END);
		Dimension d = new Dimension(896, 240);
		window.setPreferredSize(d);
		window.pack();
		window.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JPanel[] panes = Decoder.decode(codeText.getText());
		for (int i = 0; i < panes.length; i++) {
			JFrame window = new JFrame("Picture " + (i + 1) + "/" + panes.length);
			window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			window.add(panes[i], BorderLayout.CENTER);
			window.setResizable(false);
			window.pack();
			window.setVisible(true);
		}
	}
}