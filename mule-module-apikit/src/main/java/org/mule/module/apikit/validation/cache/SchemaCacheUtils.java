/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.apikit.validation.cache;

import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Raml;

public class SchemaCacheUtils
{

    private static final String SEPARATOR = ",";

    public static String getSchemaCacheKey(Action action, String mimeTypeName)
    {
        StringBuilder key = new StringBuilder(action.getResource().getUri());
        key.append(SEPARATOR).append(action.getType());
        key.append(SEPARATOR).append(mimeTypeName);
        return key.toString();
    }

    public static String resolveSchema(String schemaCacheKey, Raml api)
    {
        String[] path = schemaCacheKey.split(SEPARATOR);
        Action action = api.getResource(path[0]).getAction(path[1]);
        MimeType mimeType = action.getBody().get(path[2]);
        String schema = mimeType.getSchema();
        //check global schemas
        if (api.getConsolidatedSchemas().containsKey(schema))
        {
            schema = api.getConsolidatedSchemas().get(schema);
        }
        return schema;
    }
}
