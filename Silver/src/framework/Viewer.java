package framework;

import javax.swing.JFrame;

import utility.Updatable;
import utility.Updateloop;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Viewer implements Updatable
{
	private JFrame frame = new JFrame();
	public void setTitle(String title) { frame.setTitle(title); }
	private Canvas canvas =
		new Canvas()
		{
			private static final long serialVersionUID = 1L;
			@Override
			public void update(Graphics g) { paint(g); }
			@Override
			public void paint(Graphics g)
			{
				g.drawImage(imageBuffer, 0, 0, null);
			}
		};
	private BufferedImage imageBuffer;
	private Updateloop ul = new Updateloop(this, 20.0);
	
	public Viewer(int width, int height)
	{
		imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		canvas.setSize(width, height);
		frame.add(canvas);
	}
	
	public abstract void renderGraphics(Graphics2D g2d);
	public void draw() { canvas.repaint(); }
	@Override
	public void update()
	{
		Graphics2D g2d = imageBuffer.createGraphics();
		renderGraphics(g2d);
		g2d.dispose();
		draw();
	}
	public void start()
	{
		EventQueue.invokeLater(windowShow);
		EventQueue.invokeLater(startUpdateloop);
	}
	private Runnable windowShow = new Runnable()
	{
		@Override
		public void run()
		{
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			if (frame.getTitle() == "")
				frame.setTitle(this.getClass().getSimpleName());
			frame.pack();
			frame.setVisible(true);
		}
	};
	private Runnable startUpdateloop = new Runnable()
	{
		@Override
		public void run() { ul.start(); }
	};
}
