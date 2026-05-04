package CircularGraphs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JFrame;

public class CircularMain extends JFrame {
	private static ArrayList<Point> graphCoords= new ArrayList<Point>();
	static ArrayList<ArrayList<Point>> S= new ArrayList<ArrayList<Point>>();
	private static ArrayList<Point> graph=new ArrayList<Point>();
	//private static ArrayList<Point> adjustedCoords=new ArrayList<Point>();
	private static ArrayList<Point> headSorted=new ArrayList<Point>();
	private static ArrayList<Point> tailSorted=new ArrayList<Point>();
	private static ArrayList<ArrayList<Integer>> neighbours=new ArrayList<ArrayList<Integer>>();
	private static ArrayList<Integer> W =new ArrayList<Integer>();
	private static ArrayList<Integer> tailPartners =new ArrayList<Integer>();
	private static ArrayList<Integer> headPartners =new ArrayList<Integer>();
	private static ArrayList<Integer> nextV=new ArrayList<Integer>();
	private static ArrayList<Point> createGraphCoords(int size) {
		ArrayList<Point> coords= new ArrayList<Point>();
		Random randomNum = new Random();
		int min=0;
		int max=359;
		for (int i=0;i<size;i++) {
			coords.add(new Point(min + randomNum.nextInt(max),min + randomNum.nextInt(max)));
		}
		return coords;
	}
	
	//Two sorting functions
	private static void sortGraph(ArrayList<Point> input) {
		Collections.sort(input,new Comparator<Point>() {
			public int compare(Point x1,Point x2){
				return Double.compare(Math.abs(x1.getX()),Math.abs(x2.getX()));
			}		
		});
		//Collections.reverse(input);
		//headSorted = input;
		/*Collections.sort(input,new Comparator<Point>() {
			public int compare(Point x1,Point x2){
				return Double.compare(x1.getY(),x2.getY());
			}		
		});
		tailSorted=input;*/
	}
	private static void sortByTail(ArrayList<Point> input) {
		Collections.sort(input,new Comparator<Point>() {
			public int compare(Point x1,Point x2){
				return Double.compare(Math.abs(x1.getY()),Math.abs(x2.getY()));
			}		
		});
		//Collections.reverse(input);
		//headSorted = input;
		/*Collections.sort(input,new Comparator<Point>() {
			public int compare(Point x1,Point x2){
				return Double.compare(x1.getY(),x2.getY());
			}		
		});
		tailSorted=input;*/
	}
	
	//find neighbours for each vertex (Only used for W)
	private static void findNeighbours(ArrayList<Point> input) {
		int curNode=0;
		ArrayList<Integer> tempList=new ArrayList<Integer>();
		for (int i=0;i<input.size();i++) {
			if (curNode!=input.get(i).getX()) {
				curNode=(int) input.get(i).getX();
				neighbours.add(tempList);
				tempList=new ArrayList<Integer>();
			}
			if (curNode==input.get(i).getX()) {
				tempList.add((int)input.get(i).getY());
			}
		}
		
	}
	
	//creates the intersectional graph
	private static void createGraph(ArrayList<Point> input) {
		
	
		double headI,headJ,tailI,tailJ;
		for (int i=0;i<input.size();i++) {
			for (int j=0;j<input.size();j++) {
				headI=input.get(i).getX();
				headJ=input.get(j).getX();
				tailI=input.get(i).getY();
				tailJ=input.get(j).getY();
				
				if(headI>tailI) {
					if ((headJ>=headI&&headJ<=359)||(headJ>=0&&headJ<=tailI)) {
						graph.add(new Point(i,j));
					}
					else if ((tailJ>=headI&&tailJ<=359)||(tailJ>=0&&tailJ<=tailI)) {
						graph.add(new Point(i,j));
					}
				}
				else {
					if ((headI>=headJ&&headI<=tailJ)||(tailI>=headJ&&tailI<=tailJ)||(headJ>=headI&&headJ<=tailI)||(tailJ>=headI&&tailJ<=tailI)) {
						graph.add(new Point(i,j));
					} 
				}
			}
		}
	}
	
	//find all tail partners for all vertexes
	private static void findTP(ArrayList<Point> input) {
		//ArrayList<Point> tailPartners =new ArrayList<Point>();
		for (int i=0;i<input.size();i++) {
			tailPartners.add(i);
		}
		Point farthestTail=input.get(0);
		int farthestTailV=0;
		for (int i=0;i<input.size();i++) {
			if (input.get(i).getY()<farthestTail.getY()) {
				farthestTail=input.get(i);
				farthestTailV=i;
			}
		}
		
		for(int i=0;i<360;i++) {
			for (int j=0;j<input.size();j++) {
				if (i==input.get(j).getX()&&input.get(j).getY()>farthestTail.getY()) {
					farthestTail=input.get(j);
					farthestTailV=j;
				}
				if (i==input.get(j).getY()) {
					tailPartners.set(j,farthestTailV);
				}
			}
		}
		/*if(tailPartners.size()<input.size()) {
			for(int i=tailPartners.size();i<input.size();i++) {
				tailPartners.add(i);
			}
		}*/
		for (int i=0;i<input.size();i++) {
			System.out.println("tailPartner "+i+" :"+tailPartners.get(i));
		}
	}
	
