package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import shapes.ShapeFactory;

import effectless.Anchor;
import effectless.Label;
import framework.Viewer;

public class ViewerTest
{
	private static int x = 0, y = 0;
	public static void main(String[] args)
	{
		Viewer v = new Viewer(500, 500)
		{
			Label l = new Label("Test", 50, 50, Color.BLACK,
					new Font("Verdana", Font.PLAIN, 14), Anchor.SOUTH);
			@Override
			public void renderGraphics(Graphics2D g2d)
			{
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, 500, 500);
				
				g2d.setColor(Color.BLUE);
				g2d.fillRect(x, y, 10, 10);
				x++;
				y++;
				
				ShapeFactory.drawPoint(g2d, Color.RED, new Point(50, 50));
				l.setVisibleBounds(true);
				l.draw(g2d);
			}
		};
		v.start();
	}
}
