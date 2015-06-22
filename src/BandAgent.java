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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setUp() {

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

		addBehaviour(new PlayMusic());

		addBehaviour(new CheerPublic());

		addBehaviour(new UseDrugs());
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

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		System.out.println("");
	}

}
