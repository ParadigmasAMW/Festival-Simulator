package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import agents.FestivalAgent;

public class FestivalGui extends JFrame {
    private static final long serialVersionUID = 1L;
    private FestivalAgent festival;
    private Box bandPublicBox;
    private Box publicRateBox;
    
    private Label publicCountLabel = new Label("Festival vazio :(");
    private Label actualBandLabel = new Label("Nenhuma banda tocando");

    public FestivalGui(FestivalAgent agent){
    	festival = agent;
    	
    	JPanel panel = new JPanel();
    	bandPublicBox = Box.createVerticalBox();
    	publicRateBox = Box.createVerticalBox();
    	
    	panel.setLayout(new GridLayout(2,3));
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
		
		bandPublicBox.add(publicCountLabel);
		bandPublicBox.add(actualBandLabel);
		
		panel.add(startFestivalButton);
		panel.add(changeBandButton);
		panel.add(finishFestivalButton);
		panel.add(bandPublicBox);
		panel.add(publicRateBox);

		
		getContentPane().add(panel, BorderLayout.CENTER);
		this.setSize(900, 200);
		setVisible(true);
    }

	public void setPublicCount(String message) {
		this.publicCountLabel.setText(message);
	}
	
	public void setActualBand(String message) {
		this.actualBandLabel.setText(message);
	}
}