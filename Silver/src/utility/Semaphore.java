package utility;

public class Semaphore
{
	private int	s;

	public Semaphore(int s)
	{
		this.s = s;
	}
	
	public synchronized void uP()
	{
		try { P(); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public synchronized void P() throws InterruptedException
	{
		while (s < 1) wait();
		s--;
	}
	
	public synchronized void V()
	{
		s++;
		notify();
	}
	
	public synchronized void hV()
	{
		s = 0;
		V();
	}
}
