package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.InboundTasksBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.InboundQueue;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.taskmanager.AcaaTask;
import com.bagnet.nettracer.tracing.db.taskmanager.DamagedTask;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundTask;
import com.bagnet.nettracer.tracing.dto.InboundTasksDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class InboundTasksUtils {
	private static Logger logger = Logger.getLogger(InboundTasksUtils.class);
	
	
	private static InboundTasksBMO bmo = null;
	
	/**
	 * If bmo is null, create new PersonalTasksBMO.  Otherwise use existing bmo.
	 * This is to allow unit test to explicitly set a mock PersonalTasksBMO object.
	 * 
	 * @return
	 */
	protected static InboundTasksBMO getInboundTasksBMO(){
		if(bmo == null){
			bmo = new InboundTasksBMO();
		}
		return bmo;
	}
	
	/**
	 * @param bmo
	 */
	protected void setInboundTasksBMO(InboundTasksBMO bmo){
		this.bmo = bmo;
	}
	
	/**
	 * Returns the current count of InboundQueueTasks that are currently open and unassigned.  This method is primarily used for the task manager count in the LogonAction
	 * 
	 * @return
	 */
	public static int getUnassignedTasksCount(){
		InboundTasksDTO dto = new InboundTasksDTO();
		dto.setAssigned_agent(null);
		dto.setSearchUnassignedTasks(true);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		dto.setStatus(status);
		return (int)getInboundTasksBMO().getTasksCount(dto);
	}
	
	/**
	 * Returns the current count of InboundQueueTasks that are currently open and assigned to an agent.  This method is primarily used for the task manager count in the LogonAction
	 * 
	 * @param agent
	 * @return
	 */
	public static int getAssignedTasksCount(Agent agent){
		InboundTasksDTO dto = new InboundTasksDTO();
		dto.setAssigned_agent(agent);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		dto.setStatus(status);
		return (int)getInboundTasksBMO().getTasksCount(dto);
	}
	
	/**
	 * Returns InboundQueueTasks count based on the criteria provided in the InboundTasksDTO
	 * 
	 * @param dto
	 * @return
	 */
	public static int getTasksCount(InboundTasksDTO dto){
		return (int)getInboundTasksBMO().getTasksCount(dto);
	}
	
	/**
	 * Returns a List of InboundQueueTasks based on the criteria provided in the InboundTasksDTO
	 * 
	 * Localizes results base on provided agent
	 * 
	 * @param dto
	 * @param agent
	 * @return
	 */
	public static List<InboundQueueTask> getTasks(InboundTasksDTO dto, Agent agent){
		List<InboundQueueTask> taskList = getInboundTasksBMO().getTasks(dto);
		if(agent != null && taskList != null){
			TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone()).getTimezone());
			for(InboundQueueTask task:taskList){
				task.set_DATEFORMAT(agent.getDateformat().getFormat());
				task.set_TIMEFORMAT(agent.getTimeformat().getFormat());
				task.set_TIMEZONE(timeZone);
				task.setLocale(agent.getCurrentlocale());
			}
		}
		return taskList;
	}
	
	/**
	 * Returns the InboundQueueTask based on the provided task id
	 * 
	 * @param id
	 * @return
	 */
	public static InboundQueueTask getTask(long id){
		InboundTasksDTO dto = new InboundTasksDTO();
		dto.setId(id);
		List<InboundQueueTask> taskList = getInboundTasksBMO().getTasks(dto);
		if(taskList != null && taskList.size() > 0){
			return taskList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Get the oldest InboundQueueTask that is open and assigned to the provided agent.
	 * 
	 * @param agent
	 * @return
	 */
	public static InboundQueueTask getNextAssignedTask(Agent agent){
		InboundTasksDTO dto = new InboundTasksDTO();
		dto.setAssigned_agent(agent);
		dto.setSort("opened_timestamp");
		dto.setDir("desc");
		dto.setMaxResults(1);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		List<InboundQueueTask> taskList = getInboundTasksBMO().getTasks(dto);
		if(taskList != null && taskList.size() > 0){
			return taskList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Closes the provided InboundQueueTask
	 * 
	 * @param task
	 * @param agent
	 * @return
	 */
	public static boolean closeTask(InboundQueueTask task, Agent agent){
		task.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_CLOSED);
		task.setStatus(status);
		return getInboundTasksBMO().saveTask(task, agent) > 0;
	}
	
	/**
	 * Closes the corresponding InboundQueueTask for the provided IncidentActivity ID
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public static boolean closeTaskByIncidentActivityId(long id, Agent agent){
		InboundTasksDTO dto = new InboundTasksDTO();
		dto.setIncidentActivityId(id);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		dto.setStatus(status);
		List<InboundQueueTask> taskList = getInboundTasksBMO().getTasks(dto);
		if(taskList != null && taskList.size() == 1){
					taskList.get(0);
					return closeTask(taskList.get(0), agent);
		} else {
			return false;
		}
	}
	
	public static boolean hasOpenInboundTaskByActivityId(long id){
		InboundTasksDTO dto = new InboundTasksDTO();
		dto.setIncidentActivityId(id);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		dto.setStatus(status);
		List<InboundQueueTask> taskList = getInboundTasksBMO().getTasks(dto);
		if(taskList != null && taskList.size() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * saves the provided task
	 * 
	 * @param task
	 * @param agent
	 * @return
	 */
	public static long saveTask(InboundQueueTask task, Agent agent){
		if(task != null && task.getInboundqueue() != null && task.getInboundqueue().getIncident() != null){
			Incident incident = IncidentBMO.getIncidentByID(task.getInboundqueue().getIncident().getIncident_ID(), null);
			incident.setAgentassigned(task.getInboundqueue().getIncident().getAgentassigned());
			task.getInboundqueue().setIncident(incident);
		}

		return getInboundTasksBMO().saveTask(task, agent);
	}
	
	/**
	 * Creates an InboundTask
	 * 
	 * If incident is a ACAA (defined by the item type being either 94 or 95), an AcaaTask will be created instead
	 * 
	 * @param incident
	 * @param agent
	 * @return
	 */
	public static long createInboundTask(Incident incident, Agent agent, Activity activity, long incidentActivityId){
		if(isAcaa(incident)){
			return createAcaaTask(incident, agent, activity, incidentActivityId);
		}
		InboundTask task = new InboundTask();
		return createInboundQueueTask(task, incident, agent, activity, incidentActivityId);
	}
	
	
	/**
	 * Creates an AcaaTask
	 * 
	 * @param incident
	 * @param agent
	 * @return
	 */
	public static long createAcaaTask(Incident incident, Agent agent, Activity activity, long incidentActivityId){
		AcaaTask task = new AcaaTask();
		return createInboundQueueTask(task, incident, agent, activity, incidentActivityId);
	}
	
	/**
	 * Creates a DamagedTask
	 * 
	 * @param incident
	 * @param agent
	 * @return
	 */
	public static long createDamagedTask(Incident incident, Agent agent, Activity activity, long incidentActivityId){
		DamagedTask task = new DamagedTask();
		return createInboundQueueTask(task, incident, agent, activity, incidentActivityId);
	}
	
	/**
	 * Creates a new InboundQueueTask based on the provided subtask (Inbound, Acaa or Damaged)
	 * 
	 * @param task
	 * @param incident
	 * @param agent
	 * @return
	 */
	private static long createInboundQueueTask(InboundQueueTask task, Incident incident, Agent agent, Activity activity, long incidentActivityId){
		InboundQueue inboundqueue = new InboundQueue();
		task.setInboundqueue(inboundqueue);
		inboundqueue.setIncident(incident);
		inboundqueue.setActivity(activity);
		inboundqueue.setIncidentActivityId(incidentActivityId);
		Status status = new Status();
		status.setStatus_ID(TracingConstants.TASK_MANAGER_OPEN);
		task.setStatus(status);
		task.setOpened_timestamp(DateUtils.convertToGMTDate(new Date()));
		return saveTask(task, agent);
	}
	
	/**
	 * Identifies if an incident is an ACAA incident.  An ACAA incident is defined as an incident that contains at least one item that is of type 94 or 95
	 * 
	 * @param incident
	 * @return
	 */
	private static boolean isAcaa(Incident incident){
		boolean isAcaa = false;
		if(incident != null && incident.getItemlist() != null){
			for(Item item: incident.getItemlist()){
				if(item.getItemtype_ID() == 94 || item.getItemtype_ID() == 95){
					isAcaa = true;
				}
			}
		}
		return isAcaa;
	}
	
	/**
	 * Returns a List of UnassignedInboundAgentElements based on the list of agents that are authorized to work on InboundQueueTasks
	 * 
	 * @return
	 */
	public static List<UnassignedInboundAgentElement> getInboundAgentMatrixList(){
		List<Agent> agents = getInboundAgentList();
		ArrayList<UnassignedInboundAgentElement> ret = new ArrayList<UnassignedInboundAgentElement>();
		if(agents != null){
			for(Agent agent:agents){
				UnassignedInboundAgentElement toAdd = new UnassignedInboundAgentElement();
				toAdd.setAgent(agent);
				ret.add(toAdd);
			}
		}
		return  ret;
	}
	
	/**
	 * Returns a List of Agent that are authorized to work on InboundQueueTasks
	 * 
	 * @return
	 */
	public static List<Agent> getInboundAgentList(){
		return getInboundTasksBMO().getInboundAgentList();
	}
	
	/**
	 * Auto assigns agent InboundQueueTasks from the provided list based on the assigned parameters provided in the list of UnassignedInboundAgentElements
	 * 
	 * TODO - describe algorithm
	 * 
	 * @param matrix
	 * @param taskList
	 * @return
	 */
	public static List<InboundQueueTask> autoAssignedTasks(List<UnassignedInboundAgentElement> matrix, List<InboundQueueTask> taskList){
		int totalTasks = taskList.size();
		double totalLoad = 0;
		for(UnassignedInboundAgentElement agent:matrix){
			totalLoad += agent.getLoad();
		}
		for(UnassignedInboundAgentElement agent:matrix){
			int maxTasks = (int) ((agent.getLoad()/totalLoad) * totalTasks) + 1;
			agent.setMaxAssign(maxTasks);
			agent.setTaskList(new ArrayList<InboundQueueTask>());
		}
		
		ArrayList<InboundQueueTask> returnList = new ArrayList<InboundQueueTask>();
		if(matrix != null && taskList != null){
			while(taskList.size() > 0){
				InboundQueueTask task = getNextTask(matrix, taskList);
				taskList.remove(task);
				UnassignedInboundAgentElement agent = getAssignAgent(matrix, task);
				if(agent != null){
					if(agent.getTaskList() == null){
						agent.setTaskList(new ArrayList<InboundQueueTask>());
					}
					agent.getTaskList().add(task);
					task.setAssigned_agent(agent.getAgent());
					task.getInboundqueue().getIncident().setAgentassigned(agent.getAgent());
				} else {
					task.setAssigned_agent(null);
					task.getInboundqueue().getIncident().setAgentassigned(null);
					returnList.add(task);//unassignable
				}
			}
		}
		

		for(UnassignedInboundAgentElement agent:matrix){
			if(agent.getTaskList() != null){
				returnList.addAll(agent.getTaskList());
			}
		}
		return returnList;
	}
	
	private static InboundQueueTask getNextTask(List<UnassignedInboundAgentElement> matrix, List<InboundQueueTask> taskList){
		//first determine of many of each task type exists
		int inbound = 0;
		int acaa = 0;
		int damaged = 0;
		InboundTask inboundTask = null;
		AcaaTask acaaTask = null;
		DamagedTask damagedTask = null;
		
		for(InboundQueueTask task: taskList){
			if(task instanceof InboundTask){
				inbound++;
				inboundTask = (InboundTask) task;
			}
			if(task instanceof AcaaTask){
				acaa++;
				acaaTask = (AcaaTask) task;
			}
			if(task instanceof DamagedTask){
				damaged++;
				damagedTask = (DamagedTask) task;
			}
		}
		
		//now determine how many available slots for each task type exists
		int inboundSlots = 0;
		int acaaSlots = 0;
		int damagedSlots = 0;
		
		for(UnassignedInboundAgentElement agent:matrix){
			if(agent.isInbound()){
				inboundSlots += agent.getRemainingSlots();
			}
			if(agent.isAcaa()){
				acaaSlots += agent.getRemainingSlots();
			}
			if(agent.isDamaged()){
				damagedSlots += agent.getRemainingSlots();
			}
		}
		
		//determine the priority of each type by taking the difference between the number of available slots and the number of that respective task remains.
		int inboundPriority = inboundSlots - inbound;
		int acaaPriority = acaaSlots - acaa;
		int damagedPriority = damagedSlots - damaged;
		
		InboundQueueTask retTask = null;
		int currentPriority = Integer.MAX_VALUE;
		
		//determine the task with the lowest priority (meaning that least amount of remaining slots for fulfillment)
		if(inbound > 0 && inboundPriority <= currentPriority){
			retTask = inboundTask;
			currentPriority = inboundPriority;
		}
		if(acaa > 0 && acaaPriority <= currentPriority){
			retTask = acaaTask;
			currentPriority = acaaPriority;
		}
		if(damaged > 0 && damagedPriority <= currentPriority){
			retTask = damagedTask;
			currentPriority = damagedPriority;
		}

		return retTask;
	}
	
	private static UnassignedInboundAgentElement getAssignAgent(List<UnassignedInboundAgentElement> matrix, InboundQueueTask task){
		int currentSlots = Integer.MIN_VALUE;
		UnassignedInboundAgentElement currentAgent = null;
		
		if(task instanceof InboundTask){
			for(UnassignedInboundAgentElement agent:matrix){
				if(agent.isInbound()){
					if(agent.getRemainingSlots() > currentSlots){
						currentAgent = agent;
						currentSlots = agent.getRemainingSlots();
					}
				}
			}
		}
		if(task instanceof AcaaTask){
			for(UnassignedInboundAgentElement agent:matrix){
				if(agent.isAcaa()){
					if(agent.getRemainingSlots() > currentSlots){
						currentAgent = agent;
						currentSlots = agent.getRemainingSlots();
					}
				}
			}
		}
		if(task instanceof DamagedTask){
			for(UnassignedInboundAgentElement agent:matrix){
				if(agent.isDamaged()){
					if(agent.getRemainingSlots() > currentSlots){
						currentAgent = agent;
						currentSlots = agent.getRemainingSlots();
					}
				}
			}
		}
		return currentAgent;
	}
	


	public static List<InboundQueueTask> sortTaskList(List<InboundQueueTask> tasks, String sort, String dir){
		if(tasks != null){
			InboundTasksUtils utils = new InboundTasksUtils();
			InboundTasksUtils.Sort c = utils.new Sort(sort,dir);

			Collections.sort(tasks, c);
		}
		return tasks;
	}
	
	private class Sort implements Comparator<InboundQueueTask>{
		String sort;
		String dir;
		
		public Sort(String sort, String dir){
			this.sort = sort;
			this.dir = dir;
		}
		
		@Override
		public int compare(InboundQueueTask o1, InboundQueueTask o2) {
			int ret = 0;
			
			if("username".equals(sort)){
				ret = o1.getInboundqueue().getIncident().getAgent().getUsername().compareTo(o2.getInboundqueue().getIncident().getAgent().getUsername());
			} else if ("type".equals(sort)){
				ret =  o1.getDescription().compareTo(o2.getDescription());
			} else if ("date".equals(sort)){
				ret =  o1.getOpened_timestamp().compareTo(o2.getOpened_timestamp());
			} else if ("incident".equals(sort)){
				ret =  o1.getInboundqueue().getIncident().getIncident_ID().compareTo(o2.getInboundqueue().getIncident().getIncident_ID());
			} else {
				ret = o1.getInboundqueue().getIncident().getAgent().getUsername().compareTo(o2.getInboundqueue().getIncident().getAgent().getUsername());
			}

			ret = "asc".equals(dir)?ret:-ret;
			return ret;
		}
	}
}
