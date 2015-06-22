import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class BandGui extends JFrame {
    private static final long serialVersionUID = 1L;
    // private BandAgent band;

    public BandGui(){
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(4,1));
		this.setTitle("Band Controller! |..|,");
		
		
		JButton startShowButton = new JButton("Começar o Show!");
		startShowButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Começou o show!");
				
			}
		});
		
		JButton cheerPublicButton = new JButton("Animar a galera!");
		cheerPublicButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Uhuuuuul");
				
			}
		});
		
		JButton useDrugsButton = new JButton("Usar drogas :(");
		useDrugsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Todo mundo ficando malucão...");
				
			}
		});
		
		JButton stopShowButton = new JButton("Acabar o show...");
		stopShowButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ah, que pena... O show acabou :(");
				
			}
		});
	
		panel.add(startShowButton);
		panel.add(cheerPublicButton);
		panel.add(useDrugsButton);
		panel.add(stopShowButton);
		
		
		getContentPane().add(panel, BorderLayout.CENTER);
		this.setSize(200, 200);
		// pack();
		setVisible(true);
    }
}