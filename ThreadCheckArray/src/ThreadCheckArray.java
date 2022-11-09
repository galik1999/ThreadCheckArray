import java.util.ArrayList;

/**
 * 
 * @author galmu
 * the runnable class which runs the threads.
 * the recursive search is implemented in this class.
 * searching in all subArrays of the array for the subArray with sum = target.
 * when 1 thread finds the result, the other thread stops immidiately.
 * 
 */
public class ThreadCheckArray implements Runnable 
{
	private boolean flag;/**boolean variable which tell one of the threads to stop*/
	private boolean [] winArray;/** array of resulting indexes which participate in the sum*/
	SharedData sd;/**object of shared data between threads*/
	ArrayList<Integer> array;/**input array for searching the sub group of elements.*/
	int b;/**the target we are searching = the sum.*/
	
	/**constructor for the SharedData object: the array and its last element.*/
	public ThreadCheckArray(SharedData sd) 
	{
		this.sd = sd;	
		synchronized (sd) 
		{
			array = sd.getArray();
			b = sd.getB();
		}		
		winArray = new boolean[array.size()];
	}
	
	/**searches all the sub groups of the array for a sub group such that its sum = target.
	 * recursion which consists of two main calls: if we chose the i element of the array and
	 * add it to the sum, or we dont chose the i elemnt to the */
	void rec(int n, int b)
	{
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		if (n == 1)
		{
			if(b == 0 || b == array.get(n-1))
			{
				flag = true;
				synchronized (sd) 
				{
					sd.setFlag(true);
				}			
			}
			if (b == array.get(n-1))
				winArray[n-1] = true;
			return;
		}
		
		rec(n-1, b - array.get(n-1));
		if (flag)
			winArray[n-1] = true;
		synchronized (sd) 
		{
			if (sd.getFlag())
				return;
		}	
		rec(n-1, b);
	}

	/**method to run the threads.*/
	public void run() {
		if (array.size() != 1)
			if (Thread.currentThread().getName().equals("thread1"))
				rec(array.size()-1, b - array.get(array.size() - 1));
			else 
				rec(array.size()-1, b);
		if (array.size() == 1)
			if (b == array.get(0) && !flag)
			{
				winArray[0] = true;
				flag = true;
				synchronized (sd) 
				{
					sd.setFlag(true);
				}
			}
		if (flag)
		{
			if (Thread.currentThread().getName().equals("thread1"))
				winArray[array.size() - 1] = true;
			synchronized (sd) 
			{
				sd.setWinArray(winArray);
			}	
		}
	}
}
