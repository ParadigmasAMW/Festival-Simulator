package agents;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class EnthusiastAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private Boolean liked = false;

	protected void setup() {
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
		
		Random rand = new Random();
		
		addBehaviour(new TickerBehaviour(this, rand.nextInt(3000) + 1000) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				// TODO Auto-generated method stub
				ACLMessage msg = receive();
				
				if(msg != null) {
					if(FestivalAgent.FESTIVALSTOPPED.equals(msg.getContent())) {
						System.out.println("Festival stopped. " + getName() + " leaving the festival...");
						ACLMessage publicLeft = new ACLMessage(ACLMessage.INFORM);
						publicLeft.setContent(FestivalAgent.PUBLICLEFT);
						publicLeft.addReceiver(new AID("RockInParadigmas", AID.ISLOCALNAME));
						send(publicLeft);
						doDelete();
					} else if(BandAgent.STARTSHOW.equals(msg.getContent())) {
						if(!liked) {
							addBehaviour(new Applause());
							liked = true;
						}
					} else if(FestivalAgent.CHANGEMUSIC.equals(msg.getContent())) {
						if(liked) {
							liked = false;
						}
					}
				} 
			}
		});
	}
	
	private class Applause extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage praiseMessage = new ACLMessage(ACLMessage.INFORM);
			praiseMessage.setContent(FestivalAgent.LIKE);
			praiseMessage.addReceiver(new AID("RockInParadigmas", AID.ISLOCALNAME));
			send(praiseMessage);
		}
		
	}
	
	public void setLiked(Boolean status) {
		this.liked = status;
	}
}