	//find all the head partners for all vertexes
	public static void findHP(ArrayList<Point> input){
		Point farthestHead=input.get(input.size()-1);
		int farthestHeadV=input.size()-1;
		for (int i=0;i<input.size();i++) {
			headPartners.add(i);
		}
		for (int i=0;i<input.size();i++) {
			if (input.get(i).getY()>farthestHead.getY()) {
				farthestHead=input.get(i);
				farthestHeadV=i;
			}
		}
		for (int i=359;i>-1;i--) {
			for (int j=0;j<input.size();j++) {
				if (i==input.get(j).getY()&&input.get(j).getX()<farthestHead.getX()) {
					farthestHead=input.get(j);
					farthestHeadV=j;
				}
				if (i==input.get(j).getX()) {
					headPartners.set(j,farthestHeadV);
				}
			}
		}
		//Collections.reverse(headPartners);
		for(int i=0;i<headPartners.size();i++) {
			System.out.println("head partner"+i+" = "+headPartners.get(i));
		}
		
	}
	
	//searches for the next vertex
	private static void findNext(ArrayList<Point> input) {
		ArrayList <Integer> P=new ArrayList<Integer>();
		ArrayList <Integer> Q=new ArrayList<Integer>();
		ArrayList <Integer> fList=new ArrayList<Integer>();
		ArrayList <Integer> cList=new ArrayList<Integer>();
		for(int i=0;i<input.size();i++) {
			//P.add(0);
			//Q.add(new Point(0,0));
			fList.add(-1);
			cList.add(-1);
		}
		
		for(int f=0;f<360;f++) {
			for (int i=0;i<input.size();i++) {
				if (f==input.get(i).getY()) {
					System.out.println("Added to P!");
					P.add(i);
					
				}
				if (f==input.get(i).getX()) {
					for (int j=0;j<P.size();j++) {
						System.out.println("Added to fList!"+ P.get(j));
						fList.set((int) P.get(j),i);
					}
					P=new ArrayList<Integer>();
				}
				
			}
		//}
		//for (int f=360;f>0;f--) {
			for (int i=0;i<input.size();i++) {
				
				if (f==input.get(i).getX()) {
					Q.add(i);
				}
				if (f==input.get(i).getY()) {
					for(int j=0;j<Q.size();j++) {
						if(j<=i) {
							cList.set(Q.get(j), i);
							
						}
					}
					Q=new ArrayList<Integer>();
				}
			}
		}
		for (int i=0;i<fList.size();i++) {
			System.out.println(i+" f "+fList.get(i));
			System.out.println(i+" c "+cList.get(i));
			
			//System.out.println(i+" cof "+cList.get(fList.get(i)));
			if (fList.get(i)==-1) {
				nextV.add(-1);
			}else {
				nextV.add(cList.get(fList.get(i)));
			}
			
		}
		
	}
	
