import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class DisplayWindow
{
	JFrame frame;
	public DisplayWindow(DotsAndBoxesBoard db, StringsAndCoinsBoard sc, Console c, int width, int height)
	{
		frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		frame.add(db);
		frame.add(sc);
		frame.add(c);
		frame.setVisible(true);
	}
}
