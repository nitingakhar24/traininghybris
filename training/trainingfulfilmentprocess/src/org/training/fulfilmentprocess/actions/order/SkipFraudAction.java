/**
 *
 */
package org.training.fulfilmentprocess.actions.order;

import de.hybris.platform.commerceservices.model.PickUpDeliveryModeModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;

import org.apache.log4j.Logger;


/**
 * @author nitingakhar
 *
 */
public class SkipFraudAction extends AbstractSimpleDecisionAction<OrderProcessModel>
{
	private static final Logger LOG = Logger.getLogger(SkipFraudAction.class);

	@Override
	public Transition executeAction(final OrderProcessModel process)
	{
		LOG.info("Process: " + process.getCode() + " in step " + getClass().getSimpleName());
		final OrderModel order = process.getOrder();

		if (order.getDeliveryMode() instanceof PickUpDeliveryModeModel)
		{
			LOG.info("Order to be picked up, skipping the fraud check process");
			return Transition.OK;
		}
		else
		{
			return Transition.NOK;
		}
	}
}
