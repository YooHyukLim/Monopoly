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

import standard.Map;

public class monoServer {
	HashMap<String, ObjectOutputStream> clients;
	
	public monoServer(){
		clients = new HashMap<String, ObjectOutputStream>();
		Collections.synchronizedMap(clients);
	}
	
	public void start(){
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("서버가 준비되었습니다.");
			
			while(true){
				System.out.println("연결요청을 기다립니다.");
				socket = serverSocket.accept();
				
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속하였습니다.");
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

		ServerReceiver(Socket socket){
			this.socket = socket;
			
			try {
				in = new ObjectInputStream (socket.getInputStream());
				out = new ObjectOutputStream (socket.getOutputStream());
			} catch (Exception e) {}
		}
		
		public void run(){
			try{
				/*name = in.readUTF();
				
				clients.put(name, out);
				System.out.println("현재 접속자 수는 "+clients.size()+"입니다.");*/
				while(in!=null){
					sendToAll(in.readUTF());
				}
			} catch(IOException e){
			} finally {
				//clients.remove(name);
				
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"에서 접속을 종료하였습니다.");
				System.out.println("현재 접속자 수는 "+clients.size()+"입니다.");
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
	
	public static void main(String args[]){
		new monoServer().start();
	}
}
