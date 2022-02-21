package com.mr.draw;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.BorderLayout;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import java.awt.geom.Ellipse2D;//正方形
import java.awt.geom.Rectangle2D;//圆
import com.mr.util.FrameGetShape;
import com.mr.util.Shapes;
import com.mr.util.ShapeWindow;
import com.mr.util.DrawImageUtil;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.AlphaComposite;
import java.awt.Font;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.Cursor;
import javax.swing.ImageIcon;

public class DrawPictureFrame extends JFrame implements FrameGetShape{
	
	BufferedImage image = new BufferedImage(570, 390, BufferedImage.TYPE_INT_BGR);
	Graphics gs = image.getGraphics();
	Graphics2D g = (Graphics2D)gs;
	DrawPictureCanvas canvas = new DrawPictureCanvas();
	Color foreColor = Color.BLACK;
	Color backgroundColor = Color.WHITE;
	
	//鼠标绘制点横纵坐标
	int x = -1;
	int y = -1;
	boolean rubber = false;//橡皮标志变量
	
	//按钮项
	private JToolBar toolBar;//工具栏
	private JToggleButton strokeButton1;//细线按钮
	private JToggleButton strokeButton2;//粗线按钮
	private JToggleButton strokeButton3;//最粗线按钮
	private JButton backgroundButton;//背景颜色按钮
	private JButton foregroundButton;//前景颜色按钮
	private JButton clearButton;//清除按钮
	private JButton eraserButton;//橡皮按钮
	private JButton saveButton;//保存按钮
	private JButton shapeButton;//图形按钮
	
	//菜单项
	private JMenuItem strokeMenuItem1;//细线菜单
	private JMenuItem strokeMenuItem2;//粗线菜单
	private JMenuItem strokeMenuItem3;//最粗线菜单
	private JMenuItem foregroundMenuItem;//前景颜色菜单
	private JMenuItem backgroundMenuItem;//背景颜色菜单
	private JMenuItem clearMenuItem;//清除菜单
	private JMenuItem eraserMenuItem;//橡皮菜单
	private JMenuItem saveMenuItem;//保存菜单
	private JMenuItem exitMenuItem;//退出菜单
	
	//水印字符串
	private String shuiyin = "";
	private JMenuItem shuiyinMenuItem;
	
	private PictureWindow picWindow;
	private JButton showPicButton;
	
	boolean drawShape = false;//画图形标志变量
	Shapes shape;//绘制的图形
	
	
	public DrawPictureFrame() {
		setResizable(false);
		setTitle("画图程序(水印内容：[)"+shuiyin+"]");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500,100,574,460);
		init();
		addListener();
	}
	
	private void init() {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, 570, 390);
		g.setColor(foreColor);
		canvas.setImage(image);
		getContentPane().add(canvas);
		
		//按钮
		toolBar = new JToolBar();
		getContentPane().add(toolBar,BorderLayout.NORTH);
		
//		showPicButton = new JButton("展开简笔画");
		showPicButton = new JButton();
		showPicButton.setToolTipText("展开简笔画");
		showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\展开.png"));
		toolBar.add(showPicButton);
//		saveButton = new JButton("保存");
		saveButton = new JButton();
		saveButton.setToolTipText("保存");
		saveButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\保存.png"));
		toolBar.add(saveButton);
		toolBar.addSeparator();//添加分割条
		
//		strokeButton1 = new JToggleButton("细线");
		strokeButton1 = new JToggleButton();
		strokeButton1.setToolTipText("细线");
		strokeButton1.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\1像素线条.png"));	
		strokeButton1.setSelected(true);//细线按钮处于被选中状态
		toolBar.add(strokeButton1);//工具栏添加按钮
//		strokeButton2 = new JToggleButton("粗线");
		strokeButton2 = new JToggleButton();
		strokeButton2.setToolTipText("粗线");
		strokeButton2.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\2像素线条.png"));	
		toolBar.add(strokeButton2);//工具栏添加按钮
//		strokeButton3 = new JToggleButton("较粗");
		strokeButton3 = new JToggleButton();
		strokeButton3.setToolTipText("较粗");
		strokeButton3.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\4像素线条.png"));
		toolBar.add(strokeButton3);//工具栏添加按钮
		ButtonGroup strokeGroup = new ButtonGroup();
		strokeGroup.add(strokeButton1);
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);
		toolBar.addSeparator();//添加分割条
		
//		backgroundButton = new JButton("背景颜色");
		backgroundButton = new JButton();
		backgroundButton.setToolTipText("背景颜色");
		backgroundButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\背景色.png"));
		toolBar.add(backgroundButton);
		
//		foregroundButton = new JButton("前景颜色");
		foregroundButton = new JButton();
		foregroundButton.setToolTipText("前景颜色");
		foregroundButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\前景色.png"));
		toolBar.add(foregroundButton);
		toolBar.addSeparator();//添加分割条
		
