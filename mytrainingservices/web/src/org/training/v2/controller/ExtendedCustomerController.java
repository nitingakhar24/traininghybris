/**
 *
 */
package org.training.v2.controller;

import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commercewebservicescommons.dto.user.UserWsDTO;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.webservicescommons.cache.CacheControl;
import de.hybris.platform.webservicescommons.cache.CacheControlDirective;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.training.populator.HttpRequestCustomerDataPopulator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author nitingakhar
 *
 */
@Controller
@RequestMapping(value = "/{baseSiteId}/customers")
@CacheControl(directive = CacheControlDirective.PRIVATE)
@Api(tags = "Customers")
public class ExtendedCustomerController extends BaseCommerceController
{
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "httpRequestCustomerDataPopulator")
	private HttpRequestCustomerDataPopulator httpRequestCustomerDataPopulator;

	@Resource
	private UserService userService;

	@Resource
	private ModelService modelService;

	private static final Logger LOG = Logger.getLogger(ExtendedCustomerController.class);

	@Secured(
	{ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@RequestMapping(value = "/current/nickname/{userId:.+}", method = RequestMethod.GET)
	@ResponseBody
	public String getCustomerNickName(
			@ApiParam(value = "Response configuration. This is the list of fields that should be returned in the response body.", allowableValues = "BASIC, DEFAULT, FULL") @PathVariable final String userId)
	{
		final String userName = customerFacade.getUserForUID(userId).getNickname();
		return userName;
	}

	@Secured(
	{ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@RequestMapping(value = "/current/{nickName}/{userId:.+}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(hidden = true, value = "Updates customer nickName", notes = "Updates customer nick name. Attributes not provided in the request body will be defined again (set to null or default).")
	public void updateNickName(@PathVariable(value = "nickName") final String nickName,
			@PathVariable(value = "userId") final String userId, final HttpServletRequest request) throws DuplicateUidException
	{
		final CustomerModel user = (CustomerModel) userService.getUserForUID(userId);
		user.setNickname(nickName);
		modelService.save(user);

	}

	@Secured(
	{ "ROLE_CUSTOMERGROUP", "ROLE_TRUSTED_CLIENT", "ROLE_CUSTOMERMANAGERGROUP" })
	@RequestMapping(value = "/current/{userId:.+}", method = RequestMethod.GET)
	@ResponseBody
	public UserWsDTO getUserDetails(@ApiParam("Customer's last name.") @PathVariable final String userId,
			@ApiParam(value = "Response configuration. This is the list of fields that should be returned in the response body.", allowableValues = "BASIC, DEFAULT, FULL") @RequestParam(defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final CustomerData customerData = customerFacade.getUserForUID(userId);
		return getDataMapper().map(customerData, UserWsDTO.class, fields);
	}
}
