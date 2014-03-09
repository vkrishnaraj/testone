package com.bagnet.nettracer.tracing.utils.taskmanager.inbound;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.InboundQueue;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.taskmanager.AcaaTask;
import com.bagnet.nettracer.tracing.db.taskmanager.DamagedTask;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundTask;

public class AutoAssignTest {
	
	private boolean PRINT_ASSIGN_RESULT = true;
	
	
	private class AssignResultElement{
		private UnassignedInboundAgentElement agent;
		
		private int expectedInbound;
		private int expectedACAA;
		private int expectedDamaged;
		
		private int actInbound;
		private int actACAA;
		private int actDamaged;
		
		public UnassignedInboundAgentElement getAgent() {
			return agent;
		}
		public void setAgent(UnassignedInboundAgentElement agent) {
			this.agent = agent;
		}
		public int getExpectedInbound() {
			return expectedInbound;
		}
		public void setExpectedInbound(int expectedInbound) {
			this.expectedInbound = expectedInbound;
		}
		public int getExpectedACAA() {
			return expectedACAA;
		}
		public void setExpectedACAA(int expectedACAA) {
			this.expectedACAA = expectedACAA;
		}
		public int getExpectedDamaged() {
			return expectedDamaged;
		}
		public void setExpectedDamaged(int expectedDamaged) {
			this.expectedDamaged = expectedDamaged;
		}
		public int getActInbound() {
			return actInbound;
		}
		public void setActInbound(int actInbound) {
			this.actInbound = actInbound;
		}
		public int getActACAA() {
			return actACAA;
		}
		public void setActACAA(int actACAA) {
			this.actACAA = actACAA;
		}
		public int getActDamaged() {
			return actDamaged;
		}
		public void setActDamaged(int actDamaged) {
			this.actDamaged = actDamaged;
		}
	}
	
	private class AssignResult {
		
		private String title;
		private List<UnassignedIncidentElement>incidents;
		private List<AssignResultElement>results;
		
		private int totalAcaa;
		private int totalDamage;
		private int totalInbound;
		
		public AssignResult(String title){
			this.title = title;
		}
		
		public List<UnassignedIncidentElement> getIncidents() {
			return incidents;
		}
		public void setIncidents(List<UnassignedIncidentElement> incidents) {
			this.incidents = incidents;
		}
		public List<AssignResultElement> getResults() {
			return results;
		}
		public void setResults(List<AssignResultElement> results) {
			this.results = results;
		}
		public String getTitle() {
			return title;
		}

		public int getTotalAcaa() {
			return totalAcaa;
		}

		public void setTotalAcaa(int totalAcaa) {
			this.totalAcaa = totalAcaa;
		}

		public int getTotalDamage() {
			return totalDamage;
		}

		public void setTotalDamage(int totalDamage) {
			this.totalDamage = totalDamage;
		}

		public int getTotalInbound() {
			return totalInbound;
		}

		public void setTotalInbound(int totalInbound) {
			this.totalInbound = totalInbound;
		}
	}
	
	
	private List<UnassignedIncidentElement> createIncidentList(AssignResult results, int inbound, int acaa, int damaged){
		results.setTotalAcaa(acaa);
		results.setTotalInbound(inbound);
		results.setTotalDamage(damaged);
		
		ArrayList<UnassignedIncidentElement> elements = new ArrayList<UnassignedIncidentElement>();
		for(int i = 0; i < inbound; i++){
			elements.add(createIncident(new InboundTask()));
		}
		for(int i = 0; i < acaa; i++){
			elements.add(createIncident(new AcaaTask()));
		}
		for(int i = 0; i < damaged; i++){
			elements.add(createIncident(new DamagedTask()));
		}
		return elements;
	}
	