//		shapeButton = new JButton("图形");
		shapeButton = new JButton();
		shapeButton.setToolTipText("图形");
		shapeButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\形状.png"));
		toolBar.add(shapeButton);
		
//		clearButton = new JButton("清除");
		clearButton = new JButton();
		clearButton.setToolTipText("清除");
		clearButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\清除.png"));
		toolBar.add(clearButton);
		
		eraserButton = new JButton("橡皮");
		eraserButton = new JButton();
		eraserButton.setToolTipText("橡皮");
		eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\橡皮.png"));
		toolBar.add(eraserButton);
		
		
		//创建菜单
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu systemMenu = new JMenu("系统");
		menuBar.add(systemMenu);
		saveMenuItem = new JMenuItem("保存");
		systemMenu.add(saveMenuItem);
		systemMenu.addSeparator();
		exitMenuItem = new JMenuItem("退出");
		systemMenu.add(exitMenuItem);
		systemMenu.addSeparator();
		shuiyinMenuItem = new JMenuItem("水印");
		systemMenu.add(shuiyinMenuItem);
		
		JMenu strokeMenu = new JMenu("线型");
		menuBar.add(strokeMenu);
		strokeMenuItem1 = new JMenuItem("细线");
		strokeMenu.add(strokeMenuItem1);
		strokeMenu.addSeparator();
		strokeMenuItem2 = new JMenuItem("粗线");
		strokeMenu.add(strokeMenuItem2);
		strokeMenu.addSeparator();
		strokeMenuItem3 = new JMenuItem("较粗");
		strokeMenu.add(strokeMenuItem3);
		
		JMenu colorMenu = new JMenu("颜色");
		menuBar.add(colorMenu);
		foregroundMenuItem = new JMenuItem("前景颜色");
		colorMenu.add(foregroundMenuItem);
		colorMenu.addSeparator();
		backgroundMenuItem = new JMenuItem("背景颜色");
		colorMenu.add(backgroundMenuItem);
		
		JMenu editMenu = new JMenu("编辑");
		menuBar.add(editMenu);
		clearMenuItem = new JMenuItem("清除");
		editMenu.add(clearMenuItem);
		editMenu.addSeparator();
		eraserMenuItem = new JMenuItem("橡皮");
		editMenu.add(eraserMenuItem);
			
		picWindow = new PictureWindow(DrawPictureFrame.this);
		
	}//init()结束
	
	private void addListener() {
		//画板添加鼠标移动事件监听
		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(rubber) {
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.createImage("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\鼠标橡皮.png");
					Cursor c = kit.createCustomCursor(img, new Point(0,0), "clear");
					setCursor(c);
				}else {
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			}
			@Override
			public void mouseDragged(final MouseEvent e) {
					if(x>0&&y>0) {
						if(rubber) {
							g.setColor(backgroundColor);
							g.fillRect(x, y, 10, 10);
						}else {
							g.drawLine(x, y, e.getX(), e.getY());
						}
					}
					x = e.getX();
					y = e.getY();
					canvas.repaint();//更新画布
			}
		});
		//鼠标抬起
		canvas.addMouseListener(new MouseAdapter() {
			public void mouseReleased(final MouseEvent arg0) {
				x = -1;
				y = -1;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(drawShape) {
					switch (shape.getType()) {
					case Shapes.YUAN:
						int yuanX = e.getX() - shape.getWidth() / 2;
						int yuanY = e.getY() - shape.getWidth() / 2;
						
						Ellipse2D yuan = new Ellipse2D.Double(yuanX,yuanY,shape.getWidth(),shape.getHeigth());
						g.draw(yuan);
						break;

					case Shapes.FANG:
						int fangX = e.getX() - shape.getWidth() / 2;
						int fangY = e.getY() - shape.getHeigth() / 2;
						
						Rectangle2D fang = new Rectangle2D.Double(fangX,fangY,shape.getWidth(),shape.getHeigth());
						g.draw(fang);
						break;
					
					}
					canvas.repaint();
					drawShape = false;
				}
			}
			
		});
		
		strokeButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		strokeButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicStroke bs = new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		strokeButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicStroke bs = new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		
		backgroundButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				Color bgColor =  JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if(bgColor != null) {
					backgroundColor = bgColor;
				}
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		foregroundButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color fColor =  JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if(fColor != null) {
					foreColor = fColor;
				}
				foregroundButton.setBackground(foreColor);
				g.setColor(foreColor);
			}
		});
		//清除按钮
		clearButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		//橡皮按钮
		eraserButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