	//main part of the algorithm, Populates S
	private static void findSi() {
		ArrayList<Point> Stail= new ArrayList<Point>();
		ArrayList<Point> Shead= new ArrayList<Point>();
		ArrayList<Integer> visited=new ArrayList<Integer>();
		boolean contains=false;
		int current=0;
		for(int i=0;i<W.size();i++) {
			
			Stail.add(new Point(W.get(i),tailPartners.get(i)));
			Shead.add(new Point(W.get(i),headPartners.get(i)));
			//visited.add(W.get(i));
			current=nextV.get(W.get(i));
			while(!visited.contains(current)&&current!=-1) {
				for(int j=0;j<Stail.size();j++) {
					if(Stail.get(j).getX()==tailPartners.get(tailPartners.get(current))||Stail.get(j).getY()==tailPartners.get(tailPartners.get(current))) {
						contains=true;
					}
				}
				
				//if (Stail.contains(tailPartners.get(tailPartners.get(current)))) {
				//	Stail.add(new Point(current,tailPartners.get(current)));
				//}
				if (contains==true) {
					Stail.add(new Point(current,tailPartners.get(current)));
				}
				else {
					Stail.add(new Point(tailPartners.get(current),tailPartners.get(tailPartners.get(current))));
				}
				visited.add(current);
				current=nextV.get(current);
				contains=false;
			}
			visited=new ArrayList<Integer>();
			//visited.add(W.get(i));
			current=nextV.get(W.get(i));
			while(!visited.contains(current)&&current!=-1) {
				
				for(int j=0;j<Shead.size();j++) {
					if(Shead.get(j).getX()==headPartners.get(headPartners.get(current))||Shead.get(j).getY()==headPartners.get(headPartners.get(current))) {
						contains=true;
					}
				}
				
				//if (Shead.contains(headPartners.get(headPartners.get(current)))) {
				//	Shead.add(new Point(current,headPartners.get(current)));
				//}
				
				if (contains==true) {
					Shead.add(new Point(current,headPartners.get(current)));
				}
				
				else {
					Shead.add(new Point(headPartners.get(current),headPartners.get(headPartners.get(current))));
				}
				visited.add(current);
				current=nextV.get(current);
				contains=false;
			}
			visited=new ArrayList<Integer>();
			if (Stail.size()<Shead.size()) {
				S.add(Stail);
			}else {S.add(Shead);}
			Stail=new ArrayList<Point>();
			Shead=new ArrayList<Point>();
			
			System.out.println("cur :"+current);
		}
	}

	
	private static void drawGraph(ArrayList<Point> input,int size) {
		JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CircGraph(input,size));
        frame.setSize(800,800);
        frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		//doStuff();
		int size=7;
		ArrayList<Point> Smin=new ArrayList<Point>();
		graphCoords= new ArrayList<Point>(Arrays.asList(new Point(50,100),new Point (70,80),new Point (75,150),new Point (125,200),new Point (170,210),new Point (180,190),new Point(205,290)));;
		
		//graphCoords=new ArrayList<Point>(Arrays.asList(new Point(10,200),new Point(20,40),new Point(50,70),new Point(150,300),new Point (250,270),new Point(280,330),new Point(310,320),new Point(215,230)));
		
		//graphCoords=new ArrayList<Point>(Arrays.asList(new Point(10,100),new Point(40,55),new Point(90,200),new Point(120,140),new Point(130,150)));
		
		//graphCoords=new ArrayList<Point>(Arrays.asList(new Point (200,250),new Point(150,222),new Point(100,130),new Point(110,190)));
		
		Collections.sort(graphCoords,new Comparator<Point>() {
			public int compare(Point x1,Point x2){
				return Double.compare(Math.abs(x1.getX()),Math.abs(x2.getX()));
			}		
		});
		
		size=graphCoords.size();
		
		//graphCoords=CircularMain.createGraphCoords(size);
		/*for (int i=0;i<graphCoords.size();i++) {
			tailSorted.add(graphCoords.get(i));
			headSorted.add(graphCoords.get(i));
		}*/
		//tailSorted=graphCoords;
		//headSorted=graphCoords;
		//sortByTail(tailSorted);//not used in current implementation
		//sortGraph(headSorted);//not used either
		createGraph(graphCoords);//graph coords is ordered by X (unintentional)
		
		
		for (int i=0;i<graph.size();i++) {
			System.out.println(graph.get(i));
		}
		
		for (int i=0;i<tailSorted.size();i++) {
			System.out.println("tail "+tailSorted.get(i));
		}
		for (int i=0;i<headSorted.size();i++) {
			System.out.println("head "+headSorted.get(i));
		}
		
		findNeighbours(graph);
		
		
		//W=neighbours.get(0);
		Point maxArc=new Point(0,0);
		for (int i=0;i<neighbours.get(0).size();i++) {
			if ((graphCoords.get(neighbours.get(0).get(i)).getY()-graphCoords.get(neighbours.get(0).get(i)).getX()>maxArc.getY()-maxArc.getX())) {
				maxArc=graphCoords.get(neighbours.get(0).get(i));
				W=neighbours.get(neighbours.get(0).get(i));
			}
		}
		
		
		for (int i=0;i<neighbours.size();i++) {
			//System.out.println("headSorted ["+i+"]: "+headSorted.get(i));
			//System.out.println("W ["+i+"]: "+W.get(i));
			System.out.println("Neigbours ["+i+"]: "+neighbours.get(i));
			
		}
		findTP(graphCoords);
		findHP(graphCoords);
		findNext(graphCoords);
		findSi();
		//mainAlgorithm(graph);
		Smin=S.get(0);
		for (int i=0;i<S.size();i++) {
			System.out.println(S.get(i));
			if (S.get(i).size()<Smin.size()) {
				Smin=S.get(i); //the output S with the least edges
			}
		}
		//Smin=S.get(3);
		System.out.println("Hallo");
		drawGraph(graph,size);
		drawGraph(Smin,size);
		for (int i=0;i<graph.size();i++) {
			System.out.println(graph.get(i));
		}
		
		
	}
	
}
