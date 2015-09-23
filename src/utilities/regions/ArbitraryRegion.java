package utilities.regions;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import utilities.MyPoint;
import utilities.MyScale;

public class ArbitraryRegion implements MyRegion {
	MyPoint[] points;
	MyScale scale;
	
	public ArbitraryRegion(MyPoint[] p, MyScale s){
		points = p;
		scale = s;
	}
		
	@Override
	public boolean isInside(MyPoint point) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawRegion(Graphics2D g2d) {
		int n = points.length;
				
		for(int i=0;i<n;i++){
			MyPoint p1 = points[i%n];
			MyPoint p2 = points[(i+1)%n];
		
			MyPoint pixelP1 = scale.getPixelValue(p1);
			MyPoint pixelP2 = scale.getPixelValue(p2);
		
			g2d.draw(new Line2D.Double(pixelP1.getX(), pixelP1.getY(), pixelP2.getX(), pixelP2.getY()));
		}
	}

	@Override
	public MyPoint[] generateNonUniform(double initX, double multX, double initY,
			double multY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPixelValues(MyPoint[] pixelValues) {
		// TODO Auto-generated method stub
		
	}

}
