package utility;

public class AdvUpdateloop extends Updateloop
{
	long beforeUpdate, updateTime;
	
	public AdvUpdateloop(Updatable u, double ups)
	{
		super(u, ups);
	}
	@Override
	public void run()
	{
		while (animator != null)
		{
			beforeUpdate = System.currentTimeMillis();
			u.update();
			updateTime = System.currentTimeMillis() - beforeUpdate;
			if ((1000/ups) - updateTime <= 0)
				continue;
			try { Thread.sleep((long)(1000/ups) - updateTime); }
			catch (InterruptedException e)
			{
				System.err.println
				("animator thread (run loop in "+getClass()+") was interrupted");
				break;
			}
		}
	}
}
