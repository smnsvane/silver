package effectless;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Label implements Drawable
{	
	private String text;
	private Anchor boundsAnchor;
	private Color textColor;
	private Font font;
	
	private Rectangle bounds;
	public boolean boundsContains(Point p) { return bounds.contains(p); }
	private boolean boundsVisible;
	private boolean compiled;
	
	public Label(String text, int x, int y, Color textColor, Font font, Anchor boundsAnchor)
	{
		this.text = text;
		bounds = new Rectangle(x, y, 0, 0);
		this.boundsAnchor = boundsAnchor;
		this.textColor = textColor; this.font = font;
		compiled = false;
	}
	@Override
	public void draw(Graphics2D g2d)
	{
		if (!compiled) compile(g2d);
		g2d.setColor(textColor);
		g2d.setFont(font);
		g2d.drawString(text, bounds.x, bounds.y + bounds.height);
		if (isBoundsVisible()) g2d.draw(bounds);
	}
	private void compile(Graphics2D g2d)
	{
		FontMetrics fm = g2d.getFontMetrics(font);
		bounds.width	= fm.stringWidth(getText());
		bounds.height	= fm.getAscent();
		if (boundsAnchor.equals(Anchor.CENTER)
				|| boundsAnchor.equals(Anchor.SOUTH)
				|| boundsAnchor.equals(Anchor.NORTH))
			bounds.x -= bounds.width / 2;
		else if (boundsAnchor == Anchor.UPPER_RIGHT
				|| boundsAnchor == Anchor.LOWER_RIGHT
				|| boundsAnchor == Anchor.EAST)
			bounds.x -= bounds.width;
		if (boundsAnchor.equals(Anchor.CENTER)
				|| boundsAnchor.equals(Anchor.EAST)
				|| boundsAnchor.equals(Anchor.WEST))
			bounds.y -= bounds.height / 2;
		else if (boundsAnchor.equals(Anchor.LOWER_LEFT)
				|| boundsAnchor.equals(Anchor.LOWER_RIGHT)
				|| boundsAnchor.equals(Anchor.SOUTH))
			bounds.y -= bounds.height;
		compiled = true;
	}
	
	// accessor methods
	public String getText() { return text; }
	public Color getTextColor() { return textColor; }
	public Font getFont() { return font; }
	public boolean isBoundsVisible() { return boundsVisible; }
	
	// mutator methods
	public void setText(String text) { this.text = text; }
	public void setTextColor(Color textColor) { this.textColor = textColor; }
	public void setFont(Font font) { this.font = font; }
	public void setVisibleBounds(boolean visibleBounds) { this.boundsVisible = visibleBounds; }
}
