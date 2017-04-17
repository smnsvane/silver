package shapes;

import java.awt.Graphics;
import java.awt.Point;

public class Vector
{
	private double x, y;
	public double getX() { return x; }
	public double getY() { return y; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public double getLength() { return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); }
	public void setLength(double length)
	{
		double angle = getAngle();
		x = 0.0; y = length;
		setAngle(angle);
	}
	public void alterLength(double factor) { x *= factor; y *= factor; }
	public double getAngle() { return Math.atan2(y, x); }
	/** cannot set angle if x = 0.0 and y = 0.0
	 * setting length first can also solve this problem */
	public void setAngle(double radianAngle)
	{
		double length = getLength();
		x = Math.cos(radianAngle)*length;
		y = Math.sin(radianAngle)*length;
	}
	public void set(Point vectorBase, Point pointed)
	{
		x = pointed.x - vectorBase.x; y = pointed.y - vectorBase.y;
	}
	public Vector() { setX(0.0); setY(0.0); }
	public Vector(int baseX, int baseY, int pointX, int pointY)
	{
		setX(pointX - baseX);
		setY(pointY - baseY);
	}
	public Vector(Point vectorBase, Point pointed) { set(vectorBase, pointed); }
	/** if polarCoord arg0 = length, arg1 = radian angle
	 *  else arg0 = x, arg1 = y */
	public Vector(double arg0, double arg1, boolean polarCoordinates)
	{
		if (polarCoordinates)
		{
			setX(Math.cos(arg1) * arg0);
			setY(Math.sin(arg1) * arg0);
		}
		else
		{
			setX(arg0);
			setY(arg1);
		}
	}
	public Point toPoint() { return toPoint(new Point()); }
	public Point toPoint(Point vectorBase)
	{
		return new Point((int)(vectorBase.x + x), (int)(vectorBase.y + y));
	}
	public double angleDifference(Vector otherVector)
	{
		return angleDifference(getAngle(), otherVector.getAngle());
	}
	
	@Override
	public boolean equals(Object o)
	{
		Vector v;
		try { v = (Vector) o; }
		catch (ClassCastException e) { return false; }
		return x == v.getX() && y == v.getY();
	}
	@Override
	public Vector clone() { return new Vector(getX(), getY(), false); }
	@Override
	public String toString() { return "Vector ("+x+", "+y+")"; }
	
	public void vectorAddition(double xAdd, double yAdd) { x += xAdd; y += yAdd; }
	public void vectorAddition(Vector vector)
	{
		vectorAddition(vector.getX(), vector.getY());
	}
	public void drawLine(Graphics g, int vectorBaseX, int vectorBaseY)
	{
		g.drawLine(vectorBaseX, vectorBaseY,
				vectorBaseX + (int) getX(), vectorBaseY + (int) getY());
	}
	public void draw(Graphics g, Vector vectorBasePointer)
	{
		draw(g, (int) vectorBasePointer.getX(), (int) vectorBasePointer.getY());
	}
	public void draw(Graphics g, int vectorBaseX, int vectorBaseY)
	{
		drawLine(g, vectorBaseX, vectorBaseY);
		//TODO
//		new Vector(25, getAngle()-0.125*Math.PI).drawLine(g, getPoint(vectorBase));
//		new Vector(15, getAngle()+0.125*Math.PI).drawLine(g, getPoint(vectorBase));
	}
	
	
	public static double angleDifference(double a0, double a1)
	{
		double angleDiff = (a0 - a1) % Math.PI;
		if (Math.abs(angleDiff) > Math.PI)
			System.out.println(angleDiff);
		return angleDiff;
	}
	public static void main(String[] args)
	{
		for (int i = 0; i < 10; i++)
		{
			double a0 = Math.random()*2*Math.PI - Math.PI;
			double a1 = Math.random()*2*Math.PI - Math.PI;
			System.out.println(a0+" "+a1+" "+angleDifference(a0, a1));
		}
	}
}
