/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.apikit.validation;

import org.mule.api.MuleContext;

import org.mule.module.apikit.SchemaType;

public final class RestSchemaValidatorFactory
{

    private static RestSchemaValidatorFactory INSTANCE;

    static
    {
        INSTANCE = new RestSchemaValidatorFactory();
    }

    private RestSchemaValidatorFactory()
    {
    }

    public static RestSchemaValidatorFactory getInstance()
    {
        return INSTANCE;
    }

    public RestSchemaValidator createValidator(SchemaType schemaType, MuleContext muleContext)
    {
        if (schemaType == SchemaType.JSONSchema)
        {
            return new RestJsonSchemaValidator(muleContext);
        }
        else if (schemaType == SchemaType.XMLSchema)
        {
            return new RestXmlSchemaValidator(muleContext);
        }

        throw new IllegalArgumentException("Invalid schema type: " + schemaType);
    }
}
