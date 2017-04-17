package input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import effectless.Anchor;
import effectless.Drawable;
import effectless.Label;

public class STextField implements Drawable
{
	private Label textLabel;
	
	public STextField(String initialText, int x, int y,
			Color textColor, Font font, Anchor anchor)
	{
		textLabel = new Label(initialText, x, y, textColor, font, anchor);
		textLabel.setVisibleBounds(true);
	}
	
	public void clear() { textLabel.setText(""); }
	public void deleteLastChar()
	{
		textLabel.setText
		(
			textLabel.getText().substring(0, textLabel.getText().length() - 1)
		);
	}
	public void addChar(char character)
	{
		textLabel.setText(textLabel.getText() + character);
	}
	public void setText(String text)
	{
		textLabel.setText(text);
	}
	
	@Override
	public void draw(Graphics2D g2d)
	{
		textLabel.draw(g2d);
	}
}
