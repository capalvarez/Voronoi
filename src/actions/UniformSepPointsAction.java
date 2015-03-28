package actions;

import generalTools.PointGenerator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilities.MyPoint;

import display.IWindow;

public class UniformSepPointsAction extends AbstractAction {
	private IWindow window;	
	
	public UniformSepPointsAction(IWindow w){
		window = w;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField deltaX = new JTextField(5);
		JTextField deltaY = new JTextField(5);
	
		JPanel inputPanel = new JPanel();
		GridLayout layout = new GridLayout(0,1);
		inputPanel.setLayout(layout);
		inputPanel.add(new JLabel("Separacion en X:"));
		
		JPanel xPanel = new JPanel();
		xPanel.add(deltaX);
		inputPanel.add(xPanel);
		
		inputPanel.add(new JLabel("Separacion en Y:"));
		JPanel yPanel = new JPanel();
		yPanel.add(deltaY);
		inputPanel.add(yPanel);
		
		int result = JOptionPane.showConfirmDialog(null, inputPanel, 
				"Puntos uniforme por separacion", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			int dX = Integer.parseInt(deltaX.getText());
			int dY = Integer.parseInt(deltaY.getText());		
			
			int numX = (int)Math.floor(window.getWidth()/dX);
			int numY = (int)Math.floor(window.getHeight()/dY);
			
			/*Generar los puntos*/
			MyPoint[] pointArray = (new PointGenerator(numX,dX,numY,dY)).getPoints();
						
			/*Dibujar los puntos y dejarlos guardados en la ventana*/
			window.drawPointsInPanel(pointArray,pointArray);
			window.repaint();
			
		}
		
	}

}
