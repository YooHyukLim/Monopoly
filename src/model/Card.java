package model;

import java.util.Calendar;
import java.util.Random;

import javax.swing.JOptionPane;

public class Card {
	int card_number;

	public Card() {
		Calendar now = Calendar.getInstance();
		int seed = now.get(Calendar.MILLISECOND);
		Random r = new Random();
		r.setSeed(seed);
		this.setCardNumber(Math.abs(r.nextInt() % 10) + 1);
	}
	
	public Card(int num){
		this.card_number = num;
	}

	public void setCardNumber(int card_number) {
		this.card_number = card_number;
	}

	public int getCardNumber() {
		return card_number;
	}

	public void exec(int master, Piece player, int card_number) {
		switch (card_number) {
		case 1:
			System.out.println("�ϴ޸��� ��\n�ϴ޸��� ���ظ� �޾� �� ĭ �����մϴ�.");
			player.movePosition(1);
			break;
		case 2:
			System.out.println("�ܳ��� ����ǳ\n�ܳ��� �÷��̾ ���ĳ� �� ĭ �����մϴ�.");
			player.movePosition(-3);
			break;
		case 3:
			System.out.println("������� �뷡\n�ҳ��� ������� �� ĭ �����մϴ�.");
			player.movePosition(2);
			break;
		case 4:
			System.out.println("�°��� ��ȣ\n�°��� ��ȣ�� �޾� �� ĭ �����մϴ�.");
			player.movePosition(1);
			break;
		case 5:
			System.out.println("������ ��¦ ����\n���� ������ ����Ͽ� �� ĭ �����մϴ�.");
			player.movePosition(-1);
			break;
		case 6:
			System.out.println("���ӽ��� ����\n���ӽ��� ���� �� ĭ �����մϴ�.");
			player.movePosition(-2);
			break;
		case 7:
			System.out.println("���� ����\n�������� �����Ͽ� ���� ��ġ�� ���ư��ϴ�.");
			player.setPosition(0);
			break;
		case 8:
			System.out.println("�κ���\n�̵��ӵ��� ������ �� ������ �� ���ϴ�.");
			player.rotationCnt++;
			break;
		case 9:
			System.out.println("���� ��\n����ũ��ũ�� ������ �� ĭ �����մϴ�.");
			player.movePosition(-4);
			break;
		case 10:
			System.out.println("�޺� ����\n���ֳ̾��� �ֺ��� ��� �� ĭ �����մϴ�.");
			player.movePosition(-2);
			break;
		default:
		}
	}
	public String getCardType(int card_number) {
		String msg = null;
		switch (card_number) {
		// corner
		case 1:
			msg = "�ϴ޸��� ��";
			break;
		case 2:
			msg = "�ܳ��� ����ǳ";
			break;
		case 3:
			msg = "������� �뷡";
			break;
		// special maps
		case 4:
			msg = "�°��� ��ȣ";
			break;
		case 5:
			msg = "������ ��¦ ����";
			break;
		case 6:
			msg = "���ӽ��� ����";
			break;
		case 7:
			msg = "���� ����";
			break;
		case 8:
			msg = "�κ���";
			break;
		case 9:
			msg = "���� ��";
			break;
		case 10:
			msg = "�޺� ����";
			break;
		// normal
		default:
			msg = "Normal";
		}
		return msg;
	}
	public String getTypeText(int card_number) {
		String msg = null;
		switch (card_number) {
		// corner
		case 1:
			msg = "�ϴ޸��� ���ظ� �޾� �� ĭ �����մϴ�.";
			break;
		case 2:
			msg = "�ܳ��� �÷��̾ ���ĳ� �� ĭ �����մϴ�.";
			break;
		case 3:
			msg = "�ҳ��� ������� �� ĭ �����մϴ�.";
			break;
		// special maps
		case 4:
			msg = "�°��� ��ȣ�� �޾� �� ĭ �����մϴ�.";
			break;
		case 5:
			msg = "���� ������ ����Ͽ� �� ĭ �����մϴ�.";
			break;
		case 6:
			msg = "���ӽ��� ���� �� ĭ �����մϴ�.";
			break;
		case 7:
			msg = "�������� �����Ͽ� ���� ��ġ�� ���ư��ϴ�.";
			break;
		case 8:
			msg = "�̵��ӵ��� ������ �� ������ �� ���ϴ�.";
			break;
		case 9:
			msg = "����ũ��ũ�� ������ �� ĭ �����մϴ�.";
			break;
		case 10:
			msg = "���ֳ̾��� �ֺ��� ��� �� ĭ �����մϴ�.";
			break;
		// normal
		default:
			msg = "Normal";
		}
		return msg;
	}
}