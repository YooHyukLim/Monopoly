package gameClient;

import gui.Lobby;
import gui.NicknameSet;
import gui.Room;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Piece;

import protocol.ChatProtocol;
import protocol.GameProtocol;
import protocol.LobbyProtocol;
import protocol.Protocol;
import standard.Board;
import standard.GameController;
import standard.Map;

public class monoClient extends Thread{
	public static String name;
	String serverIp = "127.0.0.1";

	static Socket socket;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	Protocol data;

	public static Lobby lobby;
	public static Room room;
	
	public ArrayList<String> clients;
	public ArrayList<String> rooms;
	public ArrayList<String> players;
	
	public boolean roomMaster = false;
	public String roomName;
	
	public Map map;
	public Board board;
	public GameController gameController;

	public static void main(String args[]) {
		new monoClient();
	}// main()

	public monoClient() {
		new NicknameSet();
	}

	public monoClient(String name) {
		try {
			this.name = name;
			System.out.println("서버에 연결 중 IP: " + serverIp);
			socket = new Socket(serverIp, 7777);

			System.out.println("서버에 연결되었습니다.");
		
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			out.writeObject(new LobbyProtocol(name, LobbyProtocol.ENTER));
			out.reset();
			
			lobby = new Lobby(this);
			this.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(in!=null){
			try {
				data = (Protocol) in.readObject();
				//System.out.println("From Server : " + data);
				
				if(data instanceof LobbyProtocol)
					analysisLobbyProtocol((LobbyProtocol) data);
				else if (data instanceof ChatProtocol)
					analysisChatProtocol((ChatProtocol) data);
				else if (data instanceof GameProtocol)
					analysisGameProtocol((GameProtocol) data);
			} catch (ClassNotFoundException e) {
				System.out.println("Class Not Found");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	public void analysisLobbyProtocol(LobbyProtocol data){
		if(data.getName() != null)
			name = data.getName();
		short state = data.getProtocol();
		
		if(state == LobbyProtocol.SEND_USER_LIST){
			refreshClients(data.getUserlist());
		} else if (state == LobbyProtocol.SEND_ROOM_LIST){
			refreshRooms(data.getRoomlist());
		} else if (state == LobbyProtocol.SEND_PLAYER_LIST){
			refreshGameRoom(data.getPlayerList());
		} else if (state == LobbyProtocol.CREATE_ROOM){
			roomMaster = true;
			roomName = data.getRoomName();
			
			lobby.f.setVisible(false);
			room = new Room(this, roomName);
		} else if (state == LobbyProtocol.ENTER_ROOM){
			roomName = data.getRoomName();
			
			lobby.f.setVisible(false);
			room = new Room(this, roomName);
		} else if (state == LobbyProtocol.EXIT_ROOM || state == LobbyProtocol.OUT_ROOM){
			if(roomMaster==false && state == LobbyProtocol.EXIT_ROOM)
				JOptionPane.showMessageDialog(null, "방장이 게임을 종료했습니다.");
			roomMaster = false;
			roomName = null;
			room.setVisible(false);
			if(lobby.f.isVisible() == false)
				lobby.f.setVisible(true);
		} else if (state == LobbyProtocol.ENTER_FAIL){
			JOptionPane.showMessageDialog(null, "방이 이미 꽉 차있습니다.");
		} else if (state == LobbyProtocol.GAME_START_USER) {
			room.getReady();
		} else if (state == LobbyProtocol.GAME_READY_CANCEL) {
			room.cancelReady();
		} else if (state == LobbyProtocol.GAME_START_FAIL) {
			JOptionPane.showMessageDialog(null, "상대방이 준비해야 합니다.");
		}
	}
	
	public void analysisChatProtocol(ChatProtocol data){
	}
	
	public void analysisGameProtocol(GameProtocol data){
		short state = data.getProtocol();
		if (state == GameProtocol.GAME_START) {
			JOptionPane.showMessageDialog(null, "!!!!");
			startGame(data);
		}
	}
	
	public void outRoom(){
		LobbyProtocol data = new LobbyProtocol(name, LobbyProtocol.OUT_ROOM);
		data.setRoomName(roomName);
		sendToServer(data);
	}
	
	public void refreshClients(ArrayList<String> clients){
		if(this.clients!=null)
			this.clients.clear();
		this.clients = new ArrayList<String>(clients);
		
		lobby.refreshClients(this.clients);
	}
	
	public void refreshRooms(ArrayList<String> rooms){
		if(this.rooms!=null)
			this.rooms.clear();
		this.rooms = rooms;
		
		lobby.refreshRooms(rooms);
	}
	
	public void refreshGameRoom(ArrayList<String> players){
		if(this.players!=null)
			this.players.clear();
		this.players = players;
		
		room.refresh(players);
	}

	public void startGame(GameProtocol data) {
		try {
			map = new Map();
			map.generate_map(data.getMapList());
			
			ArrayList<Piece> Players = new ArrayList<Piece>(2);
			Piece Player1 = new Piece(0, 0);
			Player1.setName(data.getRoomPlayer().get(0));
			Player1.map_size = 36;
			Piece Player2 = new Piece(1, 0);
			Player2.setName(data.getRoomPlayer().get(1));
			Player2.map_size = 36;
			
			Players.add(Player1);
			Players.add(Player2);
			
			gameController = new GameController();
			gameController.setPlayer(Players);
			board = new Board(map);
			gameController.setView(board);
			gameController.setMyName(name);
			board.getController(gameController);

			room.setVisible(false);
			board.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendToServer(Protocol data){
		try {
			out.writeObject(data);
			out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		System.exit(0);
	}
}
