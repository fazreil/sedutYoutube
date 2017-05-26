package com.rohaizanroosley;


import java.awt.EventQueue;
import com.rohaizanroosley.ui.Main;

public class Application{
	
	public static void main(String[] args){

		EventQueue.invokeLater( new Runnable(){
			@Override
			public void run() {
				
				try {
					Main window = new Main();
					window.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	
}
