package com.myeedit;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileWindow extends Frame implements ActionListener, Runnable {
	Thread compiler = null;
	Thread run_prom = null;
	boolean bn = true;
	CardLayout myCard;
	File file_save = null;
	JButton button_input_txt, button_compiler, button_compiler_txt, button_run_prom, button_see_doswin;
	JPanel p = new JPanel();
	JTextArea input_text = new JTextArea();
	JTextArea compiler_text = new JTextArea();
	JTextArea dos_out_text = new JTextArea();

	JTextField input_file_name_text = new JTextField();
	JTextField run_file_name_text = new JTextField();

	public FileWindow() {
		super("java 语言编译器");
		myCard = new CardLayout();
		compiler = new Thread(this);
		run_prom = new Thread(this);
		button_input_txt = new JButton("程序输入区（白色）");
		button_compiler = new JButton("编译程序");
		button_compiler_txt = new JButton("编译结果区（绿色）");
		button_run_prom = new JButton("运行程序");
		button_see_doswin = new JButton("程序运行结果（浅蓝色）");
		p.setLayout(myCard);
		p.add("input", input_text);
		p.add("compiler", compiler_text);
		p.add("dos", dos_out_text);
		add(p, "Center");
		compiler_text.setBackground(Color.green);
		dos_out_text.setBackground(Color.cyan);
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(3, 3));
		p1.add(button_input_txt);
		p1.add(button_compiler_txt);
		p1.add(button_see_doswin);
		p1.add(new JLabel("请输入要编译的文件名："));
		p1.add(input_file_name_text);
		p1.add(button_compiler);
		p1.add(new JLabel("输入主程序类名:"));
		p1.add(run_file_name_text);
		p1.add(button_run_prom);
		add(p1, "North");
		button_compiler.addActionListener(this);
		button_compiler_txt.addActionListener(this);
		button_input_txt.addActionListener(this);
		button_run_prom.addActionListener(this);
		button_see_doswin.addActionListener(this);
	}

	@Override
	public void run() {
		if (Thread.currentThread() == compiler) {
			compiler_text.setText(null);
			String temp = input_text.getText().trim();
			byte[] buffer = temp.getBytes();
			int b = buffer.length;
			String file_name = null;
			file_name = input_file_name_text.getText().trim();

			try {
				file_save = new File(file_name);
				FileOutputStream fileWrite = null;
				fileWrite = new FileOutputStream(file_save);
				fileWrite.write(buffer, 0, b);
				fileWrite.close();
			} catch (Exception e) {
				System.out.println("ERROR");
			}

			try {
				Runtime rt = Runtime.getRuntime();
				InputStream in = rt.exec("javac " + file_name).getErrorStream();
				BufferedInputStream bufIn = new BufferedInputStream(in);
				byte[] inbf = new byte[100];
				int n = 0;
				boolean flag = true;
				while ((n = bufIn.read(inbf, 0, inbf.length)) != -1) {
					String s = null;
					s = new String(inbf, 0, n);
					compiler_text.append(s);
					if (s != null) {
						flag = false;
					}
				}
				if (flag) {
					compiler_text.append("Compile success");
				}
			} catch (Exception e) {
				System.out.println("错误");
			}
		} else if (Thread.currentThread() == run_prom) {
			dos_out_text.setText(null);
			try {
				Runtime rt = Runtime.getRuntime();
				String path = run_file_name_text.getText().trim();
				Process stream = rt.exec("java " + path);
				InputStream in = stream.getInputStream();
				BufferedInputStream biserr = new BufferedInputStream(stream.getErrorStream());
				BufferedInputStream bisIn = new BufferedInputStream(in);
				byte[] buf = new byte[150];
				byte[] err_buf = new byte[150];
				int i = 0;
				int m = 0;
				String s = null;
				String err = null;
				while ((m = bisIn.read(buf, 0, 150)) != -1) {
					s = new String(buf, 0, 150);
					dos_out_text.append(s);
				}
				while ((i = biserr.read(err_buf)) != -1) {
					err = new String(err_buf, 0, 150);
					dos_out_text.append(err);
				}
			} catch (Exception e) {
				System.out.println("cuowu");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button_input_txt) {
			myCard.show(p, "input");
		} else if (e.getSource() == button_compiler_txt) {
			myCard.show(p, "compiler");
		} else if (e.getSource() == button_see_doswin) {
			myCard.show(p, "dos");
		} else if (e.getSource() == button_compiler) {
			if (!(compiler.isAlive())) {
				compiler = new Thread(this);
			}
			try {
				compiler.start();
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			myCard.show(p, "compiler");
		} else if (e.getSource() == button_run_prom) {
			if (!(run_prom.isAlive())) {
				run_prom = new Thread(this);
			}
			try {
				run_prom.start();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			myCard.show(p, "dos");
		}

	}

}
