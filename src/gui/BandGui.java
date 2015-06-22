package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import agents.BandAgent;

public class BandGui extends JFrame {
    private static final long serialVersionUID = 1L;
     private BandAgent band;

    public BandGui(BandAgent agent){
    	band = agent;
    	
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(2,2));
		this.setTitle("Band " + agent.getLocalName() + " Controller! |..|,");
		
		
		JButton startShowButton = new JButton("Começar o Show!");
		startShowButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "Começou o show!");
					band.startShow();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		JButton playMusicButton = new JButton("Começar o Show!");
		playMusicButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "A música começou!");
					band.playMusic();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		
		JButton cheerPublicButton = new JButton("Animar a galera!");
		cheerPublicButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "Put yours hands UP !!!");
					band.cheerPublic();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton useDrugsButton = new JButton("Usar drogas :(");
		useDrugsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JOptionPane.showMessageDialog(null, "Todo mundo ficando malucão...");
					band.useDrugs();
				} catch (Exception error) {
					JOptionPane.showMessageDialog(null, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton stopShowButton = new JButton("Acabar o show...");
		stopShowButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ah, que pena... O show acabou :(");
				band.stopShow();
			}
		});
	
		panel.add(startShowButton);
		panel.add(playMusicButton);
		panel.add(cheerPublicButton);
		panel.add(useDrugsButton);
		panel.add(stopShowButton);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		this.setSize(500, 200);
		setVisible(true);
    }
}