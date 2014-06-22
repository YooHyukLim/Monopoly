package gameClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class monoClient {
	public static void main(String args[]){
		try {
			String serverIp = "127.0.0.1";
			System.out.println("������ ���� �� IP: "+serverIp);
			Socket socket = new Socket(serverIp, 7777);
			
			System.out.println("������ ����Ǿ����ϴ�.");
			
			Thread sender = new Thread(new ClientSender(socket, args[0]));
			Thread receiver = new Thread(new ClientReceiver(socket));
			
			sender.start();
			receiver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//main()
	
	static class ClientSender extends Thread{
		Socket socket;
		ObjectOutputStream out;
		String name;
		
		ClientSender(Socket socket, String name){
			this.socket = socket;
			
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				this.name = name;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//������
		
		public void run(){		
			while(out!=null){
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
