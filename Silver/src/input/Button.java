package input;

import java.awt.Point;

import effectless.Drawable;

public interface Button extends Drawable
{
	public boolean isPressed();
	public void setPressed(boolean press);
	public void press(int x, int y);
	public void press(Point p);
}
