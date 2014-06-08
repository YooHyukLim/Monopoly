package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import standard.GameController;


public class Piece {
	int piece_type;
	int position;

	public int map_size;
	int turn_cnt;
	String name;
	PieceImage pImage;
	public ArrayList<Card> cardList = new ArrayList<Card>();

	public Piece(int type, int start_position) {
		piece_type = type;
		position = start_position;
		turn_cnt = 0;
		pImage = new PieceImage();
	}

	public void addCard(Card card) {
		cardList.add(card);
	}

	public int getType() {
		return piece_type;
	}

	public int getPosition() {
		return position;
	}

	public int setPosition(int pos) {
		position = pos;
		return position;
	}

	public int movePosition(int dice) {
		int pos = position + dice;

		if (pos > map_size - 1)
			turn_cnt++;
		pos = pos % (map_size - 1);

		return setPosition(pos);
	}

	public String setName(String name) {
		this.name = name;
		return this.name;
	}

	public String getName() {
		return this.name;
	}

	public void deleteCard(int n){
		cardList.remove(n);
	}

	public class PieceImage extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (GameController.currentPlayer == 1) {
				g.drawRect(0, 0, 15, 15);
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, 15, 15);
			} else {
				g.drawRect(0, 0, 15, 15);
				g.setColor(Color.RED);
				g.fillRect(0, 0, 15, 15);
			}
		}

		public void drawPiece(int x, int y) {
			repaint();
		}
	}
}
