package effectless;

import java.awt.Color;
import java.awt.Graphics;

public class RoundVarThickBorder extends VarThickBorder
{
	private int outerArch, innerArch;
	public RoundVarThickBorder(int x, int y, int width, int height,
			Color borderColor, Color fillColor, int thickness,
			int outerArch, int innerArch)
	{
		super(x, y, width, height, borderColor, fillColor, thickness);
		this.outerArch = outerArch; this.innerArch = innerArch;
	}
	
	public void draw(Graphics g, Color newBorderColor)
	{
		g.setColor(newBorderColor);
		g.fillRoundRect(border.x, border.y, border.width, border.height, outerArch, outerArch);
		g.setColor(fillColor);
		g.fillRoundRect(fill.x, fill.y, fill.width, fill.height, innerArch, innerArch);
	}
}
