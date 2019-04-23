/**
 *
 */
package org.training.facades.populators;

import de.hybris.platform.commercefacades.user.data.SocialProfileData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import org.training.core.model.SocialProfileModel;


/**
 * @author nitingakhar
 *
 */
public class SocialProfilePopulator implements Populator<SocialProfileModel, SocialProfileData>
{

	@Override
	public void populate(final SocialProfileModel source, final SocialProfileData target) throws ConversionException
	{
		target.setFacebookprofile(source.getFacebookprofile());
		target.setTwitterprofile(source.getTwitterprofile());
		target.setInstagramprofile(source.getInstagramprofile());
	}

}
