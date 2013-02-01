/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.webservice.rest;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.webservice.AbstractWebService;
import org.mule.webservice.api.QueryParamInterfaceDefinitionFilter;
import org.mule.webservice.api.WebService;
import org.mule.webservice.api.WebServiceInterface;

public class RestWebService extends AbstractWebService implements WebService
{

    public RestWebService(String name, WebServiceInterface webServiceInterface, MuleContext muleContext)
    {
        super(name, webServiceInterface, muleContext);
    }

    @Override
    protected QueryParamInterfaceDefinitionFilter getInterfaceRepresentationFilter()
    {
        return new QueryParamInterfaceDefinitionFilter(initialState, this)
        {
            @Override
            public MuleEvent process(MuleEvent event) throws MuleException
            {
                return processNext(event);
            }
        };
    }

}