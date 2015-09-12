package com.yellowbyte.jjss.buttons;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.yellowbyte.jjss.JellyGame;


public class TextScroller {

	private Vector2 pos; //Position of left of first line on screen.
	
	private String text; //Text to be displayed.
	private String[] displayText; //Text currently being displayed. (Substring of given text.)
	private int scrollPos; //Current character being added to substring.
	
	private int lineLimit = 20; //Maximum amount of characters allowed in one line.
	private int lineNum = 0; //Current line being drawn.
	
	private boolean finished; //Finished scrolling?
	
	//TIMER
	private long startTime, currTime; //Timer variables.
	private float scrollSpeed = 20; //Speed at which the text should be scrolled to screen.
	
	
	private BitmapFont font; //Text font.
	
	
	public TextScroller(String text, Vector2 pos) {
		this.text = text;
		this.pos = pos;
		
		scrollPos = 0;
		
		displayText = new String[3]; //Decides how many lines we'll need.
		displayText[lineNum] = "";
		
		
		finished = false;
		
		startTime = System.nanoTime();
		
		font = JellyGame.font;
	}
	
	public void update() {
		
		if(!finished) {

			currTime = System.nanoTime();
			long timeElapsed = ((currTime - startTime) / 1000000);

			if (timeElapsed > scrollSpeed) {
				startTime = System.nanoTime();
				

				if (displayText[lineNum].length() < text.length()) {
					displayText[lineNum] += text.charAt(scrollPos);
					
					scrollPos++;
					
					checkEndLine();
					
				} else {
					finished = true;
				}
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(JellyGame.camera.combined);
		sb.begin();
		
		for(int i = 0; i <= lineNum; i++) {
			System.out.println("updateing");
			font.draw(sb, displayText[i], pos.x, pos.y);
		}
		
		sb.end();
	}
	
	private void checkEndLine() {
		
		if(scrollPos > lineLimit) {
			lineNum++;
			displayText[lineNum] = "";
		}
		
	}
}
