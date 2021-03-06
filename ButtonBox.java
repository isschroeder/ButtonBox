import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;   // needed to inherit java.io.InputStream
import java.util.ArrayList;
import javax.sound.sampled.*;


/**
 * 
 * This program plays different sounds!!
 * @author Isabelle Schroeder
 *
 */
public class ButtonBox extends JFrame implements ActionListener, LineListener{ 
	// need both action and line listener to listen to button pushes and lines
	
	private static final long serialVersionUID = 1; // keeps from throwing an exception (needed for happiness) 
	private JPanel forButtons; // panel for the buttons
	private File sounds;
	ArrayList<String> soundList = new ArrayList<String>();
	boolean playCompleted; // used in the super try-catch (That's the really cool one that covers all the catching)
	String buttonsAndStuff;
	
	
	public ButtonBox(){
		
		super( "ButtonBox" ); // there's also Bat(ButtonBox)
		
		JButton button; // for all the buttons
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program close when exited
		setLayout( new BorderLayout() );
		
		forButtons = new JPanel(); // adding panel to frame
		
		forButtons.setLayout( new GridLayout( 3, 3) ); // laying out the buttons in a 3 row, 3 column set up
		
		button = new JButton( "Ominous" ); // name on button
		button.setActionCommand( "one" ); // identification in switch
		button.addActionListener( this ); // adding action listener for this JButton
		forButtons.add( button ); // ADDING THE BUTTON!!!
		
		// rest of the buttons follow the same reasoning
		button = new JButton( "Seal" );
		button.setActionCommand( "two" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "Sheep" );
		button.setActionCommand( "three" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "Thunder" );
		button.setActionCommand( "four" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "Monkey" );
		button.setActionCommand( "five" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "Duck" );
		button.setActionCommand( "six" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "Play Recording" );
		button.setActionCommand( "play" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "PRESS BEFORE RECORD" );
		button.setActionCommand( "fixin' stuff" );
		button.addActionListener( this );
		forButtons.add( button );
		
		button = new JButton( "Record" );
		button.setActionCommand( "record" );
		button.addActionListener( this );
		forButtons.add( button );
		
		add( forButtons, BorderLayout.SOUTH ); // add the panel to the frame, set the layout
			
		setSize( getPreferredSize() ); // set the size of the frame to preferred
		pack(); // pack the whole frame all happy
		setVisible( true ); // This is pretty important, not gonna lie.
	}
	
    
   /**
    * This does the actual playing part.
    * @param takes a file
    */
   void play( String noise ) {
		switch( noise ){
		case "one":
			sounds = new File("ominous.wav");
			break; // always, always, ALWAYS remember to put your breaks in when you don't want fallthroughs -_-
		case "two":
			sounds = new File("seal.wav");
			break;
		case "three":
			sounds = new File("sheep521.wav");
			break;
		case "four":
			sounds = new File("thunder-02.wav");
			break;
		case "five":
			sounds = new File("monkey2.wav");
			break;
		case "six":
			sounds = new File("duck.wav");
			break;
		}
	   
	   // These operations require a lot of try catch things to surround them, so here is just one big try catch so everyone is happy!!
       try {
    	   AudioInputStream audioStream = AudioSystem.getAudioInputStream( sounds ); // accesses file

           AudioFormat format = audioStream.getFormat(); // setting up audio format

           DataLine.Info info = new DataLine.Info(Clip.class, format); // "adds media-related functionality" (start and stop and such things)

           Clip audioClip = (Clip) AudioSystem.getLine(info); // making the clip

           audioClip.addLineListener(this); // listen to the clip

           audioClip.open(audioStream); // open clip file
            
           audioClip.start(); // start playing clip
            
           while (!playCompleted) { // wait for clip to complete
               try {
                   Thread.sleep(1000); // wait 1 second (1000 milliseconds)
               } catch (InterruptedException e) { // Catch the exception. Do nothing..
               }
           }
            
       // for when sadness happens
       } catch (UnsupportedAudioFileException e) {
           System.out.println("The specified audio file is not supported.");
       } catch (LineUnavailableException e) {
           System.out.println("Audio line for playing back is unavailable.");
       } catch (IOException e) {
           System.out.println("Error playing the audio file.");
       }
        
   }
    
   // for adding sounds to list?
   public void recording( String command ){
	   switch( command ){
	   case "ominous":
		   soundList.add( command );
		   break;
	   case "seal":
		   soundList.add( command );
		   break;
	   case "sheep":
		   soundList.add( command );
		   break;
	   case "thunder":
		   soundList.add( command );
		   break;
	   case "monkey":
		   soundList.add( command );
		   break;
	   case "duck":
		   soundList.add( command );
		   break;
	   }
	   
   }
   
   // this should play back the recorded sounds
   public void playbackRecording( ArrayList<String> soundList ){
	   for( int ctr = 0; ctr < soundList.size(); ctr++ ){
		   buttonsAndStuff = soundList.get( ctr );
		   play( buttonsAndStuff ); 
		   // decided to use a Thread.sleep instead of incorporating longs into program
		   try{
			   Thread.sleep(2000);
		   } catch(InterruptedException e){
		   }
	   }
   }
   
   
   /**
    * Listens to the START and STOP events of the audio line.
    */
   @Override
   public void update(LineEvent event) {
       LineEvent.Type type = event.getType();
        
       if (type == LineEvent.Type.START) {
            
       } else if (type == LineEvent.Type.STOP) {
           playCompleted = true;
       }

   }
	
   /**
    * Tells the program which sound to play!
    */
	public void actionPerformed( ActionEvent e ){
		JButton button = (JButton)e.getSource();
		soundList.add(button.getActionCommand());
		
		switch( button.getActionCommand() ){
			case "play":
				playbackRecording( soundList );
				break;
			case "fixin' stuff":
				soundList.clear(); // clears old sounds before user starts wanting to actually record sounds
				break;
			case "record":
				recording( e.getActionCommand() );
				break;
			default:
				soundList.clear(); // for that person who decides to skip the "PRESS BEFORE RECORD" button
				play( e.getActionCommand() );
				break;
		}
		
	}
	
	public static void main(String[] args) {
		new ButtonBox(); // Run the program!!
	}

}
