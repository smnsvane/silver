package input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class PluckButton extends StaticButton implements Button
{
	private Color currentbkGround; 
	private Color bkGround;
	private boolean pressed;
	public boolean isPressed() { return pressed; }
	public void setPressed(boolean press) { pressed = press; }
	@Override
	public void whenPressed() { pressed = true; }
	
	public PluckButton(String text, int x, int y, int width, int height,
			Color borderColor, Color bkGround, Font font)
	{
		super(text, x, y, width, height, borderColor, font);
		this.bkGround = bkGround;
		currentbkGround = bkGround;
		pressed = false;
	}
	
	public void highlight(Point p) { highlight(p.x, p.y); }
	public void highlight(int x, int y)
	{
		if (contains(x, y)) currentbkGround = bkGround.brighter();
		else if (!pressed) currentbkGround = bkGround;
		else currentbkGround = bkGround.darker();
	}
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(currentbkGround);
		((Graphics2D) g).fill(getRectangle());
		super.draw(g);
	}
}
