package utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Imagecreator
{
	public static void main(String[] args)
	{
		// Set image parameters
		
		String filename = "fighter"; // will be followed with '.jpg'
		int image_pixel_width = 10;
		int image_pixel_height = 15;
		
		// End of image parameters
		
		BufferedImage buf = new BufferedImage(image_pixel_width, image_pixel_height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = buf.createGraphics();
		
		// Draw image here
		
		int[] xpoints = {5, 10, 0};
		int[] ypoints = {0, 15, 15};
		Polygon p = new Polygon(xpoints, ypoints, xpoints.length);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(p);
		
		// End of image drawing
		
		saveImage(buf, filename);
		g2d.dispose();
	}
	
	public static void saveImage(BufferedImage buf, String filename)
	{
		try { ImageIO.write(buf, "jpeg", new File(filename+".jpg")); }
		catch (IOException e) { e.printStackTrace(); }
	}
}
