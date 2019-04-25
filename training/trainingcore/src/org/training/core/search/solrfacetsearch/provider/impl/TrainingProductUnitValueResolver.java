package org.training.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * ValueResolver for Unit Name
 *
 * @author nitingakhar
 *
 */
public class TrainingProductUnitValueResolver extends AbstractValueResolver<ProductModel, Object, Object>
{
	@Autowired
	private FieldNameProvider fieldNameProvider;
	@Autowired
	private CommonI18NService commonI18NService;

	@Override
	protected void addFieldValues(final InputDocument inputDocument, final IndexerBatchContext indexerBatchContext,
			final IndexedProperty indexedProperty, final ProductModel productModel,
			final ValueResolverContext<Object, Object> valueResolverContext) throws FieldValueProviderException
	{

		final String unitName = getUnitName(productModel);

		if (unitName != null)
		{
			final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
			for (final String fieldName : fieldNames)
			{
				inputDocument.addField(fieldName, unitName);
			}
		}

	}

	private String getUnitName(final ProductModel product)
	{
		return product.getUnit().getName();
	}
}
