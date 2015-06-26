package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Random;


public class CompanionAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private Boolean liked = false;
	
	protected void setup() {
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
							addBehaviour(new Boo());
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
	
	private class Boo extends OneShotBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage criticizeMessage = new ACLMessage(ACLMessage.INFORM);
			criticizeMessage.setContent(FestivalAgent.DISLIKE);
			criticizeMessage.addReceiver(new AID("RockInParadigmas", AID.ISLOCALNAME));
			send(criticizeMessage);
		}
		
	}
	
	public void setLiked(Boolean status) {
		this.liked = status;
	}
}
