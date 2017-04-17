package effectless;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class VarThickBorder extends Border
{
	protected final Rectangle fill;
	protected final Color fillColor;
	public VarThickBorder(int x, int y, int width, int height,
			Color borderColor, Color fillColor, int thickness)
	{
		super(x, y, width, height, borderColor);
		fill = new Rectangle(x+thickness, y+thickness,
								width-2*thickness, height-2*thickness);
		this.fillColor = fillColor;
	}
	
	public void draw(Graphics g, Color newBorderColor)
	{
		g.setColor(newBorderColor);
		g.fillRect(border.x, border.y, border.width, border.height);
		g.setColor(fillColor);
		g.fillRect(fill.x, fill.y, fill.width, fill.height);
	}
}
