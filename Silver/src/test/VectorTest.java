package test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import shapes.Vector;
import utility.Updatable;
import utility.Updateloop;

public class VectorTest extends Canvas implements Updatable
{
	private static final long serialVersionUID = 1L;
	
	private JFrame f;
	private Updateloop ul;
	private final BufferedImage backbuffer;
	private final Graphics2D g2d;
	private int width, height;
	
	public static void main(String[] args)
	{
		VectorTest vecTest = new VectorTest(600, 480);
		vecTest.start();
	}
	public void start()
	{
		EventQueue.invokeLater(new createWindow());
		EventQueue.invokeLater(new createUpdateloop());
	}
	private Vector vector;
	public VectorTest(int w, int h)
	{
		backbuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		g2d.setFont(new Font("Verdana", Font.PLAIN, 11));
		setSize(w, h); width = w; height = h;
		vector = new Vector(0, 150, false);
	}
	public VectorTest self() { return this; }
	class createWindow implements Runnable
	{
		public void run()
		{
			f = new JFrame();
			f.add(self());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setTitle("Vector Test");
			f.pack();
			f.setVisible(true);	
		}
	}
	class createUpdateloop implements Runnable
	{
		public void run()
		{
			ul = new Updateloop(self(), 20.0);
			ul.start();
		}	
	}
	private boolean rising = true;
	public void update()
	{
		vector.setAngle(vector.getAngle()+Math.PI/200);
		if (vector.getLength() > 200) rising = false;
		else if (vector.getLength() < 20) rising = true;
		if (rising) vector.alterLength(1.1);
		else vector.alterLength(0.9);
		
		repaint();
	}
	public void update(Graphics g)
	{
		// paint background black
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height);
		
		g2d.setColor(Color.GREEN);
		vector.draw(g2d, 250, 250);
		
		g.drawImage(backbuffer, 0, 0, this);
	}
	public void paint(Graphics g) { update(g); }
}
