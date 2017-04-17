package test;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import utility.Updatable;
import utility.Updateloop;

public abstract class DefaultViewer extends Canvas implements Updatable,
								MouseMotionListener, KeyListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private Updateloop ul;
	protected BufferedImage backBuffer;
	protected Graphics2D g2d;
	protected final int width, height;
	public DefaultViewer(int w, int h)
	{
		width = w; height = h;
		f = new JFrame();
		f.add(this);
		
		setSize(width, height);
		backBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		g2d = backBuffer.createGraphics();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle(this.getClass().getSimpleName());
		f.pack();
		f.setVisible(true);
		
		ul = new Updateloop(this, 20.0);
	}
	protected void start() { ul.start(); }
	@Override
	public abstract void update(Graphics g);
	public void update() { repaint(); }
	@Override
	public void paint(Graphics g) { g.drawImage(backBuffer, 0, 0, this); }
}
