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
import java.awt.geom.Ellipse2D;//������
import java.awt.geom.Rectangle2D;//Բ
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
	
	//�����Ƶ��������
	int x = -1;
	int y = -1;
	boolean rubber = false;//��Ƥ��־����
	
	//��ť��
	private JToolBar toolBar;//������
	private JToggleButton strokeButton1;//ϸ�߰�ť
	private JToggleButton strokeButton2;//���߰�ť
	private JToggleButton strokeButton3;//����߰�ť
	private JButton backgroundButton;//������ɫ��ť
	private JButton foregroundButton;//ǰ����ɫ��ť
	private JButton clearButton;//�����ť
	private JButton eraserButton;//��Ƥ��ť
	private JButton saveButton;//���水ť
	private JButton shapeButton;//ͼ�ΰ�ť
	
	//�˵���
	private JMenuItem strokeMenuItem1;//ϸ�߲˵�
	private JMenuItem strokeMenuItem2;//���߲˵�
	private JMenuItem strokeMenuItem3;//����߲˵�
	private JMenuItem foregroundMenuItem;//ǰ����ɫ�˵�
	private JMenuItem backgroundMenuItem;//������ɫ�˵�
	private JMenuItem clearMenuItem;//����˵�
	private JMenuItem eraserMenuItem;//��Ƥ�˵�
	private JMenuItem saveMenuItem;//����˵�
	private JMenuItem exitMenuItem;//�˳��˵�
	
	//ˮӡ�ַ���
	private String shuiyin = "";
	private JMenuItem shuiyinMenuItem;
	
	private PictureWindow picWindow;
	private JButton showPicButton;
	
	boolean drawShape = false;//��ͼ�α�־����
	Shapes shape;//���Ƶ�ͼ��
	
	
	public DrawPictureFrame() {
		setResizable(false);
		setTitle("��ͼ����(ˮӡ���ݣ�[)"+shuiyin+"]");
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
		
		//��ť
		toolBar = new JToolBar();
		getContentPane().add(toolBar,BorderLayout.NORTH);
		
//		showPicButton = new JButton("չ����ʻ�");
		showPicButton = new JButton();
		showPicButton.setToolTipText("չ����ʻ�");
		showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\չ��.png"));
		toolBar.add(showPicButton);
//		saveButton = new JButton("����");
		saveButton = new JButton();
		saveButton.setToolTipText("����");
		saveButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\����.png"));
		toolBar.add(saveButton);
		toolBar.addSeparator();//��ӷָ���
		
//		strokeButton1 = new JToggleButton("ϸ��");
		strokeButton1 = new JToggleButton();
		strokeButton1.setToolTipText("ϸ��");
		strokeButton1.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\1��������.png"));	
		strokeButton1.setSelected(true);//ϸ�߰�ť���ڱ�ѡ��״̬
		toolBar.add(strokeButton1);//��������Ӱ�ť
//		strokeButton2 = new JToggleButton("����");
		strokeButton2 = new JToggleButton();
		strokeButton2.setToolTipText("����");
		strokeButton2.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\2��������.png"));	
		toolBar.add(strokeButton2);//��������Ӱ�ť
//		strokeButton3 = new JToggleButton("�ϴ�");
		strokeButton3 = new JToggleButton();
		strokeButton3.setToolTipText("�ϴ�");
		strokeButton3.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\4��������.png"));
		toolBar.add(strokeButton3);//��������Ӱ�ť
		ButtonGroup strokeGroup = new ButtonGroup();
		strokeGroup.add(strokeButton1);
		strokeGroup.add(strokeButton2);
		strokeGroup.add(strokeButton3);
		toolBar.addSeparator();//��ӷָ���
		
//		backgroundButton = new JButton("������ɫ");
		backgroundButton = new JButton();
		backgroundButton.setToolTipText("������ɫ");
		backgroundButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\����ɫ.png"));
		toolBar.add(backgroundButton);
		
//		foregroundButton = new JButton("ǰ����ɫ");
		foregroundButton = new JButton();
		foregroundButton.setToolTipText("ǰ����ɫ");
		foregroundButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\ǰ��ɫ.png"));
		toolBar.add(foregroundButton);
		toolBar.addSeparator();//��ӷָ���
		
//		shapeButton = new JButton("ͼ��");
		shapeButton = new JButton();
		shapeButton.setToolTipText("ͼ��");
		shapeButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\��״.png"));
		toolBar.add(shapeButton);
		
//		clearButton = new JButton("���");
		clearButton = new JButton();
		clearButton.setToolTipText("���");
		clearButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\���.png"));
		toolBar.add(clearButton);
		
		eraserButton = new JButton("��Ƥ");
		eraserButton = new JButton();
		eraserButton.setToolTipText("��Ƥ");
		eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\��Ƥ.png"));
		toolBar.add(eraserButton);
		
		
		//�����˵�
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu systemMenu = new JMenu("ϵͳ");
		menuBar.add(systemMenu);
		saveMenuItem = new JMenuItem("����");
		systemMenu.add(saveMenuItem);
		systemMenu.addSeparator();
		exitMenuItem = new JMenuItem("�˳�");
		systemMenu.add(exitMenuItem);
		systemMenu.addSeparator();
		shuiyinMenuItem = new JMenuItem("ˮӡ");
		systemMenu.add(shuiyinMenuItem);
		
		JMenu strokeMenu = new JMenu("����");
		menuBar.add(strokeMenu);
		strokeMenuItem1 = new JMenuItem("ϸ��");
		strokeMenu.add(strokeMenuItem1);
		strokeMenu.addSeparator();
		strokeMenuItem2 = new JMenuItem("����");
		strokeMenu.add(strokeMenuItem2);
		strokeMenu.addSeparator();
		strokeMenuItem3 = new JMenuItem("�ϴ�");
		strokeMenu.add(strokeMenuItem3);
		
		JMenu colorMenu = new JMenu("��ɫ");
		menuBar.add(colorMenu);
		foregroundMenuItem = new JMenuItem("ǰ����ɫ");
		colorMenu.add(foregroundMenuItem);
		colorMenu.addSeparator();
		backgroundMenuItem = new JMenuItem("������ɫ");
		colorMenu.add(backgroundMenuItem);
		
		JMenu editMenu = new JMenu("�༭");
		menuBar.add(editMenu);
		clearMenuItem = new JMenuItem("���");
		editMenu.add(clearMenuItem);
		editMenu.addSeparator();
		eraserMenuItem = new JMenuItem("��Ƥ");
		editMenu.add(eraserMenuItem);
			
		picWindow = new PictureWindow(DrawPictureFrame.this);
		
	}//init()����
	
	private void addListener() {
		//�����������ƶ��¼�����
		canvas.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(rubber) {
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.createImage("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\�����Ƥ.png");
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
					canvas.repaint();//���»���
			}
		});
		//���̧��
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
				Color bgColor =  JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
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
				Color fColor =  JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
				if(fColor != null) {
					foreColor = fColor;
				}
				foregroundButton.setBackground(foreColor);
				g.setColor(foreColor);
			}
		});
		//�����ť
		clearButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				g.setColor(backgroundColor);
				g.fillRect(0, 0, 570, 390);
				g.setColor(foreColor);
				canvas.repaint();
			}
		});
		//��Ƥ��ť
		eraserButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
