package standard;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import dialog.cardDialog;
import dialog.missionDialog;

import model.Card;
import model.Dice;
import model.Piece;

public class GameController {
	public Board board;
	public ArrayList<Piece> Players;
	public int currentPlayer;
	public int playerN;
	public int turn;
	public int DiceNumber, Dice1, Dice2;
	Dice dice = new Dice();
	public Card tempcard;

	public GameController() {
	}

	public void setPlayerbyDice() {
		Dice1 = dice.exec(1);
		Dice2 = dice.exec(2);
		DiceNumber = Dice1 + Dice2;
		String msg = "Dice Number: " + Dice1 + " + " + Dice2 + " = "
				+ DiceNumber;
		JOptionPane.showMessageDialog(null, msg);

		Players.get(currentPlayer).movePosition(DiceNumber);
		System.out
				.println("CurrentPlayer: "
						+ Players.get(currentPlayer).getName() + " Dice: "
						+ DiceNumber);
	}

	public void MapExec() {
		System.out.println("Map Type: "
				+ Map.Map_pieces[Players.get(currentPlayer).getPosition()]
						.get_map_number());
		Map.Map_pieces[Players.get(currentPlayer).getPosition()].exec(Players
				.get(currentPlayer));

		for (int i = 0; i < playerN; i++)
			System.out.println(Players.get(i).getName() + ": "
					+ Players.get(i).getPosition());
	}

	public int changePlayer() {
		turn++;
		return currentPlayer = (currentPlayer + 1) % playerN;
	}

	public boolean getCard() {
		Card card = new Card(1);
		if (Players.get(currentPlayer).cardList.size() < 5) {
			Players.get(currentPlayer).addCard(card);
			System.out.println("Card Type: "
					+ Players.get(currentPlayer).cardList.get(
							Players.get(currentPlayer).cardList.size() - 1)
							.getCardNumber());
		} else {
			this.tempcard = card;
			return loadCardDialog();
		}

		return true;
	}

	public boolean addCard(Card card) {
		if (Players.get(currentPlayer).cardList.size() < 5) {
			Players.get(currentPlayer).addCard(card);
			System.out.println("Card Type: "
					+ Players.get(currentPlayer).cardList.get(
							Players.get(currentPlayer).cardList.size() - 1)
							.getCardNumber());
		} else {
			this.tempcard = card;
			return loadCardDialog();
		}

		return true;
	}

	public void deleteCard(int n) {
		System.out.println("Card " + n + " is deleted");
		Players.get(currentPlayer).deleteCard(n);
	}

	public void setPlayer(ArrayList<Piece> Players) {
		currentPlayer = 0;
		this.turn = 0;
		this.playerN = Players.size();
		this.Players = Players;
	}
	
	public void catching(){
		int nowposition = Players.get(currentPlayer).getPosition();
		int size = Players.size();
		for(int i=0; i<size; i++){
			if(i != currentPlayer && nowposition == Players.get(i).getPosition()){
				System.out.println(Players.get(currentPlayer).getName()+"이 "+Players.get(i).getName()+"을 잡았습니다!");
				board.disappearPiece(i);
				Players.get(currentPlayer).catching();
				Players.get(i).caught();
				board.showPiece(i);
			}
		}
	}
	
	public void missionCheck(){
		int size = Players.size();
		for(int i=0; i<size; i++){
			if(Players.get(i).missionCheck())
				loadMissionDialog(i);
		}
	}

	public void setView(Board board) {
		this.board = board;
		System.out.println("GameController got a Board");
	}

	public boolean loadCardDialog() {
		System.out.println("Loaded CardDialog");
		cardDialog dialog = new cardDialog(board.frame, this);
		return dialog.init();
	}
	
	public void loadMissionDialog(int i){
		System.out.println("Game Over!");
		new missionDialog(board.frame, this, i);
	}

	public int getTurn() {
		return turn / Players.size() + 1;
	}
}