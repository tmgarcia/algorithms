import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class DotsAndBoxes
{
	Graph dotsAndBoxesGraph;
	int numDBRows;
	int numDBColumns;
	int totalDots;
	DotsAndBoxesBoard dbBoard;
	
	Graph stringsAndCoinsGraph;
	int numSCRows;
	int numSCColumns;
	int totalCoins;
	StringsAndCoinsBoard scBoard;
	
	ArrayList<Integer> players;
	
	Console cons;
	DisplayWindow win;
	DfsGraphTraversal dfs;
	BfsGraphTraversal bfs;
	
	int MARKED = 1;
	int UNMARKED = 0;
	public DotsAndBoxes(int rows, int columns)
	{
		numDBRows = rows;
		numDBColumns = columns;
		totalDots = numDBRows * numDBColumns;
		dotsAndBoxesGraph = new Graph(totalDots);
		
		numSCColumns = columns+1;
		numSCRows = rows+1;
		totalCoins = numSCRows*numSCColumns;
		stringsAndCoinsGraph = new Graph(totalCoins);
		
		setupStrings();
		
		dfs = new DfsGraphTraversal();
		bfs = new BfsGraphTraversal();
		
		players = new ArrayList<Integer>(2);
		
		dbBoard = new DotsAndBoxesBoard(numDBColumns, numDBRows, 500, 500, 75, dotsAndBoxesGraph);
		scBoard = new StringsAndCoinsBoard(numSCColumns, numSCRows, 500, 500, 25, stringsAndCoinsGraph);
		cons = new Console(numDBColumns, numDBRows);
		win = new DisplayWindow(dbBoard, scBoard, cons, 1300,600);
		
		cons.drawButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawLinePressed();
			}
		});
		cons.playerScoreButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				scorePressed();
			}
		});
		cons.movesLeftButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				movesLeftPressed();
			}
		});
		cons.dbBFSButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dbBFS();
			}
		});
		cons.dbDFSButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dbDFS();
			}
		});
		cons.scBFSButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				scBFS();
			}
		});
		cons.scDFSButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				scDFS();
			}
		});
		cons.dcButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				doubleCrossPressed();
			}
		});
		cons.cycleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cyclesPressed();
			}
		});
		cons.chainButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chainsPressed();
			}
		});
	}
	
	public int drawLine(int player, int x1, int y1, int x2, int y2)
	{
		if(!((Math.abs(x1-x2)==1 && Math.abs(y1-y2)==0) ||(Math.abs(x1-x2)==0 && Math.abs(y1-y2)==1)))
		{
			throw new IllegalArgumentException("Points must be adjacent.");
		}
		int dot1 = (y1*numDBColumns) + x1;
		int dot2 = (y2*numDBColumns) + x2;
		int coin1=0;
		int coin2=0;
		if(Math.abs(x1-x2)>0)
		{
			int coinx = Math.max(x1, x2);
			int coiny1 = y1;
			int coiny2 = y1+1;
			coin1 = (coiny1*numSCColumns) + coinx;
			coin2 = (coiny2*numSCColumns) + coinx;
		}
		else if(Math.abs(y1-y2)>0)
		{
			int coiny = Math.max(y1, y2);
			int coinx1 = x1;
			int coinx2 = x1+1;
			coin1 = (coiny*numSCColumns) + coinx1;
			coin2 = (coiny*numSCColumns) + coinx2;
		}
		dotsAndBoxesGraph.addEdge(dot1, dot2, 1);
		dotsAndBoxesGraph.addEdge(dot2, dot1, 1);
		stringsAndCoinsGraph.removeEdge(coin1, coin2);
		stringsAndCoinsGraph.removeEdge(coin2, coin1);
		
		if(player >= players.size())
		{
			players.ensureCapacity(player);
			for(int i = players.size(); i < player+1; i++)
			{
				players.add(0);
			}
		}
		int score = 0;
		if(!isEdgeCoin(coin1)&&stringsAndCoinsGraph.degree(coin1)==0)
		{
			score++;
		}
		if(!isEdgeCoin(coin2)&&stringsAndCoinsGraph.degree(coin2)==0)
		{
			score++;
		}
		players.set(player, players.get(player)+score);
		return score;
	}
	public int score(int player)
	{
		int score = 0;
		if(player < players.size())
		{
			score = players.get(player);
		}
		return score;
	}
	public boolean anyMovesLeft()
	{
		// returns whether the Dots and Boxes board is full.
		return stringsAndCoinsGraph.ecount()>0;
	}
	public int countDoubleCrosses()
	{
		int dc = 0;
		List<List<Integer>> components = dfs.traverse(stringsAndCoinsGraph);
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).size()==2)
			{
				dc++;
			}
		}
		return dc;
	}
	public int countCycles()
	{
		List<List<Integer>> components = dfs.traverse(stringsAndCoinsGraph);
		int numCycles = 0;
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).size()>2 && isCycle(components.get(i)))
			{
				numCycles++;
			}
		}
		return numCycles;
	}
	private boolean isCycle(List<Integer> component)
	{
		boolean isCycle = true;
		for(int i = 0; i < component.size() && isCycle; i++)
		{
			if(stringsAndCoinsGraph.degree(component.get(i))!=2)
			{
				isCycle = false;
			}
		}
		if(isCycle)
		{
			isCycle = stringsAndCoinsGraph.isEdge(component.get(0), component.get(component.size()-1));
		}
		return isCycle;
	}
	public int countOpenChains()
	{
		unmarkAll();
		List<List<Integer>> components = dfs.traverse(stringsAndCoinsGraph);
		unmarkAll();
		int numChains = 0;
		
		for(int i = 0; i < components.size(); i++)
		{
			if(components.get(i).size()>2)
			{
				numChains+= numChains(components.get(i));
			}
		}
		return numChains;
	}
	private int numChains(List<Integer> component)
	{
		int numChains = 0;
		for(int i = 0; i < component.size(); i++)
		{
			if(stringsAndCoinsGraph.degree(component.get(i))==2)
			{
				int links = followChain(component.get(i));
				if(links > 2)
				{
					numChains++;
				}
			}
		}
		return numChains;
	}
	//int numLinks;
	private int followChain(int currentVert)
	{
		if(stringsAndCoinsGraph.degree(currentVert)!=2)
		{
			return 0;
		}
		if(stringsAndCoinsGraph.getMark(currentVert)==MARKED)
		{
			return 0;
		}
		int neighbor1 = stringsAndCoinsGraph.first(currentVert);
		int neighbor2 = stringsAndCoinsGraph.next(currentVert, neighbor1);
		if(stringsAndCoinsGraph.getMark(neighbor1)==MARKED && stringsAndCoinsGraph.getMark(neighbor2)==MARKED)
		{
			return Integer.MIN_VALUE/2;//cycle
		}
		
		stringsAndCoinsGraph.setMark(currentVert, MARKED);
		int n1 = (stringsAndCoinsGraph.getMark(neighbor1)==MARKED)? 0 : followChain(neighbor1);
		int n2 = (stringsAndCoinsGraph.getMark(neighbor2)==MARKED)? 0 : followChain(neighbor2);
		return 1 + n1 + n2;
		
		//return followChain(nextLink, numLinks);
	}
	private void unmarkAll()
	{
		for(int i = 0; i < stringsAndCoinsGraph.vcount(); i++)
		{
			stringsAndCoinsGraph.setMark(i, UNMARKED);
		}
	}
	private void setupStrings()
	{
		for(int i = 0; i < totalCoins; i++)
		{
			if(!isFirstRow(i) && !isLastRow(i))
			{
				if(!isFirstColumn(i))
				{
					int leftVert = i-1;
					stringsAndCoinsGraph.addEdge(i, leftVert, 1);
				}
				if(!isLastColumn(i))
				{
					int rightVert = i+1;
					stringsAndCoinsGraph.addEdge(i, rightVert, 1);
				}
			}
			if(!isFirstColumn(i) && !isLastColumn(i))
			{
				if(!isFirstRow(i))
				{
					int upperVert = i-numSCColumns;
					stringsAndCoinsGraph.addEdge(i, upperVert, 1);
				}
				if(!isLastRow(i))
				{
					int lowerVert = i+numSCColumns;
					stringsAndCoinsGraph.addEdge(i, lowerVert, 1);
				}
			}
		}
	}
	private boolean isEdgeCoin(int index)
	{
		return (isFirstRow(index)||isLastRow(index)||isFirstColumn(index)||isLastColumn(index));
	}
	private boolean isFirstRow(int index)
	{
		return (index < numSCColumns);
	}
	private boolean isLastRow(int index)
	{
		return (index >= (totalCoins-numSCColumns));
	}
	private boolean isFirstColumn(int index)
	{
		return (index%numSCColumns == 0);
	}
	private boolean isLastColumn(int index)
	{
		return ((index+1)%numSCColumns == 0);
	}
	private void scorePressed()
	{
		int player = cons.getPlayerScoreNum();
		int score = score(player);
		cons.pushMessage("Player " + player + " score: " + score);
	}
	private void movesLeftPressed()
	{
		String response = anyMovesLeft()? "Available moves remain." : "No remaining moves available.";
		cons.pushMessage(response);
	}
	private void dbBFS()
	{
		List<List<Integer>> b = bfs.traverse(dotsAndBoxesGraph);
		System.out.println("\n/***** Dots and Boxes BFS *****/");
		for(int i = 0; i < b.size(); i++)
		{
			System.out.println("Component " + i);
			for(int j = 0; j < b.get(i).size(); j++)
			{
				System.out.print(b.get(i).get(j) + ",");
			}
			System.out.println();
		}
	}
	private void dbDFS()
	{
		List<List<Integer>> b = dfs.traverse(dotsAndBoxesGraph);
		System.out.println("\n/***** Dots and Boxes DFS *****/");
		for(int i = 0; i < b.size(); i++)
		{
			System.out.println("Component " + i);
			for(int j = 0; j < b.get(i).size(); j++)
			{
				System.out.print(b.get(i).get(j) + ",");
			}
			System.out.println();
		}
	}
	private void scBFS()
	{
		List<List<Integer>> b = bfs.traverse(stringsAndCoinsGraph);
		System.out.println("\n/***** String and Coins BFS *****/");
		for(int i = 0; i < b.size(); i++)
		{
			System.out.println("Component " + i);
			for(int j = 0; j < b.get(i).size(); j++)
			{
				System.out.print(b.get(i).get(j) + ",");
			}
			System.out.println();
		}
	}
	private void scDFS()
	{
		List<List<Integer>> b = dfs.traverse(stringsAndCoinsGraph);
		System.out.println("\n/***** String and Coins DFS *****/");
		for(int i = 0; i < b.size(); i++)
		{
			System.out.println("Component " + i);
			for(int j = 0; j < b.get(i).size(); j++)
			{
				System.out.print(b.get(i).get(j) + ",");
			}
			System.out.println();
		}
	}
	private void drawLinePressed()
	{
		int x1 = cons.getX1();
		int y1 = cons.getY1();
		int x2 = cons.getX2();
		int y2 = cons.getY2();
		int player = cons.getPlayerNum();
		
		if((Math.abs(x1-x2)==1 && Math.abs(y1-y2)==0) ||(Math.abs(x1-x2)==0 && Math.abs(y1-y2)==1))
		{
			int score = drawLine(player, x1, y1, x2, y2);
			cons.pushMessage("("+x1+","+y1+") to ("+x2+","+y2+")");
			cons.pushMessage("Player " + player + " recieved " + score + " points.");
		}
		else
		{
			cons.pushMessage("Chosen dots must be adjacent either horizontally or vertically.");
		}
	}
	private void doubleCrossPressed()
	{
		int dc = countDoubleCrosses();
		cons.pushMessage("Double Crosses: " + dc);
	}
	private void cyclesPressed()
	{
		int cycles = countCycles();
		cons.pushMessage("Cycles: " + cycles);
	}
	private void chainsPressed()
	{
		int chains = countOpenChains();
		cons.pushMessage("Chains: " + chains);
	}
}
