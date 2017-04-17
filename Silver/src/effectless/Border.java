package effectless;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Border implements Drawable
{
	protected final Rectangle border;
	public Rectangle getRectangle() { return border; }
	protected final Color borderColor;
	public Border(int x, int y, int width, int height, Color borderColor)
	{
		border = new Rectangle(x, y, width, height);
		this.borderColor = borderColor;
	}
	
	public void draw(Graphics2D g) { draw(g, borderColor); }
	public void draw(Graphics2D g, Color newBorderColor)
	{
		g.setColor(newBorderColor);
		g.drawRect(border.x, border.y, border.width, border.height);
	}
}
