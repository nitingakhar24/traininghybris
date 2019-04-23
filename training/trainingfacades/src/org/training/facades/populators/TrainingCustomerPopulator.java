/**
 *
 */
package org.training.facades.populators;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.SocialProfileData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;
import org.training.core.model.SocialProfileModel;



/**
 * @author nitingakhar
 *
 */
public class TrainingCustomerPopulator implements Populator<CustomerModel, CustomerData>
{
	private Converter<AddressModel, AddressData> addressConverter;
	private Converter<SocialProfileModel, SocialProfileData> socialProfileConverter;

	@Override
	public void populate(final CustomerModel source, final CustomerData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setNickname(source.getNickname());
		if (source.getWorkOfficeAddress() != null)
		{
			target.setWorkOfficeAddress(getAddressConverter().convert(source.getWorkOfficeAddress()));
		}
		if (source.getSocialProfile() != null)
		{
			target.setSocialProfile(getSocialProfileConverter().convert(source.getSocialProfile()));
		}
	}

	protected Converter<AddressModel, AddressData> getAddressConverter()
	{
		return addressConverter;
	}

	@Required
	public void setAddressConverter(final Converter<AddressModel, AddressData> addressConverter)
	{
		this.addressConverter = addressConverter;
	}

	public Converter<SocialProfileModel, SocialProfileData> getSocialProfileConverter()
	{
		return socialProfileConverter;
	}

	@Required
	public void setSocialProfileConverter(final Converter<SocialProfileModel, SocialProfileData> socialProfileConverter)
	{
		this.socialProfileConverter = socialProfileConverter;
	}

}
