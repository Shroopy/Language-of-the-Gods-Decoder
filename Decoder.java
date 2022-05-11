import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;

public class Decoder {
	private static final HashMap<Character, Point2D> dictionary = new HashMap<Character, Point2D>();
	static {
		double xMod = 59.5833333333;
		char[] row1 = new char[] { '`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=' };
		char[] row2 = new char[] { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\' };
		char[] row3 = new char[] { 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'' };
		char[] row4 = new char[] { 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/' };
		char[][] characters = new char[][] { row1, row2, row3, row4 };
		int[] xOffsets = { 30, 120, 135, 160 };
		int[] yOffsets = { 30, 90, 150, 210 };

		for (int i = 0; i < characters.length; i++) {
			int xOffset = xOffsets[i];
			int yOffset = yOffsets[i];
			for (int j = 0; j < characters[i].length; j++) {
				dictionary.put(characters[i][j], new Point2D(xOffset + (int) (xMod * j), yOffset));
			}
		}

	}

	public static JPanel[] decode(String code) {
		ArrayList<ArrayList<ArrayList<Character>>> lines = new ArrayList<ArrayList<ArrayList<Character>>>();
		String[] linesA = code.split("\n");
		for (int i = 0; i < linesA.length; i++) {
			ArrayList<ArrayList<Character>> words = new ArrayList<ArrayList<Character>>();
			lines.add(words);
			String[] wordsA = linesA[i].split("\s");
			for (int j = 0; j < wordsA.length; j++) {
				ArrayList<Character> chars = new ArrayList<Character>();
				words.add(chars);
				char[] charsA = wordsA[j].toCharArray();
				for (int k = 0; k < charsA.length; k++) {
					chars.add(charsA[k]);
				}
			}
		}

		// System.out.println(code);
		// System.out.println();
		// for (int i = 0; i < lines.size(); i++) {
		// 	for (int j = 0; j < lines.get(i).size(); j++) {
		// 		System.out.print(lines.get(i).get(j) + " ");
		// 	}
		// 	System.out.println();
		// }

		JPanel[] panes = new JPanel[lines.size()];
		for (int i = 0; i < lines.size(); i++) {
			JPanel pane = new JPanel2(lines.get(i), dictionary);
			Dimension d = new Dimension(896, 240);
			pane.setPreferredSize(d);
			panes[i] = pane;
		}
		return panes;
	}
}

class JPanel2 extends JPanel {
	private ArrayList<ArrayList<Character>> words;
	private final HashMap<Character, Point2D> dictionary;

	public JPanel2(ArrayList<ArrayList<Character>> words, HashMap<Character, Point2D> dictionary) {
		super();
		this.words = words;
		this.dictionary = dictionary;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		final int WIDTH = 50, HEIGHT = 50;
		final BufferedImage image;
		try {
			image = ImageIO.read(new File("keyboard.png"));
		} catch (IOException e) {
			System.out.println("Failed to load image.");
			return;
		}
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(image, 0, 0, null);
		g2.setColor(new Color(105, 255, 70));
		AlphaComposite alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
		g2.setComposite(alcom);

		for (int i = 0; i < words.size(); i++) {
			if (words.get(i).size() == 1) {
				g2.setStroke(new BasicStroke(5));
				Point2D coords = dictionary.get(words.get(i).get(0));
				g2.drawOval(coords.getX() - (WIDTH / 2), coords.getY() - (HEIGHT / 2), WIDTH, HEIGHT);
			} else {
				g2.setStroke(new BasicStroke(10));
				for (int j = 0; j < words.get(i).size() - 1; j++) {
					Point2D coords = dictionary.get(words.get(i).get(j));
					Point2D coords2 = dictionary.get(words.get(i).get(j + 1));
					g2.drawLine(coords.getX(), coords.getY(), coords2.getX(), coords2.getY());
				}
			}
		}
	}
}