package com.mr.draw;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.File;
import com.mr.util.*;

public class PictureWindow extends JWindow {
	private JButton changeButton;
	private JButton hiddenButton;
	private BackgroundPanel centerPanel;
	File list[];//ͼƬ�ļ�����
	int index;//��ǰѡ�е�ͼƬ����
	DrawPictureFrame frame;
	
	public PictureWindow(DrawPictureFrame frame) {
		this.frame = frame;
		setSize(400,460);
		init();
		addListener();
	}

	private void init() {
		Container c = getContentPane();
		File dir = new File("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\image\\picture");
		list = dir.listFiles();
		centerPanel = new BackgroundPanel(getListImage());
		c.add(centerPanel,BorderLayout.CENTER);
		
		FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
		flow.setHgap(20);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(flow);
		changeButton = new JButton("����ͼƬ");
		southPanel.add(changeButton);
		hiddenButton = new JButton("����");
		southPanel.add(hiddenButton);
		c.add(southPanel,BorderLayout.SOUTH);
	}

	private void addListener() {
		hiddenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				frame.initShowPicButton();
			}
		});
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				centerPanel.setImage(getListImage());
			}
		});
	}
	
	private Image getListImage() {
		String imgPath = list[index].getAbsolutePath();
		ImageIcon image = new ImageIcon(imgPath);
		index++;
		if(index >= list.length) {
			index = 0;
		}
		return image.getImage();
	}
	
}
