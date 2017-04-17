package framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable
{
	private static final long	serialVersionUID	= 1L;

	private static final int	PWIDTH				= 500;		// size of panel
	private static final int	PHEIGHT				= 400;

	private Thread				animator;						// for the animation
	private volatile boolean	running				= false;	// stops the animation

	private volatile boolean	gameOver			= false;	// for game termination

	public GamePanel()
	{
		setBackground(Color.white); // white background
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

		setFocusable(true);
		requestFocus(); // JPanel now receives key events
		readyForTermination();
		
		// TODO create game components
		
		// listen for mouse presses
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				testPress(e.getX(), e.getY());
			}
		});

	}

	private void readyForTermination()
	{
		addKeyListener(new KeyAdapter()
		{
			// listen for esc, q, end, ctrl-c
			public void keyPressed(KeyEvent e)
			{
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ESCAPE ||
						keyCode == KeyEvent.VK_Q ||
						keyCode == KeyEvent.VK_END ||
						(keyCode == KeyEvent.VK_C) && e.isControlDown())
					running = false;
			}
		});
	}

	private void testPress(int x, int y)
	{
		if (gameOver) return;
		
		// TODO do something
	}
	
	/**
	 * Wait for the JPanel to be added to the
	 * JFrame/JApplet before starting.
	 */
	public void addNotify()
	{
		super.addNotify(); // creates the peer
		startGame(); // start the thread
	}

	/** initialize and start the thread */
	private void startGame()
	{
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	} // end of startGame( )

	public void stopGame()
	// called by the user to stop execution
	{
		running = false;
	}

	private static final int	NO_DELAYS_PER_YIELD	= 16;
	/* Number of frames with a delay of 0 ms before the
	   animation thread yields to other running threads. */

	private static int			MAX_FRAME_SKIPS		= 5;
	// no. of frames that can be skipped in any one animation loop
	// i.e the games state is updated but not rendered

	private int					period				= 20;	//FIXME check up on this
	
	/**
	 * Repeatedly update, render, sleep so loop takes close
	   to period nsecs. Sleep inaccuracies are handled.
	   The timing calculation use the Java 3D timer.

	   Overruns in update/renders will cause extra updates
	   to be carried out so UPS tild== requested FPS
	 */
	public void run()
	{
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;

		beforeTime = System.nanoTime();

		running = true;
		while (running)
		{
			gameUpdate();
			gameRender();
			paintScreen();

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0)
			{
				// some time left in this cycle
				try { Thread.sleep(sleepTime / 1000000L); } // nano -> ms
				catch (InterruptedException ex) {}
				overSleepTime = System.nanoTime() - afterTime - sleepTime;
			}
			else
			{
				// sleepTime <= 0; frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;

				if (++noDelays >= NO_DELAYS_PER_YIELD)
				{
					Thread.yield(); // give another thread a chance to run
					noDelays = 0;
				}
			}

			beforeTime = System.nanoTime();

			/* If frame animation is taking too long, update the game state
			   without rendering it, to get the updates/sec nearer to
			   the required FPS. */
			int skips = 0;
			while (excess > period && skips < MAX_FRAME_SKIPS)
			{
				excess -= period;
				gameUpdate(); // update state but don't render
				skips++;
			}
		}
		System.exit(0);
	}

	/** update game state ... */
	private void gameUpdate()
	{
		if (gameOver)
			return;
		
		// TODO ...
	}
	
	/** actively render the buffer image to the screen */
	private void paintScreen()
	{
		Graphics g;
		try
		{
			g = this.getGraphics(); // get the panel's graphic context
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, 	null);
			Toolkit.getDefaultToolkit().sync(); // sync the display on some systems
			g.dispose();
		}
		catch (Exception e) { System.out.println("Graphics context error: " + e); }
	}

	// global variables for off-screen rendering
	private Graphics	dbg;
	private Image		dbImage	= null;
	
	/** draw the current frame to an image buffer */
	private void gameRender()
	{
		if (dbImage == null)
		{
			// create the buffer
			dbImage = createImage(PWIDTH, PHEIGHT);
			if (dbImage == null)
			{
				System.out.println("dbImage is null");
				return;
			}
			else
				dbg = dbImage.getGraphics();
		}

		// clear the background
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

		// TODO draw game elements

		if (gameOver)
			gameOverMessage(dbg);
	}

	private void gameOverMessage(Graphics g)
	{
		g.drawString("GAME OVER", PWIDTH/2, PHEIGHT/2);
	}
}
