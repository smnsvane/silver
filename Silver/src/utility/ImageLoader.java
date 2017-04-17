package utility;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageLoader
{
	public final String IMAGE_DIR;
	
	private HashMap<String, List<String>> imageGroupFrameNames =
		new HashMap<String, List<String>>();
	public List<String> getImageFrameNames(String imageGroupName)
	{
		return imageGroupFrameNames.get(imageGroupName);
	}
	
	private HashMap<String, List<Image>> images =
		new HashMap<String, List<Image>>();
	public Image getImage(String imageName)
	{
		return getImage(imageName, 0);
	}	
	public Image getImage(String imageName, int imageNumber)
	{
		return images.get(imageName).get(imageNumber);
	}
	public List<Image> getImageSequence(String imageName)
	{
		return images.get(imageName);
	}
	public void storeImageSequence(String imageName, List<Image> imageList)
	{
		// image name may be a filename, if so the extension is removed
		String imageSequenceName = imageName.split("\\.")[0];
		if (images.containsKey(imageSequenceName))
			throw new RuntimeException(
					"image by this name already loaded "+imageSequenceName);
		images.put(imageSequenceName, imageList);
	}
	
	private MediaTracker mt;
	
	public ImageLoader(String imageDirectory, Component comp)
	{
		IMAGE_DIR = imageDirectory;
		mt = new MediaTracker(comp);
	}
	
	/* **************************
	 * SIMPLE IMAGE LOAD METHODS
	 ****************************/
	
	public Image loadImage(String imageFilename)
	{
		Image image = Toolkit.getDefaultToolkit().createImage(imageFilename);
		mt.addImage(image, 0);
		try { mt.waitForAll(); }
		catch (InterruptedException e) { e.printStackTrace(); }
		return image;
	}
	public List<Image> loadImageSequence(List<String> imageFilenames)
	{
		List<Image> list = new ArrayList<Image>();
		for (int i = 0; i < imageFilenames.size(); i++)
		{
			Image image =
				Toolkit.getDefaultToolkit().createImage(imageFilenames.get(i));
			mt.addImage(image, 0);
			list.add(image);
		}
		try { mt.waitForAll(); }
		catch (InterruptedException e) { e.printStackTrace(); }
		return list;
	}
	
	/* ***************************************
	 * LOAD IMAGES FROM IMAGE DISCRIPTOR FILE 
	 *****************************************/
	
	public void loadImageFromDescriptionFile(String descriptorFilename)
	{
		String path = IMAGE_DIR + descriptorFilename;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String line;
			while ((line = br.readLine()) != null)
			{
				if (line.length() == 0)
					// blank line
					continue;
				if (line.startsWith("//"))
					// line containing a comment
					continue;
				String[] lineSplit = line.split("\\s");
				String linePrefix = lineSplit[0].toLowerCase();
				if (linePrefix.equals("o"))
					// a single image
					createSingleImage(lineSplit[1]);
				else if (linePrefix.equals("n"))
					// a numbered sequence of images
					createImageGroupFromNumberedImages(lineSplit[1],
							Integer.parseInt(lineSplit[2]));
				else if (linePrefix.equals("s"))
					// an images strip
					createImagesFromImageStrip(lineSplit[1],
							Integer.parseInt(lineSplit[2]));
				else if (linePrefix.equals("g"))
					// a group of images
					createImageGroupFromImageList(lineSplit);
				else if (linePrefix.equals("a"))
					// a 2d image strip
					createImagesFromImage2dStrip(lineSplit[1],
							Integer.parseInt(lineSplit[2]),
							Integer.parseInt(lineSplit[3]));
				else
					System.out.println("Do not recognize line: " + line);
			}
			br.close();
		}
		catch (IOException e)
		{
			System.err.println("Error reading file: " + path);
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/* **********************************************************************
	 * METHODS USED TO LOAD SPECIFIC IMAGE DESCRIPTIONS FROM DESCRIPTOR FILE
	 ************************************************************************/
	
	public void createSingleImage(String imageFilename)
	{
		Image image = loadImage(imageFilename);
		List<Image> list = new ArrayList<Image>();
		list.add(image);
		storeImageSequence(imageFilename, list);
	}
	public void createImageGroupFromNumberedImages(String imageFilenameExpression,
			int numberOfImages)
	{
		String[] filenameFactions = imageFilenameExpression.split("#");
		// check that filename contain only one *
		if (filenameFactions.length != 2)
			throw new RuntimeException(
					"encountered filename not having exactly one # in it");
		List<String> imageFilenames = new ArrayList<String>(numberOfImages);
		// substitute * with integer
		for (int i = 1; i <= numberOfImages; i++)
			imageFilenames.add(filenameFactions[0]+i+filenameFactions[1]);
		// load images
		List<Image> images = loadImageSequence(imageFilenames);
		// removal of * from filename
		String generelFilename = filenameFactions[0]+filenameFactions[1];
		// store image sequence
		storeImageSequence(generelFilename, images);
	}
	public void createImagesFromImageStrip(String imageFilename, int numberOfImages)
	{
		if (numberOfImages <= 1)
			throw new RuntimeException("numberOfImages <= 1");
		// load strip image file
		Image stripImage = loadImage(imageFilename);
		// calculate frame width and height
		int frameWidth = stripImage.getWidth(null) / numberOfImages;
		int frameHeight = stripImage.getHeight(null);
		// creating an image array to contain the frames
		List<Image> frames = new ArrayList<Image>(numberOfImages);
		// extract frames from strip image
		for (int i = 0; i < numberOfImages; i++)
		{
			BufferedImage image =
				new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = image.createGraphics();
			// create frame
			g2d.drawImage(stripImage,
					// destination (write) rectangle
					0, 0, frameWidth, frameHeight,
					// read rectangle
					i * frameWidth, 0, (i * frameWidth) + frameWidth, frameHeight,
					null);
			g2d.dispose();
			frames.add(image);
		}
		// store frames
		storeImageSequence(imageFilename, frames);
	}
	private void createImageGroupFromImageList(String... imageGroupName)
	{
		// note: imageGroupName[0] = "g"
		if (imageGroupName.length < 3)
			throw new RuntimeException("no filenames found");
		// load images
		List<Image> images = new ArrayList<Image>(imageGroupName.length - 2);
		List<String> imageNames = new ArrayList<String>(imageGroupName.length - 2);
		for (int i = 2; i < imageGroupName.length; i++)
		{
			images.add(loadImage(imageGroupName[i]));
			imageNames.add(imageGroupName[i]);
		}
		// store image group
		storeImageSequence(imageGroupName[1], images);
		// store image names
		if (imageGroupFrameNames.containsKey(imageGroupName[1]))
			throw new RuntimeException(
					"image group by this name already loaded "+imageGroupName[1]);
		imageGroupFrameNames.put(imageGroupName[1], imageNames);
	}
	private void createImagesFromImage2dStrip(String imageFilename,
			int frameColumns, int frameRows)
	{
		if (frameRows < 1 || frameColumns < 1)
			throw new RuntimeException("frameRows < 1 or frameColumns < 1");
		// load animation image file
		Image image = loadImage(imageFilename);
		// calculate frame width and height
		int frameWidth = image.getWidth(null) / frameColumns;
		int frameHeight = image.getHeight(null) / frameRows;
		// creating an image list to contain the frames
		List<Image> frames = new ArrayList<Image>(frameColumns*frameRows);
		// extract frames from animation image
		for (int j = 0; j < frameRows; j++)
			for (int i = 0; i < frameColumns; i++)
			{
				BufferedImage imageBuf =
					new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = imageBuf.createGraphics();
				// create frame
				g2d.drawImage(image,
						// destination (write) rectangle
						0, 0, frameWidth, frameHeight,
						// read rectangle
						i * frameWidth, j * frameHeight,
						i * frameWidth + frameWidth,
						j * frameHeight + frameHeight,
						null);
				g2d.dispose();
				frames.add(imageBuf);
			}
		// store frames
		storeImageSequence(imageFilename, frames);
	}
}
