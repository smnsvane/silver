package utility;

public class Timer
{
	private long first, second;
	public void setTime()
	{
		first = System.currentTimeMillis();
	}
	public void getTime()
	{
		second = System.currentTimeMillis();
	}
	public void printTimeDifference()
	{
		System.out.println("Time: "+(second-first)+" miliseconds");
	}
}
