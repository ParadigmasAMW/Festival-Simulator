package agents;
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
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.PlatformController;
import jade.wrapper.StaleProxyException;

import java.util.Random;
import java.util.Vector;


public class FestivalAgent extends Agent {
	
	// MESSAGE TYPES
	public final static String LETSROCK = "LETSROCK";
	public final static String FESTIVALSTOPPED = "FESTIVALSTOPPED";

	private static final long serialVersionUID = 1L;
	private FestivalGui gui;
	
	protected AgentController actualBand;
	protected Vector<AgentController> publicList = new Vector<AgentController>();
	protected int publicCount = 0;
	
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
	
	private class StartFestival extends OneShotBehaviour {

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
	
	private class ListenPublic extends CyclicBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = receive();
			
			if(msg != null) {
				if(LETSROCK.equals(msg.getContent())){
					// Set festival state
					publicCount++;
					System.out.println(msg.getSender().getLocalName() + " join to festival!");
					gui.setPublicCount("Publico: " + publicCount);
				}
			}
		}
		
	}
	
	private class ChangeBand extends OneShotBehaviour {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void action(){			
			ACLMessage stopShow = new ACLMessage(ACLMessage.INFORM);
			stopShow.setContent(BandAgent.STOPSHOW);
			try {
				stopShow.addReceiver(new AID(actualBand.getName(), AID.ISGUID));
				send(stopShow);
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startBand("Banda 2");
		}
		
	}
	
	private class FinishFestival extends OneShotBehaviour {

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage stopFestival = new ACLMessage(ACLMessage.INFORM);
			stopFestival.setContent(FESTIVALSTOPPED);
			for(AgentController agent : publicList) {
				try {
					stopFestival.addReceiver(new AID(agent.getName(), AID.ISGUID));
					send(stopFestival);
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				stopFestival.addReceiver(new AID(actualBand.getName(), AID.ISGUID));
				send(stopFestival);
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Festival finished...");
		}
		
	}
	
	public void startFestival(){
		startBand("Banda 1");
		addBehaviour(new StartFestival());
		invitePublic(10);
		addBehaviour(new ListenPublic());
	}
	
	public void changeBand(){
		addBehaviour(new ChangeBand());
	}
	
	public void finishFestival(){
		addBehaviour(new FinishFestival());
	}
	
	protected void startBand(String bandName) {
		PlatformController c = getContainerController();
		AgentController agent;
		try {
			agent = c.createNewAgent(bandName, "agents.BandAgent", null);
			agent.start();
			actualBand = agent;
			gui.setActualBand(bandName + " se apresentando!! |..|,");
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void takeDown() {
		for(AgentController agent : publicList) {
			try {
				agent.kill();
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void invitePublic(int publicSize) {
		PlatformController c = getContainerController();
		try {
			for (int i = 0; i < publicSize; i++) {
				Random rand = new Random();
				int number = rand.nextInt(11) + 1;
				
				AgentController agent;
				if (number % 2 == 0){
					String localName = "Enthusiast " + i;
					agent = c.createNewAgent(localName, "agents.EnthusiastAgent", null);
				} else {
					String localName = "Companion " + i;
					agent = c.createNewAgent(localName, "agents.CompanionAgent", null);
				}
				agent.start();
				publicList.add(agent);
			}
		} catch (Exception e) {}
	}
}
