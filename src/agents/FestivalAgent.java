package agents;
import examples.content.Receiver;
import gui.FestivalGui;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.tools.testagent.ReceiveCyclicBehaviour;


public class FestivalAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private FestivalGui gui;
	
	protected void setup(){
		gui = new FestivalGui(this);
		
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

	}
	
	private class StartFestival extends CyclicBehaviour {

		private static final long serialVersionUID = 1L;

		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
					System.out.println(" Festival has begun "+msg.getSender().getName());
			}
			else {
				block();
			}
		}
		
	}
	
	private class ChangeBand extends OneShotBehaviour {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void action(){
			ACLMessage msg = myAgent.receive();
			if(msg !=null) {
			// Message received. Process it
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.INFORM);
				reply.setContent("Changing Band !!!");
			}
				myAgent.send(msg);
		}
		
	}
	
	private class FinishFestival extends OneShotBehaviour {

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			System.out.println("Festival-agent "+getAID().getName()+" terminating.");
		}
		
	}
	
	public void startFestival(){
		addBehaviour(new StartFestival());		
	}
	
	public void changeBand(){
		addBehaviour(new ChangeBand());
	}
	
	public void finishFestival(){
		addBehaviour(new FinishFestival());
	}
}
