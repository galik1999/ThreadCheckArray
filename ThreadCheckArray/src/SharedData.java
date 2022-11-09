import java.util.ArrayList;

/**
 * 
 * @author galmu
 *	class which is shared with both threads.
 */
public class SharedData 
{
	private ArrayList<Integer> array;/**input array which is searched */
	private boolean [] winArray;/**array of indexes of the chosen elements*/
	private boolean flag;/**variable which tells one of the threads to stop searching 
	when he finds the resulting target*/
	private final int b;/**the target we are searching in the array. */
	
	/**
	 * constructor for the array and the last element of the array.
	 */
	public SharedData(ArrayList<Integer> array, int b) {
		this.array = array;
		this.b = b;
	}

	/** @return the array of indexes of the elements that participate in the sum = target.*/
	public boolean[] getWinArray() 
	{
		return winArray;
	}

	/** constructor for the winArray*/
	public void setWinArray(boolean [] winArray) 
	{
		this.winArray = winArray;
	}

	/** @return getter for the input array*/
	public ArrayList<Integer> getArray()
	{
		return array;
	}

	/**@return getter for the last element of the array.*/
	public int getB() 
	{
		return b;
	}

	/**@return getter for the flag.*/
	public boolean getFlag() 
	{
		return flag;
	}

	/**setter for the flag.*/
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
