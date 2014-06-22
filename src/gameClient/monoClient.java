package gameClient;

import gui.NicknameSet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import protocol.LoginProtocol;

public class monoClient {
	public static String name;
	String serverIp = "127.0.0.1";
	
	public static ObjectInputStream in;
	public static ObjectOutputStream out;
	
	public static JFrame m_frame;
	
	public static void main(String args[]){
		new monoClient();
	}//main()
	
	public monoClient(){
		new NicknameSet();
	}
	
	public monoClient(String name){
		try {
			this.name = name;
			System.out.println("������ ���� �� IP: "+serverIp);
			Socket socket = new Socket(serverIp, 7777);
				
			System.out.println("������ ����Ǿ����ϴ�.");
			
			Thread sender = new Thread(new ClientSender(socket, name));
			Thread receiver = new Thread(new ClientReceiver(socket));
			
			sender.start();
			receiver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class ClientSender extends Thread{
		Socket socket;
		ObjectOutputStream out;
		String name;
		
		ClientSender(Socket socket, String name){
			this.socket = socket;
			this.name = name;
			
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//������
		
		public void run(){
			try {
				out.writeObject(new LoginProtocol(name, LoginProtocol.LOGIN_CHECK));
				
				while(out!=null){
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//run()
	}//ClientSender()
	
	static class ClientReceiver extends Thread{
		Socket socket;
		ObjectInputStream in;
		
		ClientReceiver(Socket socket){
			this.socket = socket;
			
			try{
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e){}
		}//������
		
		public void run(){
			while(in!=null){
			}
		}//run()
	}//ClientReceiver()
}
