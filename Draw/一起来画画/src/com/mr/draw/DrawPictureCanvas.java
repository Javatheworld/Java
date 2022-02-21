package com.mr.draw;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;

public class DrawPictureCanvas extends Canvas{
	private Image image = null;
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	
	public void update(Graphics g) {
		paint(g);
	}

}
