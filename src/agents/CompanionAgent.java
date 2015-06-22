package agents;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;


public class CompanionAgent extends Agent implements Public{

	private static final long serialVersionUID = 1L;
	
	protected void setup() {
		
		
		ACLMessage letsRock = new ACLMessage(ACLMessage.INFORM);
		letsRock.setContent(FestivalAgent.LETSROCK);
		letsRock.addReceiver(new AID("RockInParadigmas", AID.ISLOCALNAME));
		send(letsRock);
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
