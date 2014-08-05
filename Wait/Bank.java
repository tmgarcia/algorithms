import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.neumont.util.Client;
import edu.neumont.util.QueueableService;


public class Bank implements QueueableService
{
	private class Teller
	{
		public Client currentClient;
		double timeTillAvailable;
		Teller()
		{
			currentClient = null;
			timeTillAvailable = 0;
		}
	}
	private class WaitingClient
	{
		Client client;
		double currentWaitTime;
		WaitingClient()
		{
			
		}
	}
	
	int numTellers;
	Teller[] tellers;
	ArrayList<WaitingClient> waitingClients;
	int numTotalClients;
	int numWaitingClients;
	//single line feeding into next available teller
	public Bank(int numberOfTellers)
	{
		numTellers = numberOfTellers;
		tellers = new Teller[numTellers];
		for(int i = 0; i < numTellers; i++)
		{
			tellers[i] = new Teller();
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
	public double getClientWaitTime(Client client)//how long wait till served
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
			for(int i = 0; i < numTellers && !foundClient; i++)
			{
				if(tellers[i].currentClient == client)
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
		boolean stillInLine = chooseTeller(c);
		if(!stillInLine)
		{
			numWaitingClients--;
			waitingClients.remove(c);
		}
		
		return true;
	}

	//changing customers expected wait times and allowing servers to become available
	//if this method called, one minute has passed in time
	@Override
	public void advanceMinute()
	{
		updateTellers();
		updateLine();
	}
	
	private void updateTellers()
	{
		for(int i = 0; i < numTellers; i++)
		{
			if(tellers[i].currentClient!=null)
			{
				tellers[i].currentClient.servedMinute();
				tellers[i].timeTillAvailable = tellers[i].currentClient.getExpectedServiceTime();
				if(tellers[i].currentClient.getExpectedServiceTime() ==0)
				{
					tellers[i].currentClient = null;
					numTotalClients--;
				}
			}
		}
	}
	private boolean chooseTeller(WaitingClient c)
	{
		c.currentWaitTime = Double.MAX_VALUE;
		boolean stillInLine = true;
		int targetTeller = 0;
		for(int i = 0; i < numTellers && stillInLine; i++)
		{
			Teller t = tellers[i];
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
					targetTeller = i;
					c.currentWaitTime = t.timeTillAvailable;
				}
			}
		}
		if(stillInLine)
		{
			tellers[targetTeller].timeTillAvailable+=c.client.getExpectedServiceTime();
		}
		return stillInLine;
	}
	private void updateLine()
	{
		Iterator<WaitingClient> clients = waitingClients.iterator();
		while(clients.hasNext())
		{
			WaitingClient c = clients.next();
			c.currentWaitTime = Double.MAX_VALUE;
			boolean stillInLine = chooseTeller(c);
			if(!stillInLine)
			{
				numWaitingClients--;
				clients.remove();
			}
		}
	}
	public void printAllClients()
	{
		for(int i = 0; i < numTellers; i++)
		{
			if(tellers[i].currentClient != null)
			{
				System.out.print("Teller " + i);
				System.out.println(" Client: expected Service Time " + tellers[i].currentClient.getExpectedServiceTime());
			}
			
		}
		Iterator<WaitingClient> clients = waitingClients.iterator();
		int i = 0;
		while(clients.hasNext())
		{
			WaitingClient c = clients.next();
			System.out.println("Waiting Client " + i + " service time " + c.client.getExpectedServiceTime() + ", wait time " + c.currentWaitTime);
			i++;
		}
	}
}
