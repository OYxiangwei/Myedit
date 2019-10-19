package com.myeedit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	public static void main(String[] args) {
		FileWindow fileWindow = new FileWindow();
		fileWindow.pack();
		fileWindow.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		fileWindow.setBounds(200, 180, 550, 360);
		fileWindow.setVisible(true);
	}

}
