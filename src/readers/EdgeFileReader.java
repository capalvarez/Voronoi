package readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import utilities.MyPoint;
import utilities.PointPair;
import utilities.edges.ExternalEdge;
import utilities.edges.InternalEdge;
import utilities.edges.AbstractEdge;
import utilities.edges.MyEdge;

public class EdgeFileReader {
	MyEdge[] edges;
	HashMap<PointPair,MyEdge> dictionary = new HashMap<PointPair,MyEdge>();
	HashMap<MyPoint,ArrayList<MyEdge>> externalDictionary = new HashMap<MyPoint,ArrayList<MyEdge>>(); 

	public EdgeFileReader(String name, MyPoint[] points, MyPoint[] externalPoints){
		File file = new File(name + ".1.edge");
			
		for(int i=0;i<externalPoints.length;i++){
			externalDictionary.put(externalPoints[i], new ArrayList<MyEdge>());
		}
		
		readFile(file,points);
	}
	
	private void readFile(File input, MyPoint[] points){
		BufferedReader br = null;	
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(input));		
			currentLine = br.readLine();
			
			/*Leemos en la primera linea la dimension de los puntos (2 o 3)*/
			String[] firstLine = currentLine.trim().split("\\s+");

			/*Leemos la segunda linea, que contiene el numero de puntos*/
			int totalEdges = Integer.parseInt(firstLine[0]);
			edges = new MyEdge[totalEdges + 1];
						
			int i = 1;
			while(i<=totalEdges){
				currentLine=br.readLine();
				
				String[] coord = currentLine.trim().split("\\s+");
			
				if(coord.length>0){
					int i1 = Integer.parseInt(coord[1]);
					int i2 = Integer.parseInt(coord[2]);
					
					AbstractEdge edge;
					if(coord[3].equals("0")){
						edge = new InternalEdge(i1,i2);
						edge.setPoints(points[i1],points[i2]);
					}else{	
						edge = new ExternalEdge(i1,i2);
						edge.setPoints(points[i1],points[i2]);
						
						externalDictionary.get(points[i1]).add(edge);
						externalDictionary.get(points[i2]).add(edge);
					}
															
					dictionary.put(new PointPair(points[i1],points[i2]), edge);
					edges[i] = edge;
				}
				i++;
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		}				
    }

	public MyEdge[] getEdges(){
		return edges;
	}
	
	public HashMap<PointPair,MyEdge> getDictionary(){
		return dictionary;
	}
	
	public HashMap<MyPoint,ArrayList<MyEdge>> getExternalDictionary(){
		return externalDictionary;
	}
}
