/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.apikit.rest.validation;

import org.mule.api.MuleEvent;

import apikit2.exception.BadRequestException;
import org.raml.model.Raml;

public interface RestSchemaValidator
{
    @Deprecated //apikit1 only
    void validate(String schemaLocation, MuleEvent muleEvent) throws InvalidInputException;
    void validate(String schemaPath, MuleEvent muleEvent, Raml api) throws BadRequestException;
}