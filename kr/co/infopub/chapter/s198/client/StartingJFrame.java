package kr.co.infopub.chapter.s198.client;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartingJFrame extends JFrame{
	private static final long serialVersionUID = 122454214237L;  //JAVA5
	private JPanel mainp;
	public StartingJFrame() {
		System.out.println(this.getClass().getName() + " Start!!");
		//inits();  //JFrame에 기본 패널을 붙이기 그리고 중앙에 놓기
	}
	private void inits() {
		//-------------- 필수 ---------------//
		mainp= (JPanel) this.getContentPane();
		mainp.setLayout(new BorderLayout()); //중앙을 사용하기 위해서
	}
	

}
