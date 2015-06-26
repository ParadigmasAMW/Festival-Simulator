package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import agents.FestivalAgent;

public class FestivalGui extends JFrame {
    private static final long serialVersionUID = 1L;
    private FestivalAgent festival;
    private Box bandPublicBox;
    private Box publicRateBox;
    private Box resultBox;
    
    private Label publicCountLabel = new Label("Festival vazio :(");
    private Label actualBandLabel = new Label("Nenhuma banda tocando");
    
    private Label likesLabel = new Label("0 pessoas estão curtindo o show!");
    private Label dislikesLabel = new Label("0 pessoas não estão curtindo...");
    
    private Label resultLabel = new Label();
    private Label resultDetailLabel = new Label();

    public FestivalGui(FestivalAgent agent){
    	festival = agent;
    	
    	JPanel panel = new JPanel();
    	bandPublicBox = Box.createVerticalBox();
    	publicRateBox = Box.createVerticalBox();
    	resultBox = Box.createVerticalBox();
    	
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
		
		publicRateBox.add(likesLabel);
		publicRateBox.add(dislikesLabel);
		
		resultBox.add(resultLabel);
		resultBox.add(resultDetailLabel);
		
		panel.add(startFestivalButton);
		panel.add(changeBandButton);
		panel.add(finishFestivalButton);
		panel.add(bandPublicBox);
		panel.add(publicRateBox);
		panel.add(resultBox);
		
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
	
	public void increaseLike(String message) {
		this.likesLabel.setText(message);
	}
	
	public void increaseDislike(String message) {
		this.dislikesLabel.setText(message);
	}
	
	public void setResultLabel(String message) {
		this.resultLabel.setText(message);
	}
	
	public void setResultDetailsLabel(String message) {
		this.resultDetailLabel.setText(message);
	}
}