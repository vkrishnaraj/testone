package com.bagnet.nettracer.tracing.utils.taskmanager.inbound;

import java.util.List;


public interface AutoAssign {
	/**
	 * Auto assigns agent InboundQueueTasks from the provided list based on the assigned parameters provided in the list of UnassignedInboundAgentElements
	 * 
	 * @param matrix
	 * @param taskList
	 */
	public void autoAssignedTasks(List<UnassignedInboundAgentElement> matrix, List<UnassignedIncidentElement> incidentList);
}
