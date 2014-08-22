import java.util.Collections;
import java.util.List;

//import edu.neumont.csc250.lab4.Sorter;


public class QuickSorter<T extends Comparable> //implements Sorter
{

	//@Override
	public void sort(List toBeSorted)
	{
		quickSort(toBeSorted, 0, toBeSorted.size()-1);
	}
	
	void quickSort(List<T> list, int i, int j)
	{
		int pivotIndex = findPivot(list, i, j);
		Collections.swap(list, pivotIndex, j);
		int k = partition(list, i-1, j, list.get(j));
		Collections.swap(list,k,j);
		if((k-i)>1)
		{
			quickSort(list, i, k-1);
		}
		if((j-k)>1)
		{
			quickSort(list, k+1, j);
		}
	}

	int partition(List<T> list, int left, int right, T pivot)
	{
		do
		{
			while(list.get(++left).compareTo(pivot)<0);
			while((right!=0 && list.get(--right).compareTo(pivot)>0));
			Collections.swap(list, left, right);
		}while(left<right);
		Collections.swap(list, left, right);
		return left;
	}
	
	int findPivot(List<T> list, int i, int j)
	{
		return (i+j)/2;
	}
}