	private UnassignedIncidentElement createIncident(InboundQueueTask task){
		UnassignedIncidentElement element = new UnassignedIncidentElement();
		element.setIncident(new Incident());
		ArrayList<InboundQueueTask> tasks = new ArrayList<InboundQueueTask>();
		tasks.add(task);
		element.setTasks(tasks);
		InboundQueue inboundqueue = new InboundQueue();
		inboundqueue.setIncident(element.getIncident());
		task.setInboundqueue(inboundqueue);
		return element;
	}
	
	
	
	
	private AssignResultElement createAgent(double load, boolean inbound, boolean acaa, boolean damaged, int expInbound, int expAcaa, int expDamaged){
		UnassignedInboundAgentElement agent = new UnassignedInboundAgentElement();
		agent.setAgent(new Agent());
		agent.setLoad(load);
		agent.setInbound(inbound);
		agent.setAcaa(acaa);
		agent.setDamaged(damaged);
		AssignResultElement element = new AssignResultElement();
		element.setAgent(agent);
		element.setExpectedInbound(expInbound);
		element.setExpectedACAA(expAcaa);
		element.setExpectedDamaged(expDamaged);
		return element;
	}
	
	
	private void assign(AssignResult results){
		ArrayList<UnassignedInboundAgentElement> agents = new ArrayList<UnassignedInboundAgentElement>();
		for(AssignResultElement result:results.getResults()){
			agents.add(result.getAgent());
		}
		InboundTasksUtils.getAutoAssignImpl().autoAssignedTasks(agents, results.getIncidents());
		updateResults(results);
		printAssignResult(results);
		assertTrue(assertAssignResult(results));
	}
	
	private void updateResults(AssignResult results){
		for(AssignResultElement resultElement:results.getResults()){
			for(UnassignedIncidentElement incident: results.getIncidents()){
				if(resultElement.getAgent().getAgent().equals(incident.getIncident().getAgentassigned())){
					if(incident.getPriorityTask().isInbound()){
						resultElement.setActInbound(resultElement.getActInbound() + 1);
					}
					if(incident.getPriorityTask().isAcaa()){
						resultElement.setActACAA(resultElement.getActACAA() + 1);
					}
					if(incident.getPriorityTask().isDamaged()){
						resultElement.setActDamaged(resultElement.getActDamaged() + 1);
					}
				}
			}
		}
	}
	
	private void printAssignResult(AssignResult results){
		if(!PRINT_ASSIGN_RESULT){
			return;
		}
		
		ArrayList<String> printList = new ArrayList<String>();
		
		printList.add(String.format("\n\nTEST: %s  %s\n", results.getTitle(),assertAssignResult(results)?"PASSED":"FAILED"));
		
		printList.add("            Load     I   A   D");
		int i = 0;
		for(AssignResultElement element:results.getResults()){
			printList.add(String.format("Agent %2d: %6.2f     %s   %s   %s",i,element.getAgent().getLoad(), element.getAgent().isInbound()?"X":" ",element.getAgent().isAcaa()?"X":" ",element.getAgent().isDamaged()?"X":" "));
			i++;
		}
		
		printList.add(String.format("\nTotal Tasks        %3d %3d %3d\n",results.getTotalInbound(), results.getTotalAcaa(), results.getTotalDamage()));
		
		i = 0;
		for(AssignResultElement element:results.getResults()){
			printList.add(String.format("Agent %2d: Expected %3d %3d %3d",i,element.getExpectedInbound(),element.getExpectedACAA(),element.getExpectedDamaged()));
			printList.add(String.format("Agent %2d: Actual   %3d %3d %3d",i,element.getActInbound(),element.getActACAA(),element.getActDamaged()));
			i++;
		}
		
		System.out.println(StringUtils.join(printList.toArray(),'\n'));
	}
	
	private boolean assertAssignResult(AssignResult results){
		boolean success = true;
		for(AssignResultElement result:results.getResults()){
			if(result.getActACAA() != result.getExpectedACAA() ||
			   result.getActDamaged() != result.getExpectedDamaged() ||
			   result.getActInbound() != result.getExpectedInbound()){
				success = false;
			}
		}
		return success;
	}
	
