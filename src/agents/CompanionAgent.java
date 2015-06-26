package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class CompanionAgent extends Agent implements Public{

	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		
		
		ACLMessage letsRock = new ACLMessage(ACLMessage.INFORM);
		letsRock.setContent(FestivalAgent.LETSROCK);
		letsRock.addReceiver(new AID("RockInParadigmas", AID.ISLOCALNAME));
		send(letsRock);
		
		addBehaviour(new ListenFestivalOrders());
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

	@Override
	public void applause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void criticize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void praise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showBoobs() {
		// TODO Auto-generated method stub
		
	}

}
