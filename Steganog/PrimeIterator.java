import java.util.ArrayList;
import java.util.Iterator;
import java.math.*;

public class PrimeIterator implements Iterator<Integer>
{
	private int maxNum;
	private int currentIndex;
	private ArrayList<Integer> primes;
	
	public PrimeIterator(int max)
	{
		maxNum = max;
		currentIndex = 0;
		primes = new ArrayList<Integer>();
		setupPrimes();
	}
	
	/**
	 * Uses the Sieve of Eratosthenes to populate the array list of prime numbers up to the max number
	 */
	private void setupPrimes()
	{
		int[] candidates = new int[maxNum+1];
		for(int i = 2; i <= maxNum; i++)
		{
			candidates[i] = i;
		}
		for(int i = 2; i<= Math.floor(Math.sqrt((double)maxNum)); i++)
		{
			if(candidates[i]!=0)
			{
				int j = i*i;
				while(j<=maxNum)
				{
					candidates[j] = 0;
					j = j+i;
				}
			}
		}
		for(int i = 0; i<= maxNum; i++)
		{
			if(candidates[i]!=0)
			{
				primes.add(candidates[i]);
			}
		}
	}
	
	/**
	 * Returns whether this iterator has any more prime numbers less than max
	 */
	@Override
	public boolean hasNext()
	{
		return (currentIndex< primes.size());
	}

	/**
	 * Gets the next prime number
	 */
	@Override
	public Integer next()
	{
		int next = primes.get(currentIndex);
		currentIndex++;
		return next;
	}

		
	@Override
	public void remove()
	{
		// TODO Auto-generated method stub
		
	}
	
}
