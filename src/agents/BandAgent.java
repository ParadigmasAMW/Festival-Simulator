package agents;
import gui.BandGui;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;

public class BandAgent extends Agent {

	private static final long serialVersionUID = 1L;

	public final static String STOPSHOW = "STOPSHOW";
	
	
	private BandGui gui;

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

	private class PlayMusic extends CyclicBehaviour {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			jade.lang.acl.ACLMessage msg = myAgent.receive(mt);
		}
	}

	private class CheerPublic extends OneShotBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.addReceiver(new	AID("Banda", AID.ISLOCALNAME));
			msg.setLanguage("English"); 
			msg.setContent("Put yours hands UP !!!");
			send(msg);
		}
	}
	
	private class UseDrugs extends OneShotBehaviour {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void action(){
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.addReceiver(new	AID("Banda", AID.ISLOCALNAME));
			msg.setLanguage("English"); 
			msg.setContent("Using some Drugs !!!");
			send(msg);
		}
	}
	
	private class StartShow extends OneShotBehaviour{
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		    msg.addReceiver(new	AID("Banda", AID.ISLOCALNAME));
			msg.setLanguage("English"); 
			msg.setContent("Starting the show !!!");
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
	
	public void cheerPublic() {
		addBehaviour(new CheerPublic());
	}
	
	public void useDrugs() {
		addBehaviour(new UseDrugs());
	}
	
	public void playMusic() {
		addBehaviour(new PlayMusic());
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

		System.out.println("");
	}

}
