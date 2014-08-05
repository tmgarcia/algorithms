import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.neumont.util.Client;
import edu.neumont.util.QueueableService;


public class GroceryStore implements QueueableService
{
	
	private class Clerk
	{
		public Client currentClient;
		double timeTillAvailable;
		int index;
		Clerk()
		{
			currentClient = null;
			timeTillAvailable = 0;
			index = 0;
		}
	}
	private class WaitingClient
	{
		Client client;
		double currentWaitTime;
		Clerk targetClerk;
		WaitingClient()
		{
			
		}
	}
	int numClerks;
	Clerk[] clerks;
	ArrayList<WaitingClient> waitingClients;
	int numTotalClients;
	int numWaitingClients;
	
	//customer arrives, place in shortest line available
	public GroceryStore(int numberOfClerks)
	{
		numClerks = numberOfClerks;
		clerks = new Clerk[numClerks];
		for(int i = 0; i < numClerks; i++)
		{
			clerks[i] = new Clerk();
			clerks[i].index = i;
		}
		numTotalClients = 0;
		numWaitingClients = 0;
		waitingClients = new ArrayList<WaitingClient>();
	}
	@Override
	public double getAverageClientWaitTime()
	{
		double avg = 0;
		if(numWaitingClients>0)
		{
			double sumWait = 0;
			Iterator<WaitingClient> clients = waitingClients.iterator();
			while(clients.hasNext())
			{
				sumWait += clients.next().currentWaitTime;
			}
			avg = sumWait/(double)numWaitingClients;
		}
		return avg;
	}

	@Override
	public double getClientWaitTime(Client client)
	{
		double waitTime = 0;
		boolean foundClient = false;
		Iterator<WaitingClient> clients = waitingClients.iterator();
		while(clients.hasNext() && !foundClient)
		{
			WaitingClient c = clients.next();
			if(c.client == client)
			{
				foundClient = true;
				waitTime = c.currentWaitTime;
			}
		}
		if(!foundClient)
		{
			for(int i = 0; i < numClerks && !foundClient; i++)
			{
				if(clerks[i].currentClient == client)
				{
					foundClient = true;
				}
			}
			if(!foundClient)
			{
				throw new NoSuchElementException("Client does not exist");
			}
		}
		return waitTime;
	}

	@Override
	public boolean addClient(Client client)
	{
		WaitingClient c = new WaitingClient();
		c.client = client;
		c.currentWaitTime = Double.MAX_VALUE;
		waitingClients.add(c);
		numWaitingClients++;
		boolean stillInLine = chooseClerk(c);
		if(!stillInLine)
		{
			numWaitingClients--;
			waitingClients.remove(c);
		}
		System.out.println(stillInLine);
		return true;
	}

	@Override
	public void advanceMinute()
	{
		updateClerks();
		updateLine();
	}
	
	private void updateClerks()
	{
		for(int i = 0; i < numClerks; i++)
		{
			if(clerks[i].currentClient!=null)
			{
				clerks[i].currentClient.servedMinute();
				clerks[i].timeTillAvailable = clerks[i].currentClient.getExpectedServiceTime();
				if(clerks[i].currentClient.getExpectedServiceTime() ==0)
				{
					clerks[i].currentClient = null;
					numTotalClients--;
				}
			}
		}
	}
	private void updateLine()
	{
		Iterator<WaitingClient> clients = waitingClients.iterator();
		while(clients.hasNext())
		{
			WaitingClient c = clients.next();
			c.currentWaitTime--;
			if(c.targetClerk.currentClient == null)
			{
				c.targetClerk.currentClient = c.client;
				numWaitingClients--;
				clients.remove();
			}
		}
	}
	private boolean chooseClerk(WaitingClient c)
	{
		c.currentWaitTime = Double.MAX_VALUE;
		boolean stillInLine = true;
		int targetTeller = 0;
		for(int i = 0; i < numClerks && stillInLine; i++)
		{
			Clerk t = clerks[i];
			if(t.timeTillAvailable == 0)
			{
				t.currentClient = c.client;
				t.timeTillAvailable = c.client.getExpectedServiceTime();
				c.currentWaitTime = 0;
				stillInLine = false;
			}
			else
			{
				if(t.timeTillAvailable<c.currentWaitTime)
				{
					c.targetClerk = t;
					c.currentWaitTime = t.timeTillAvailable;
				}
			}
		}
		if(stillInLine)
		{
			c.targetClerk.timeTillAvailable+=c.client.getExpectedServiceTime();
		}
		return stillInLine;
	}
	public void printAllClients()
	{
		for(int i = 0; i < numClerks; i++)
		{
			if(clerks[i].currentClient != null)
			{
				System.out.print("Teller " + i);
				System.out.println(" Client: expected Service Time " + clerks[i].currentClient.getExpectedServiceTime());
			}
			
		}
		Iterator<WaitingClient> clients = waitingClients.iterator();
		int i = 0;
		while(clients.hasNext())
		{
			WaitingClient c = clients.next();
			System.out.println("Waiting Client " + i + " service time " + c.client.getExpectedServiceTime() + ", wait time " + c.currentWaitTime + ", Target Clerk " + c.targetClerk.index);
			i++;
		}
	}
	
}
