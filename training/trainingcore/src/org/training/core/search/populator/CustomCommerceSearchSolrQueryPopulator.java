/**
 *
 */
package org.training.core.search.populator;

import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SearchQueryPageableData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchRequest;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchSolrQueryPopulator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.solrfacetsearch.config.FacetSearchConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author nitingakhar
 *
 */
public class CustomCommerceSearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		extends SearchSolrQueryPopulator<INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
{

	private static final Logger LOG = Logger.getLogger(CustomCommerceSearchSolrQueryPopulator.class);
	private static final String SONY_EMAIL_DOMAIN = "@sony.com";
	private static final String CANON_EMAIL_DOMAIN = "@canon.com";
	private static final String SOLR_MANUFACTURE_NAME_PARAM = "manufacturerName_text";

	@Autowired
	private UserService userService;

	@Override
	public void populate(final SearchQueryPageableData<SolrSearchQueryData> source,
			final SolrSearchRequest<FacetSearchConfig, IndexedType, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE> target)
	{
		super.populate(source, target);
		final UserModel currentUserModel = userService.getCurrentUser();
		if (currentUserModel != null && !userService.isAnonymousUser(currentUserModel))
		{
			final CustomerModel customerModel = (CustomerModel) currentUserModel;
			if (customerModel.getContactEmail().endsWith(SONY_EMAIL_DOMAIN))
			{
				target.getSearchQuery().addFilterQuery(SOLR_MANUFACTURE_NAME_PARAM, "Sony");
				LOG.info("Modified the Solr Search Query to fetch only Sony Brand Products");
			}

			if (customerModel.getContactEmail().endsWith(CANON_EMAIL_DOMAIN))
			{
				target.getSearchQuery().addFilterQuery(SOLR_MANUFACTURE_NAME_PARAM, "Canon");
				LOG.info("Modified the Solr Search Query to fetch only Canon Brand Products");

			}

		}


	}

}
