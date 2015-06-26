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

public class BandAgent extends Agent {
	private static final long serialVersionUID = 1L;

	public final static String STOPSHOW = "STOPSHOW";
	public final static String STARTSHOW = "STARTSHOW";

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
		
		addBehaviour(new ListenFestivalOrders());
		addBehaviour(new StartShow());
	}
	
	private class ListenFestivalOrders extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void action() {
			ACLMessage msg = receive();
			
			if(msg != null) {
				if(STOPSHOW.equals(msg.getContent())){
					// Set festival state
					System.out.println(getName() + " stopping the show...");
					doDelete();
				} else if(FestivalAgent.FESTIVALSTOPPED.equals(msg.getContent())) {
					System.out.println("Festival stopped. " + getName() + " stopping the show...");
					doDelete();
				}
			}
		}
		
	}
	
	private class StartShow extends OneShotBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.addReceiver(new	AID("RockInParadigmas", AID.ISLOCALNAME));
			msg.setContent(STARTSHOW);
			send(msg);
		}
		
	}
	
	private class StopShow extends OneShotBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.addReceiver(new	AID("Banda", AID.ISLOCALNAME));
			msg.setLanguage("English"); 
			msg.setContent("Stopping the show !!!");
			send(msg);
		}
		
	}
	
	public void startShow() {
		addBehaviour(new StartShow());
	}

	public void stopShow() {
		addBehaviour(new StopShow());
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}
