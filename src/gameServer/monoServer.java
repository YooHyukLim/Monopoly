package gameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import protocol.LoginProtocol;
import protocol.Protocol;

public class monoServer {
	HashMap<String, ObjectOutputStream> clients;
	Protocol data;
	
	public static void main(String args[]){
		new monoServer().start();
	}
	
	public monoServer(){
		clients = new HashMap<String, ObjectOutputStream>();
		Collections.synchronizedMap(clients);
	}
	
	public void start(){
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("������ �غ�Ǿ����ϴ�.");
			
			while(true){
				System.out.println("�����û�� ��ٸ��ϴ�.");
				socket = serverSocket.accept();
				
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"���� �����Ͽ����ϴ�.");
				new ServerReceiver(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//start()
	
	class ServerReceiver extends Thread{
		Socket socket;
		ObjectInputStream in;
		ObjectOutputStream out;
		String name;

		ServerReceiver(Socket socket){
			this.socket = socket;
			
			try {
				in = new ObjectInputStream (socket.getInputStream());
				out = new ObjectOutputStream (socket.getOutputStream());
			} catch (Exception e) {}
		}
		
		public void run(){
			try{
				data = (Protocol) in.readObject();
				name = data.getName();
				
				if(data instanceof LoginProtocol)
					clients.put(name, out);
				else
					System.exit(0);
				
				System.out.println(name+"���� �����ϼ̽��ϴ�.");
				System.out.println("���� ������ ���� "+clients.size()+"�Դϴ�.");
				
				while(in!=null){
					//sendToAll(in.readUTF());
				}
			} catch(IOException e){
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				clients.remove(name);
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"���� ������ �����Ͽ����ϴ�.");
				System.out.println("���� ������ ���� "+clients.size()+"�Դϴ�.");
			}
		}//run()
	}//ServerReceiver()
	
	void sendToAll(String msg){
		Iterator it = clients.keySet().iterator();

		while(it.hasNext()){
			try {
				ObjectOutputStream out = new ObjectOutputStream((OutputStream) clients.get(it.next()));
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}//sendToAll
}
