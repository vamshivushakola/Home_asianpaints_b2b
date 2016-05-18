package de.hybris.platform.omsb2b.actions.b2b;

import de.hybris.platform.b2b.process.approval.event.ApprovalProcessCompleteEvent;
import de.hybris.platform.b2b.process.approval.model.B2BApprovalProcessModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.task.RetryLaterException;
import de.hybris.platform.omsb2b.constants.Omsb2bConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


public class OmsApprovalProcessCompleteAction extends AbstractSimpleDecisionAction<B2BApprovalProcessModel>
{
	 private static final Logger LOGGER = Logger.getLogger(OmsApprovalProcessCompleteAction.class);
	 
	    private BusinessProcessService businessProcessService;
	    private EventService eventService;
	 
	    @Override
	    public Transition executeAction(B2BApprovalProcessModel b2BApprovalProcessModel) throws RetryLaterException, Exception
	    {
	        eventService.publishEvent(new ApprovalProcessCompleteEvent(b2BApprovalProcessModel));
	        final OrderProcessModel continuationProcess = getBusinessProcessService().<OrderProcessModel> createProcess(
	                b2BApprovalProcessModel.getOrder().getCode() + "_ordermanagement", Omsb2bConstants.ORDER_PROCESS_NAME);
	        continuationProcess.setOrder(b2BApprovalProcessModel.getOrder());
	        save(continuationProcess);
	        LOGGER.info("Start Order continuation-process: '" + continuationProcess.getCode() + "'");
	        getBusinessProcessService().startProcess(continuationProcess);
	        return Transition.OK;
	    }
	 
	    protected BusinessProcessService getBusinessProcessService()
	    {
	        return businessProcessService;
	    }
	 
	    @Required
	    public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	    {
	        this.businessProcessService = businessProcessService;
	    }
	 
	    protected EventService getEventService()
	    {
	        return eventService;
	    }
	 
	    @Required
	    public void setEventService(EventService eventService)
	    {
	        this.eventService = eventService;
	    }
}
