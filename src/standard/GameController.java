package standard;

import gameClient.monoClient;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import dialog.cardDialog;
import dialog.missionDialog;

import model.Card;
import model.Dice;
import model.Champion_Sound;
import model.Piece;
import model.Sound;

public class GameController {
	public Board board;
	public ArrayList<Piece> Players;
	public int currentPlayer;
	public int playerN;
	public int turn;
	public int DiceNumber, Dice1, Dice2;
	Dice dice = new Dice();
	public Card tempcard;
	public String myName;
	public int cardmap[][];
	
	monoClient monoClient;

	public GameController() {
	}
	
	public void dicnButton(){
		try {
			Sound btnsound = new Sound(
					"Resources/sounds/game/global-button_large.wav");
			btnsound.play();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("/********************************/");
		if (board.lessThanFive == false) {
			JOptionPane.showMessageDialog(null, "���� ī�带 Ŭ���ؾ� �մϴ�.");
			return;
		} else if (board.cardtime == true) {
			JOptionPane.showMessageDialog(null, "ī�带 ������� �ʰ� �Ѿ�ϴ�.");

			try {
				dofinal();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}

		board.lessThanFive = getCard();

		if (board.lessThanFive == true) {
			try {
				dorest();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void dorest() throws Exception {
		board.refreshCards();
		board.update("card");
		board.disappearPiece(currentPlayer);
		setPlayerbyDice();
		board.refreshInfo();

		CardExec();
		MapExec();
		CardExec();
		board.refreshInfo();

		catching();
		board.showPiece(currentPlayer);
		
		board.Dice_button.setVisible(false);
		board.Next_button.setVisible(true);

		board.executableCards();
	}

	public void dofinal() throws Exception {
		board.Next_button.setVisible(false);
		board.Dice_button.setVisible(true);
		board.gameController.missionCheck();
		board.refreshInfo();
		currentPlayer = changePlayer();
		board.update("card");
		board.refreshInfo();
		board.cardtime = false;
	}

	public void setPlayerbyDice() throws Exception {
		Dice1 = dice.exec(1);
		Dice2 = dice.exec(2);
		DiceNumber = Dice1 + Dice2;
		String msg = "Dice Number: " + Dice1 + " + " + Dice2 + " = "
				+ DiceNumber;
		new Champion_Sound(Players.get(currentPlayer).getType()).normal();
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
	}
	
	public void CardExec(){
		Piece player = Players.get(currentPlayer);
		int position = player.getPosition();
		if(cardmap[position][0]==0)
			return;
		
		System.out.println("//Card Type: "+cardmap[position][2]+"//");
		System.out.println("//"+new Card().getTypeText(cardmap[position][2])+"//");
		JOptionPane.showMessageDialog(null, new Card().getTypeText(cardmap[position][2]));
		new Card().exec(cardmap[position][1], player, cardmap[position][2]);
		board.disappearCard(position);
		
		for (int i = 0; i < playerN; i++)
			System.out.println(Players.get(i).getName() + ": "
					+ Players.get(i).getPosition());
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
	
	public boolean useCard(int n){
		Piece player = Players.get(currentPlayer);
		int position = player.getPosition();
		if(position == 0){
			JOptionPane.showMessageDialog(null, "���� ��ġ���� ī�带 ���� �� �����ϴ�.");
			return false;
		}
		
		if(cardmap[position][0]==1){
			JOptionPane.showMessageDialog(null, "ī�尡 �̹� �����մϴ�.");
			return false;
		}
		System.out.println("Card"+n+" is used");
		player.deleteCard(n);
		cardmap[position][0]=1;
		cardmap[position][1]=currentPlayer;
		cardmap[position][2]=n;
		board.showCard(position);
		
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
		this.cardmap = new int[36][3];
	}
	
	public int changePlayer() {
		turn++;
		return currentPlayer = (currentPlayer + 1) % playerN;
	}
	
	public void catching() throws Exception{
		int nowposition = Players.get(currentPlayer).getPosition();
		int size = Players.size();
		Sound defeat = new Sound("Resources/sounds/game/defeat_enemy1.wav");
		if(nowposition == 0)
			return;
		for(int i=0; i<size; i++){
			if(i != currentPlayer){
				if (nowposition == Players.get(i).getPosition()) {
					System.out.println(Players.get(currentPlayer).getName()
							+ "�� " + Players.get(i).getName() + "�� ��ҽ��ϴ�!");
					defeat.play();
					board.disappearPiece(i);
					Players.get(currentPlayer).catching();
					Players.get(i).caught();
					board.showPiece(i);
				} else if (Players.get(currentPlayer).piece_type == 1
						&& Players.get(i).getPosition() - nowposition <= 3
						&& Players.get(i).getPosition() - nowposition >= -1) {
					System.out.println(Players.get(currentPlayer).getName()
							+ "�� �ϰ��ʻ�!!");
					new Sound("Resources/sounds/MasterYi/alpha.wav").play();
					defeat.play();
					board.disappearPiece(i);
					Players.get(currentPlayer).catching();
					Players.get(i).caught();
					board.showPiece(i);
				}
			}
		}
	}
	
	public void missionCheck() throws Exception{
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
	
	public void loadMissionDialog(int i) throws Exception{
		System.out.println("Game Over!");
		new missionDialog(board.frame, this, i);
	}
	
	public void getClient(monoClient monoClient){
		this.monoClient = monoClient;
	}

	public int getTurn() {
		return turn / Players.size() + 1;
	}
	
	public String getMyName() {
		return myName;
	}
	
	public void setMyName(String myName) {
		this.myName = myName;
	}
}