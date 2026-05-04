package CircularGraphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;



public class CircGraph extends JPanel{
	int BORDER_GAP=22;
	int Y_HATCH_CNT=30;
	int GRAPH_POINT_WIDTH=5;
	int offset = 25;
	private ArrayList<Point> graphPoints = new ArrayList<Point>();
	private int size;
	public CircGraph(ArrayList<Point> graphPoints,int size) {
		this.graphPoints=graphPoints;
		this.size=size;
	}
	protected void paintComponent(Graphics g) {
		  ArrayList<Point> positions=new ArrayList<Point>();
	      super.paintComponents(g);
	      Graphics2D g2 = (Graphics2D)g;
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	     

	      // create x and y axes 
	      g2.drawLine(offset, getHeight() - offset, offset, offset);
	      g2.drawLine(offset, getHeight() - offset, getWidth() - offset, getHeight() - offset);
	      
	      int theta=360/size;
	      
	      //place the points
	      for (int i=1;i<size+1;i++) {
	    	  int x=(int) (400+200*Math.cos(theta*i));
	    	  int y=getHeight() - (int) (400+200*Math.sin(theta*i));
	    	  g2.fillOval(x,y,8,8);
	    	  g2.drawString("v"+i, x+10, y);
	    	  positions.add(new Point(x,y));
	      }
	      
	      //draw the edges
	      for (int i=1;i<graphPoints.size()+1;i++) {
	    	  int xSource=(int)positions.get((int)graphPoints.get(i-1).getX()).getX();
	    	  int ySource=(int)positions.get((int)graphPoints.get(i-1).getX()).getY();
	    	  int xDest=(int)positions.get((int)graphPoints.get(i-1).getY()).getX();
	    	  int yDest=(int)positions.get((int)graphPoints.get(i-1).getY()).getY();
	    	  g2.drawLine(xSource,ySource,xDest,yDest);
	      }
	      
	      
	      // create hatch marks for y axis. 
	      /*for (int i = 0; i < Y_HATCH_CNT; i++) {
	         int x0 = BORDER_GAP;
	         int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
	         int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
	         int y1 = y0;
	         g2.drawLine(x0, y0, x1, y1);
	      }

	      // and for x axis
	      for (int i = 0; i < graphPoints.size() - 1; i++) {
	         int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (graphPoints.size() - 1) + BORDER_GAP;
	         int x1 = x0;
	         int y0 = getHeight() - BORDER_GAP;
	         int y1 = y0 - GRAPH_POINT_WIDTH;
	         g2.drawLine(x0, y0, x1, y1);
	      }

	      /*Stroke oldStroke = g2.getStroke();
	      g2.setColor(GRAPH_COLOR);
	      g2.setStroke(GRAPH_STROKE);
	      for (int i = 0; i < graphPoints.size() - 1; i++) {
	         int x1 = graphPoints.get(i).x;
	         int y1 = graphPoints.get(i).y;
	         int x2 = graphPoints.get(i + 1).x;
	         int y2 = graphPoints.get(i + 1).y;
	         g2.drawLine(x1, y1, x2, y2);         
	      }

	      g2.setStroke(oldStroke);      
	      g2.setColor(GRAPH_POINT_COLOR);
	      for (int i = 0; i < graphPoints.size(); i++) {
	         int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
	         int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
	         int ovalW = GRAPH_POINT_WIDTH;
	         int ovalH = GRAPH_POINT_WIDTH;
	         g2.fillOval(x, y, ovalW, ovalH);
	      }*/
	   }
	
}