//				if(eraserButton.getText().equals("橡皮")) {
//					rubber = true;
//					eraserMenuItem.setText("画图");
//					eraserButton.setText("画图");
//				}else {
//					rubber = false;
//					eraserMenuItem.setText("橡皮");
//					eraserButton.setText("橡皮");
//					g.setColor(foreColor);
//				}
				
				
				if(rubber) {
					eraserButton.setToolTipText("橡皮");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\橡皮.png"));
					eraserMenuItem.setText("橡皮");
					g.setColor(foreColor);
					rubber = false;
				}else {
					eraserMenuItem.setText("画图");
					eraserButton.setToolTipText("画图");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\画笔.png"));
					g.setColor(backgroundColor);
					rubber = true;
				}
				
				
			}
		});
		
		shapeButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				ShapeWindow shapeWindow = new ShapeWindow(DrawPictureFrame.this);
				int shapeButtonWidth = shapeButton.getWidth();
				int shapeWindowWidth = shapeWindow.getWidth();
				int shapeButtonX = shapeButton.getX();
				int shapeButtonY = shapeButton.getY();
				
				int shapeWindowX = getX() + shapeButtonX -(shapeWindowWidth - shapeButtonWidth)/2;
				int shapeWindowY = getY() + shapeButtonY +80;
				
				shapeWindow.setLocation(shapeWindowX,shapeButtonY);
				
				shapeWindow.setVisible(true);
			}
		});
		
		saveButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});
		
		//系统菜单监听
		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addWatermark();
				DrawImageUtil.saveImage(DrawPictureFrame.this, image);
			}
		});
		
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		strokeMenuItem1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		strokeMenuItem2.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicStroke bs = new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
				g.setStroke(bs);
			}
		});
		strokeMenuItem3.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				BasicStroke bs = new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
				g.setStroke(bs);		
			}
		});
		foregroundMenuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				Color fColor =  JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if(fColor != null) {
					foreColor = fColor;
				}
				foregroundButton.setBackground(foreColor);
				g.setColor(foreColor);
			}
		});
		backgroundMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color bgColor =  JColorChooser.showDialog(DrawPictureFrame.this, "选择颜色对话框", Color.CYAN);
				if(bgColor != null) {
					backgroundColor = bgColor;
				}
				backgroundButton.setBackground(backgroundColor);
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		clearMenuItem.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		eraserMenuItem.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
//				if(eraserButton.getText().equals("橡皮")) {
//					rubber = true;
//					eraserMenuItem.setText("画图");
//					eraserButton.setText("画图");
//				}else {
//					rubber = false;
//					eraserMenuItem.setText("橡皮");
//					eraserButton.setText("橡皮");
//					g.setColor(foreColor);
//				}
				
				
				if(rubber) {
					eraserButton.setToolTipText("橡皮");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\橡皮.png"));
					eraserMenuItem.setText("橡皮");
					g.setColor(foreColor);
					rubber = false;
				}else {
					eraserMenuItem.setText("画图");
					eraserButton.setToolTipText("画图");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\画笔.png"));
					g.setColor(backgroundColor);
					rubber = true;
				}
				
				
			}
		});
		
		//水印
		shuiyinMenuItem.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				shuiyin = JOptionPane.showInputDialog(DrawPictureFrame.this,"你想添加什么水印？");
				if(null == shuiyin) {
					shuiyin = "";
				}else {
					setTitle("画图程序(水印内容：["+shuiyin+"]");
				}
			}
		});
		
		toolBar.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		
		showPicButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = picWindow.isVisible();
				if(isVisible) {
//					showPicButton.setText("展开简笔画");
					showPicButton.setToolTipText("展开简笔画");
					showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\展开.png"));
					picWindow.setVisible(false);
				}else {
//					showPicButton.setText("隐藏窗口");
					showPicButton.setToolTipText("隐藏窗口");
					showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\隐藏.png"));
					picWindow.setLocation(getX() - picWindow.getWidth() - 5,getY());
					picWindow.setVisible(true);
				}
			}
		});
	}
	
	
	public void initShowPicButton(){
//		showPicButton.setText("展开简笔画");
		showPicButton.setToolTipText("展开简笔画");
		showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\一起来画画\\src\\img\\icon\\展开.png"));
	}
	
	private void addWatermark() {
		if(!"".equals((shuiyin).trim())) {
			g.rotate(Math.toRadians(-30));
			Font font = new Font("楷体",Font.BOLD,72);
			g.setFont(font);
			g.setColor(Color.GRAY);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.4f);
			g.setComposite(alpha);
			g.drawString(shuiyin, 150, 500);
			
			canvas.repaint();
			
			g.rotate(Math.toRadians(30));
			alpha = AlphaComposite.SrcOver.derive(1f);
			g.setComposite(alpha);
			g.setColor(foreColor);
		}
	}
	
	
	public static void main(String[] args) {
		DrawPictureFrame frame = new DrawPictureFrame();
		frame.setVisible(true);
	}

	@Override
	public void getShape(Shapes shape) {
		this.shape = shape;
		drawShape = true;
	}
}
