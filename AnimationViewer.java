/*
 * ==========================================================================================
 * AnimationViewer.java : Moves shapes around on the screen according to different paths.
 * It is the main drawing area where shapes are added and manipulated.
 * YOUR UPI: bkwo461 - Shinbeom Kwon
 * ==========================================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Field;

class AnimationViewer extends JComponent implements Runnable {
	private Thread animationThread = null;		// the thread for animation
	private static int DELAY = 120;				 // the current animation speed
	private ArrayList<Shape> shapes = new ArrayList<Shape>(); //create the ArrayList to store shapes
	private ShapeType currentShapeType=Shape.DEFAULT_SHAPETYPE; // the current shape type,
	private PathType currentPathType=Shape.DEFAULT_PATHTYPE;	// the current path type
	private Color currentColor=Shape.DEFAULT_COLOR; // the current fill colour of a shape
	private int currentPanelWidth=Shape.DEFAULT_PANEL_WIDTH, currentPanelHeight = Shape.DEFAULT_PANEL_HEIGHT, currentWidth=Shape.DEFAULT_WIDTH, currentHeight=Shape.DEFAULT_HEIGHT;
	private String currentText = Shape.DEFAULT_TEXT;
	
	public AnimationViewer() {
		start();
		addMouseListener(new MyMouseAdapter());
	}
	protected void createNewShape(int x, int y) {
		//complete this
		Shape newOne;
		if(currentShapeType == ShapeType.RECTANGLE){
			newOne = new RectangleShape(x, y, currentWidth, currentHeight, currentPanelWidth, currentPanelHeight, currentColor, currentPathType, currentText);
			
		}else{
			newOne = new OvalShape(x, y, currentWidth, currentHeight, currentPanelWidth, currentPanelHeight, currentColor, currentPathType, currentText);
			
		}
		shapes.add(newOne);
	}


	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		//complete this
		
		for(Shape curShape : shapes){
			curShape.move();
			curShape.draw(g);
			curShape.drawHandles(g);
			curShape.drawString(g);
		}
	}
	public void setCurrentColor(Color bc) {
		//complete this
		currentColor = bc;
		for(Shape getShape : shapes){
			if(getShape.isSelected() == true){
				getShape.setColor(bc);
			}
			
		}

	}
	public void setCurrentText(String text){
		
		for(Shape sh : shapes){
			if(sh.isSelected() == true){
				sh.setText(text);
			}
		}currentText = text;
	}
	public String getCurrentText(){
		return currentText;
	}
	public void createAndAddShape(String sentence){
		String[] arr = sentence.split(",");
		Shape newOne;
		if(ShapeType.valueOf(arr[0]) == ShapeType.RECTANGLE){
			newOne = new RectangleShape(Integer.valueOf(arr[3]), Integer.valueOf(arr[4]), currentWidth, currentHeight, currentPanelWidth, currentPanelHeight,getColorFromString(arr[2]),PathType.valueOf(arr[1]),arr[5]);
			setCurrentShapeType(ShapeType.RECTANGLE);
			setCurrentPathType(PathType.valueOf(arr[1]));
			currentColor = getColorFromString(arr[2]);
			currentText = arr[5];
		}else{
			newOne = new OvalShape(Integer.valueOf(arr[3]), Integer.valueOf(arr[4]), currentWidth, currentHeight, currentPanelWidth, currentPanelHeight,getColorFromString(arr[2]),PathType.valueOf(arr[1]),arr[5]);
			setCurrentShapeType(ShapeType.OVAL);
			setCurrentPathType(PathType.valueOf(arr[1]));
			currentColor = getColorFromString(arr[2]);
			currentText = arr[5];
		}
		shapes.add(newOne);
	}
	public boolean loadShapes(String filename){
		try {
			Scanner input = new Scanner(new File(filename));
			while(input.hasNextLine()){
				String line = input.nextLine();
				createAndAddShape(line);
			}
			
			return true;
		} catch (Exception e) {
			System.out.printf("ERROR: The file %s does not exist.\n",filename);
			return false;
		}
	}

	// you don't need to make any changes after this line ______________
	public void setCurrentShapeType(ShapeType value) { currentShapeType = value; }
	public void setCurrentPathType(PathType value) { currentPathType = value; }
	public ShapeType getCurrentShapeType() { return currentShapeType; }
	public PathType getCurrentPathType() { return currentPathType; }
	public int getCurrentWidth() { return currentWidth; }
	public int getCurrentHeight() { return currentHeight; }
	public Color getCurrentColor() { return currentColor; }
	public Color getColorFromString(String value) {
		try {
			Field field = Color.class.getField(value);
			return (Color)field.get(null);
		} catch (Exception e) {
			return Color.black;
		}
	}
	class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked( MouseEvent e ) {
			boolean found = false;
			for (Shape currentShape: shapes)
				if ( currentShape.contains( e.getPoint()) ) { // if the mousepoint is within a shape, then set the shape to be selected/deselected
					currentShape.setSelected( ! currentShape.isSelected() );
					found = true;
				}
			if (!found) createNewShape(e.getX(), e.getY());
		}
	}
	public void update(Graphics g){ paint(g); }
	public void resetMarginSize() {
		currentPanelWidth = getWidth();
		currentPanelHeight = getHeight() ;
		for (Shape currentShape: shapes)
			currentShape.setPanelSize(currentPanelWidth,currentPanelHeight );
	}
	public void start() {
		animationThread = new Thread(this);
		animationThread.start();
	}
	public void stop() {
		if (animationThread != null) {
			animationThread = null;
		}
	}
	public void run() {
		Thread myThread = Thread.currentThread();
		while(animationThread==myThread) {
			repaint();
			pause(DELAY);
		}
	}
	private void pause(int milliseconds) {
		try {
			Thread.sleep((long)milliseconds);
		} catch(InterruptedException ie) {}
	}
}
