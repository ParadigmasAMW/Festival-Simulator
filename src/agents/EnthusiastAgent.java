package agents;
import gui.BandGui;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class EnthusiastAgent extends Agent implements Public{

	private static final long serialVersionUID = 1L;

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
	}
	private class Applause extends CyclicBehaviour{

		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
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

}
