package agents;
import gui.FestivalGui;
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
	public final static String PUBLICLEFT = "PUBLICLEFT";
	public final static String LIKE = "LIKE";
	public final static String DISLIKE = "DISLIKE";
	public final static String CHANGEMUSIC = "CHANGEMUSIC";

	private static final long serialVersionUID = 1L;
	private FestivalGui gui;
	private int likes = 0;
	private int dislikes = 0;
	
	protected AgentController actualBand;
	private Vector<AgentController> publicList = new Vector<AgentController>();
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
	
	private class CheckLikes extends TickerBehaviour {

		private static final long serialVersionUID = 1L;

		public CheckLikes(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub
			if((likes + dislikes) == publicCount) {
				if(likes >= dislikes) {
					gui.setResultLabel("Parece que o pessoal gostou da banda!");
					gui.setResultDetailsLabel("Próxima música!!");
					addBehaviour(new ChangeMusic());
				} else {
					gui.setResultLabel("Parece que essa banda nao foi tao boa...");
					gui.setResultDetailsLabel("Próxima banda!!");
				}
			} else {
				gui.setResultLabel("");
				gui.setResultDetailsLabel("");
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
				} else if(BandAgent.STARTSHOW.equals(msg.getContent())) {
					startedShowMessage();
				} else if(LIKE.equals(msg.getContent())) {
					setLikes(getLikes() + 1);
					gui.increaseLike(getLikes() + " pessoas estão curtindo o show!");
					System.out.println(msg.getSender().getLocalName() + " liked the band!");
				} else if(DISLIKE.equals(msg.getContent())) {
					setDislikes(getDislikes() + 1);
					gui.increaseDislike(getDislikes() + " pessoas não estão curtindo...");
					System.out.println(msg.getSender().getLocalName() + " disliked the band!");
				} else if(PUBLICLEFT.equals(msg.getContent())) {
					publicCount--;
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
			addBehaviour(new ChangeMusic());
		}	
	}
	
	private class ChangeMusic extends OneShotBehaviour {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			likes = 0;
			dislikes = 0;
			gui.increaseLike(likes + " pessoas estão curtindo o show!");
			gui.increaseDislike(dislikes + " pessoas não estão curtindo...");
			
			for(AgentController agent : publicList) {
				ACLMessage newMusic = new ACLMessage(ACLMessage.INFORM);
				newMusic.setContent(CHANGEMUSIC);
				try {
					newMusic.addReceiver(new AID(agent.getName(), AID.ISGUID));
					send(newMusic);
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			startedShowMessage();
			
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
	
	private void startedShowMessage() {
		ACLMessage startShow = new ACLMessage(ACLMessage.INFORM);
		startShow.setContent(BandAgent.STARTSHOW);
		for(AgentController agent : publicList) {
			try {
				startShow.addReceiver(new AID(agent.getName(), AID.ISGUID));
				send(startShow);
			} catch (StaleProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startFestival(){
		startBand("Banda 1");
		invitePublic(20);
		addBehaviour(new CheckLikes(this, 3000));
		addBehaviour(new ListenPublic());
	}
	
	public void changeBand(){
		gui.increaseLike("0 pessoas estão curtindo o show!");
		gui.increaseDislike("0 pessoas não estão curtindo...");
		this.likes = 0;
		this.dislikes = 0;
		addBehaviour(new ChangeBand());
		startedShowMessage();
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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
}
