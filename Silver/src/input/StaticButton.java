package input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import effectless.Anchor;
import effectless.Border;
import effectless.Drawable;
import effectless.Label;

public class StaticButton implements Drawable
{
	private Label label;
	private Border border;
	public Rectangle getRectangle() { return border.getRectangle(); }
	private ActionListener al;
	public void setActionListener(ActionListener al) { this.al = al; }
	
	/**@param text
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param borderColor
	 * @param font */
	public StaticButton(String text, int x, int y, int width, int height,
			Color borderColor, Font font)
	{
		border = new Border(x, y, width, height, borderColor);
		label = new Label(text, x+width/2, y+height/2, Color.BLACK, font, Anchor.CENTER);
	}
	
	public boolean contains(int x, int y) { return getRectangle().contains(x, y); }
	public void press(Point p) { press(p.x, p.y); }
	public void press(int x, int y)
	{
		if (al != null && contains(x, y))
		{
			al.actionPerformed(new ActionEvent(this, 0, label.toString()));
			whenPressed();
		}
	}
	/** override this method for more functionality */
	protected void whenPressed() {}
	
	public void draw(Graphics2D g)
	{
		border.draw(g);
		label.draw(g);
	}
}
