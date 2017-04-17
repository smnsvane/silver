package input;

import java.awt.Color;
import java.awt.Font;

public class CheckboxButton extends PluckButton
{
	public CheckboxButton(String text, int x, int y, int width, int height,
			Color borderColor, Color bkGround, Font font)
	{
		super(text, x, y, width, height, borderColor, bkGround, font);
	}
	@Override
	public void whenPressed() { setPressed(!isPressed()); }
}
