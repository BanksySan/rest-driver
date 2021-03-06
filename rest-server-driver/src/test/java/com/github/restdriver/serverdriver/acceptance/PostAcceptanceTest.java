/**
 * Copyright © 2010-2011 Nokia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.restdriver.serverdriver.acceptance;

import static com.github.restdriver.serverdriver.Matchers.*;
import static com.github.restdriver.serverdriver.RestServerDriver.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.restdriver.clientdriver.ClientDriverRequest;
import com.github.restdriver.clientdriver.ClientDriverResponse;
import com.github.restdriver.clientdriver.ClientDriverRule;
import com.github.restdriver.serverdriver.http.response.Response;

/**
 * User: mjg
 * Date: 21/04/11
 * Time: 13:52
 */
public class PostAcceptanceTest {
    
    @Rule
    public ClientDriverRule driver = new ClientDriverRule();
    
    private String baseUrl;
    
    @Before
    public void getServerDetails() {
        baseUrl = driver.getBaseUrl();
    }
    
    @Test
    public void postEmptyBody() {
        driver.addExpectation(
                new ClientDriverRequest("/").withMethod(ClientDriverRequest.Method.POST),
                new ClientDriverResponse("Content"));
        
        Response response = post(baseUrl);
        
        assertThat(response, hasStatusCode(200));
        assertThat(response.getContent(), is("Content"));
    }
    
    @Test
    public void postWithTextPlainBody() {
        driver.addExpectation(
                new ClientDriverRequest("/").withMethod(ClientDriverRequest.Method.POST).withBody("Your body", "text/plain"),
                new ClientDriverResponse("Back at you").withStatus(202));
        
        Response response = post(baseUrl, body("Your body", "text/plain"));
        
        assertThat(response, hasStatusCode(202));
        assertThat(response.getContent(), is("Back at you"));
    }
    
    @Test
    public void postWithApplicationXmlBody() {
        driver.addExpectation(
                new ClientDriverRequest("/")
                        .withMethod(ClientDriverRequest.Method.POST)
                        .withBody("<yo/>", "application/xml"),
                new ClientDriverResponse("Back at you").withStatus(202));
        
        Response response = post(baseUrl, body("<yo/>", "application/xml"));
        
        assertThat(response, hasStatusCode(202));
        assertThat(response.getContent(), is("Back at you"));
    }
    
    @Test
    public void postWithApplicationJsonBodyAndHeaders() {
        driver.addExpectation(
                new ClientDriverRequest("/jsons")
                        .withMethod(ClientDriverRequest.Method.POST)
                        .withBody("<yo/>", "application/xml")
                        .withHeader("Accept", "Nothing"),
                new ClientDriverResponse("Back at you").withStatus(202));
        
        Response response = post(baseUrl + "/jsons", body("<yo/>", "application/xml"), header("Accept", "Nothing"));
        
        assertThat(response, hasStatusCode(202));
        assertThat(response.getContent(), is("Back at you"));
    }
    
    @Test
    public void postWithDuplicateBodyUsesLastOne() {
        driver.addExpectation(
                new ClientDriverRequest("/xml")
                        .withMethod(ClientDriverRequest.Method.POST)
                        .withBody("<yo/>", "application/xml"),
                new ClientDriverResponse("Back at you").withStatus(202));
        
        Response response = post(baseUrl + "/xml", body("{}", "application/json"), body("<yo/>", "application/xml"));
        
        assertThat(response, hasStatusCode(202));
        assertThat(response.getContent(), is("Back at you"));
    }
    
}
