package shapes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class ShapeFactory
{
	public static void drawPoint(Graphics g, Color c, Point p)
	{
		g.setColor(c);
		g.drawLine(p.x-1, p.y-1, p.x+1, p.y+1);
		g.drawLine(p.x-1, p.y+1, p.x+1, p.y-1);
	}
	public static void drawCircle(Graphics g, Color c, Point center,
			int radius)
	{
		g.setColor(c);
		g.drawOval(center.x-radius, center.y-radius, 2*radius, 2*radius);
	}
	public static void fillCircle(Graphics g, Color c, Point center,
			int radius)
	{
		g.setColor(c);
		g.fillOval(center.x-radius, center.y-radius, 2*radius, 2*radius);
	}
}