	@Test
	public void baseTest(){
		AssignResult results = new AssignResult("baseTest");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,9, 9, 9));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(100.0, true, true, true, 3, 3, 3));
		resultList.add(createAgent(100.0, true, true, true, 3, 3, 3));
		resultList.add(createAgent(100.0, true, true, true, 3, 3, 3));
		
		assign(results);
	}
	
	@Test
	public void acaa1Test(){
		AssignResult results = new AssignResult("acaa1Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 1, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 0, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 0, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa2Test(){
		AssignResult results = new AssignResult("acaa2Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 2, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 0, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa3Test(){
		AssignResult results = new AssignResult("acaa3Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 3, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 1, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa4Test(){
		AssignResult results = new AssignResult("acaa4Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 4, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 2, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa5Test(){
		AssignResult results = new AssignResult("acaa5Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 5, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 2, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa6Test(){
		AssignResult results = new AssignResult("acaa6Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 6, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 1, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 3, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa7Test(){
		AssignResult results = new AssignResult("acaa7Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 7, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 3, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa8Test(){
		AssignResult results = new AssignResult("acaa8Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 8, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 4, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa9Test(){
		AssignResult results = new AssignResult("acaa9Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 9, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 3, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 4, 0));
		
		assign(results);
	}
	
	@Test
	public void acaa10Test(){
		AssignResult results = new AssignResult("acaa10Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,0, 10, 0));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 0, 2, 0));
		resultList.add(createAgent(50.0,  true, true, true, 0, 3, 0));
		resultList.add(createAgent(100.0, true, true, true, 0, 5, 0));
		
		assign(results);
	}
	
	@Test
	public void edgeCase1Test(){
		AssignResult results = new AssignResult("edgeCase1Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,2, 2, 2));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34.0,  true, true, true, 1, 1, 1));
		resultList.add(createAgent(50.0,  true, true, true, 1, 1, 1));
		resultList.add(createAgent(100.0, true, true, true, 0, 0, 0));
		
		assign(results);
	}
	
	@Test
	public void zeroLoad1Test(){
		AssignResult results = new AssignResult("zeroLoad1Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,9, 9, 9));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(0.0,  true, true, true, 3, 3, 3));
		resultList.add(createAgent(0.0,  true, true, true, 3, 3, 3));
		resultList.add(createAgent(0.0,  true, true, true, 3, 3, 3));
		
		assign(results);
	}
	
	@Test
	public void zeroLoad2Test(){
		AssignResult results = new AssignResult("zeroLoad2Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,9, 9, 9));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(0.0,  true, true, true, 0, 0, 0));
		resultList.add(createAgent(0.0,  true, true, true, 0, 0, 0));
		resultList.add(createAgent(1.0,  true, true, true, 9, 9, 9));
		
		assign(results);
	}
	
	@Test
	public void zeroLoad3Test(){
		AssignResult results = new AssignResult("zeroLoad3Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,9, 9, 9));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(0.0,  true,  false, false, 0, 0, 0));
		resultList.add(createAgent(0.0,  false, true,  true,  0, 0, 0));
		resultList.add(createAgent(1.0,  false, true,  true,  0, 9, 9));
		
		assign(results);
	}
	
	@Test
	public void normalize1Test(){
		AssignResult results = new AssignResult("normalize1Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,3, 5, 8));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(34,  true, true, true, 1, 2, 3));
		resultList.add(createAgent(50,  true, true, true, 2, 3, 5));
		
		assign(results);
	}
	
	@Test
	public void normalize2Test(){
		AssignResult results = new AssignResult("normalize2Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,3, 5, 8));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(67,   true, true, true, 1, 2, 3));
		resultList.add(createAgent(100,  true, true, true, 2, 3, 5));
		
		assign(results);
	}
	
	@Test
	public void normalize3Test(){
		AssignResult results = new AssignResult("normalize3Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,3, 5, 8));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(0.34,  true, true, true, 1, 2, 3));
		resultList.add(createAgent(0.50,  true, true, true, 2, 3, 5));
		
		assign(results);
	}
	
	@Test
	public void normalize4Test(){
		AssignResult results = new AssignResult("normalize4Test");
		
		/* create tasks (inbound/acaa/damaged) */
		results.setIncidents(createIncidentList(results,3, 5, 8));
		
		/* create agents (load/inbound/acaa/damaged/expInbound/expAcaa/expDamaged) */
		ArrayList<AssignResultElement> resultList = new ArrayList<AssignResultElement>();
		results.setResults(resultList);
		resultList.add(createAgent(0.50,  true, true, true, 2, 3, 5));
		resultList.add(createAgent(0.34,  true, true, true, 1, 2, 3));

		assign(results);
	}
}
