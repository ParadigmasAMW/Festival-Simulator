package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.util.Random;


public class EnthusiastAgent extends Agent implements Public{

	private static final long serialVersionUID = 1L;
	private String musicStyle;

	protected void setup() {
		setMusicStyle();

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(this.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("music-festival");
		sd.setName("JADE-music-festival");
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		ACLMessage letsRock = new ACLMessage(ACLMessage.INFORM);
		letsRock.setContent(FestivalAgent.LETSROCK);
		letsRock.addReceiver(new AID("RockInParadigmas", AID.ISLOCALNAME));
		send(letsRock);
		
		addBehaviour(new ListenFestivalOrders());
	}
	
	private class Applause extends CyclicBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class ListenFestivalOrders extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = receive();
			
			if(msg != null) {
				if(FestivalAgent.FESTIVALSTOPPED.equals(msg.getContent())) {
					System.out.println("Festival stopped. " + getName() + " leaving the festival...");
					doDelete();
				}
			}
			
		}
		
	}
	
	private class Boo extends CyclicBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class Criticize extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class Praise extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class ShowBoobs extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	@Override
	public void applause() {
		addBehaviour(new Applause());
	}

	@Override
	public void boo() {
		addBehaviour(new Boo());
	}

	@Override
	public void criticize() {
		addBehaviour(new Criticize());
	}

	@Override
	public void praise() {
		addBehaviour(new Praise());
	}

	@Override
	public void showBoobs() {
		addBehaviour( new ShowBoobs());
	}
	
	private void setMusicStyle() {
		Random rand = new Random();
		int number = rand.nextInt(11) + 1;

		if (number % 2 == 0){
			this.musicStyle = MusicStyle.BLUES;
		} else {
			this.musicStyle = MusicStyle.HEAVYMETAL;
		}
	}

}
