package input;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import effectless.Drawable;

public class RadioButtonGroup implements Drawable
{
	private ArrayList<Button> buttons;
	
	public RadioButtonGroup() { buttons = new ArrayList<Button>(); }
	public RadioButtonGroup(Button...rb)
	{
		buttons = new ArrayList<Button>(Arrays.asList(rb));
	}
	
	public void press(int x, int y)
	{
		for (Button rb : buttons)
			rb.press(x, y);
	}
	
	@Override
	public void draw(Graphics2D g) { for (Button rb : buttons) rb.draw(g); }
}
