/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.module.apikit;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.port;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mule.module.apikit.Configuration.APPLICATION_RAML;

import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;

import com.jayway.restassured.RestAssured;

import org.junit.Rule;
import org.junit.Test;

public class StandaloneConsoleTestCase extends FunctionalTestCase
{

    private static final String CONSOLE_PATH = "/konsole";

    @Rule
    public DynamicPort serverPort = new DynamicPort("serverPort");

    @Override
    public int getTestTimeoutSecs()
    {
        return 6000;
    }

    @Override
    protected void doSetUp() throws Exception
    {
        RestAssured.port = serverPort.getNumber();
        super.doSetUp();
    }

    @Override
    protected String getConfigResources()
    {
        return "org/mule/module/apikit/console/standalone-console-config.xml";
    }

    @Test
    public void console() throws Exception
    {
        given().header("Accept", "text/html")
                .expect()
                .response().body(allOf(containsString("<title>api:Console</title>"),
                                       containsString("src=\"http://localhost:" + port + CONSOLE_PATH + "/\"")))
                .header("Content-type", "text/html").statusCode(200)
                .when().get(CONSOLE_PATH + "/index.html");
    }

    @Test
    public void consoleResource() throws Exception
    {
        given().header("Accept", "text/css")
                .expect()
                .response().body(containsString("#raml-console-unembedded"))
                .header("Content-type", "text/css").statusCode(200)
                .when().get(CONSOLE_PATH + "/styles/app.css");
    }

    @Test
    public void getRaml() throws Exception
    {
        given().header("Accept", APPLICATION_RAML)
                .expect()
                .response().body(containsString("baseUri: \"http://localhost:" + port + "/api\""))
                .header("Content-type", APPLICATION_RAML).statusCode(200)
                .when().get("/api");
    }

}