//				if(eraserButton.getText().equals("��Ƥ")) {
//					rubber = true;
//					eraserMenuItem.setText("��ͼ");
//					eraserButton.setText("��ͼ");
//				}else {
//					rubber = false;
//					eraserMenuItem.setText("��Ƥ");
//					eraserButton.setText("��Ƥ");
//					g.setColor(foreColor);
//				}
				
				
				if(rubber) {
					eraserButton.setToolTipText("��Ƥ");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\��Ƥ.png"));
					eraserMenuItem.setText("��Ƥ");
					g.setColor(foreColor);
					rubber = false;
				}else {
					eraserMenuItem.setText("��ͼ");
					eraserButton.setToolTipText("��ͼ");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\����.png"));
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
		
		//ϵͳ�˵�����
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
				Color fColor =  JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
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
				Color bgColor =  JColorChooser.showDialog(DrawPictureFrame.this, "ѡ����ɫ�Ի���", Color.CYAN);
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
//				if(eraserButton.getText().equals("��Ƥ")) {
//					rubber = true;
//					eraserMenuItem.setText("��ͼ");
//					eraserButton.setText("��ͼ");
//				}else {
//					rubber = false;
//					eraserMenuItem.setText("��Ƥ");
//					eraserButton.setText("��Ƥ");
//					g.setColor(foreColor);
//				}
				
				
				if(rubber) {
					eraserButton.setToolTipText("��Ƥ");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\��Ƥ.png"));
					eraserMenuItem.setText("��Ƥ");
					g.setColor(foreColor);
					rubber = false;
				}else {
					eraserMenuItem.setText("��ͼ");
					eraserButton.setToolTipText("��ͼ");
					eraserButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\����.png"));
					g.setColor(backgroundColor);
					rubber = true;
				}
				
				
			}
		});
		
		//ˮӡ
		shuiyinMenuItem.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				shuiyin = JOptionPane.showInputDialog(DrawPictureFrame.this,"�������ʲôˮӡ��");
				if(null == shuiyin) {
					shuiyin = "";
				}else {
					setTitle("��ͼ����(ˮӡ���ݣ�["+shuiyin+"]");
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
//					showPicButton.setText("չ����ʻ�");
					showPicButton.setToolTipText("չ����ʻ�");
					showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\չ��.png"));
					picWindow.setVisible(false);
				}else {
//					showPicButton.setText("���ش���");
					showPicButton.setToolTipText("���ش���");
					showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\����.png"));
					picWindow.setLocation(getX() - picWindow.getWidth() - 5,getY());
					picWindow.setVisible(true);
				}
			}
		});
	}
	
	
	public void initShowPicButton(){
//		showPicButton.setText("չ����ʻ�");
		showPicButton.setToolTipText("չ����ʻ�");
		showPicButton.setIcon(new ImageIcon("D:\\Myself\\java\\Project\\Draw\\һ��������\\src\\img\\icon\\չ��.png"));
	}
	
	private void addWatermark() {
		if(!"".equals((shuiyin).trim())) {
			g.rotate(Math.toRadians(-30));
			Font font = new Font("����",Font.BOLD,72);
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
