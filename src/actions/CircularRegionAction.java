package actions;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import regionBounders.CircleBounder;

import utilities.MyPoint;
import utilities.MyScale;
import utilities.regions.MyRegion;
import utilities.regions.circular.FullCircularRegion;
import utilities.regions.circular.PartialCircularRegion;

import dataProcessors.PointInitProcess;
import display.IWindow;

public class CircularRegionAction extends AbstractAction {
	private IWindow window;	
	
	public CircularRegionAction(IWindow w){
		window = w;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String[] options = { "Completo", "Medio", "Cuarto"};
	
		JTextField xFieldO = new JTextField(5);
		JTextField yFieldO = new JTextField(5);
		JTextField InnerRadius = new JTextField(5);
		JTextField OuterRadius = new JTextField(5);
		final JLabel space = new JLabel();
		
		final JPanel anglePanel = new JPanel();
		JTextField initAngle = new JTextField(5);
		JTextField endAngle = new JTextField(5);
		anglePanel.add(new JLabel("Ángulo inicial:"));
		anglePanel.add(initAngle);
		anglePanel.add(new JLabel("Ángulo final:"));
		anglePanel.add(endAngle);
		
		final JPanel inputPanel = new JPanel();
		GridLayout layout = new GridLayout(0,1);
		inputPanel.setLayout(layout);
		
		final JComboBox<String> optionList = new JComboBox<String>(options);
		optionList.setSelectedIndex(0);		
		inputPanel.add(optionList);
				
		optionList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(optionList.getSelectedIndex()==3){					
					inputPanel.remove(space);
					inputPanel.add(anglePanel);
				}else{
					inputPanel.remove(anglePanel);
					inputPanel.add(space);
				}
				
				inputPanel.repaint();
	       		inputPanel.revalidate();
	       		
			}
		});
		
		JPanel outerRadiusPanel = new JPanel();
		outerRadiusPanel.add(new JLabel("Radio externo:"));
		outerRadiusPanel.add(OuterRadius);
		inputPanel.add(outerRadiusPanel);
		
		JPanel radiusPanel = new JPanel();
		radiusPanel.add(new JLabel("Radio interno (en blanco para un círculo):"));
		radiusPanel.add(InnerRadius);
		inputPanel.add(radiusPanel);
		
		inputPanel.add(new JLabel("Punto de origen: (0,0) por defecto"));
		
		JPanel oXPanel = new JPanel();
		oXPanel.add(new JLabel("x:"));
		oXPanel.add(xFieldO);
		inputPanel.add(oXPanel);
		
		JPanel oYPanel = new JPanel();
		oYPanel.add(new JLabel("y:"));
		oYPanel.add(yFieldO);
		inputPanel.add(oYPanel);	
		
		inputPanel.add(space);
		
		int result = JOptionPane.showConfirmDialog(null, inputPanel, 
				"Perforacion circular", JOptionPane.OK_CANCEL_OPTION);
		
		/*Solamente hacemos algo si el usuario confirmo el ingreso de los puntos*/
		if (result == JOptionPane.OK_OPTION) {
			/*Chequear si el usuario desea un punto de origen especifico->por ahora asumir buenos usuarios (no va a ingresar
			 * en un solo espacio)*/
			double origenX = 0;
			double origenY = 0;
			
			if(xFieldO.getText().length () != 0){
				origenX = Double.parseDouble(xFieldO.getText());
				origenY = Double.parseDouble(yFieldO.getText());
			}
		
			double InnerR = 0;	
			if(InnerRadius.getText().length() != 0){
				InnerR = Double.parseDouble(InnerRadius.getText());	
			}
			
			double OuterR = Double.parseDouble(OuterRadius.getText());
			MyPoint origen = new MyPoint(origenX,origenY);
						
			MyRegion region;
			
			double[] angles = null;
			if(optionList.getSelectedIndex()==3){
				double angle1 = Double.parseDouble(initAngle.getText());
				double angle2 = Double.parseDouble(endAngle.getText());
				
				angles = new double[2];
				angles[0] = angle1;
				angles[1] = angle2;
			}
							
			CircleBounder cbp = new CircleBounder(origen,OuterR,angles);			
			MyScale scale;
			
			switch(optionList.getSelectedIndex()){
				case 0:
					PointInitProcess pip = new PointInitProcess(cbp.circleBounding(),window);					
					scale = pip.getScale();
					
					region = new FullCircularRegion(InnerR,OuterR,origen,scale);
					break;
				case 1:
					pip = new PointInitProcess(cbp.halfCircleBounding(),window);														
					scale = pip.getScale();
											
					double[] anglesHalf = {0,180};
					region = new PartialCircularRegion(InnerR,OuterR,origen,scale,anglesHalf);
					break;
				case 2:
					pip = new PointInitProcess(cbp.quarterCircleBounding(),window);
					scale = pip.getScale();
					
					double[] anglesQuarter = {0,90};
					region = new PartialCircularRegion(InnerR,OuterR,origen,scale,anglesQuarter);
					break;
				default:
					pip = new PointInitProcess(cbp.otherCircleBounding(InnerR),window);
					scale = pip.getScale();
				
					double angle1 = Double.parseDouble(initAngle.getText());
					double angle2 = Double.parseDouble(endAngle.getText());
					double[] anglesOther = {angle1,angle2};
					
					region = new PartialCircularRegion(InnerR,OuterR,origen,scale,anglesOther);
					break;
			}
			
			window.drawRegionInPanel(region, scale);
			window.repaint();
			
			
		}

		
		
		
		
		
		
		
		
		
		


	}

}
