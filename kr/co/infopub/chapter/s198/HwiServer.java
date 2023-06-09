package kr.co.infopub.chapter.s198;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HwiServer {
	private List<ServerThread> room = 
			Collections.synchronizedList(new ArrayList<>());
	public HwiServer() {
		room.clear();
	}
	public synchronized void add(ServerThread st) {
		room.add(st);
	}
	public synchronized void remove(ServerThread st) {
		room.remove(st);
	}
	public static void main(String[] args) {
		HwiServer hserver = new HwiServer();
		hserver.go();
	}
	public void broadCasting(String msg) {
		for(int i = 0; i < room.size(); i++) {
			System.out.println("방 개수:" + room.size());
			ServerThread st = room.get(i);
			st.sendMessage(msg);
		}
	}
	ServerSocket ss;
	int port = 9907;
	public void go() {
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			System.out.println("서버소켓 Ready~~~~");
			//blocking
			while(true) {
				Socket s = ss.accept();
				ServerThread st = new ServerThread(s, this);
				add(st);
				st.start();
			}
		}catch(IOException e) {
			System.out.println(e);
		}finally {
			try {
				ss.close();
			}catch(IOException e) {
				
			}
		}	
	}
}
