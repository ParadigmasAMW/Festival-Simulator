package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import agents.BandAgent;
import agents.FestivalAgent;

public class FestivalGui extends JFrame {
    private static final long serialVersionUID = 1L;
     private FestivalAgent festival;

    public FestivalGui(FestivalAgent agent){
    	festival = agent;
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(2,2));
		this.setTitle(agent.getLocalName() + " Controller! |..|,");
		
		
		JButton startFestivalButton = new JButton("Começar festival!");
		startFestivalButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "O festival começou!");
					festival.startFestival();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		JButton changeBandButton = new JButton("Trocar Banda!");
		changeBandButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "A nova banda já está no palco!");
					festival.changeBand();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		JButton finishFestivalButton = new JButton("Terminar festival!");
		finishFestivalButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "O festival acabou :(");
					festival.finishFestival();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});

		panel.add(startFestivalButton);
		panel.add(changeBandButton);
		panel.add(finishFestivalButton);

		
		getContentPane().add(panel, BorderLayout.CENTER);
		this.setSize(500, 200);
		setVisible(true);
    }
}