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
package com.github.restdriver.clientdriver.clientdriver.unit;

import com.github.restdriver.clientdriver.ClientDriver;
import junit.framework.Assert;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.github.restdriver.clientdriver.ClientDriverFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class ClientDriverFactoryTest {

	@Test
	public void simpleTest() {
		// Hopefully no exceptions here
		assertThat(new ClientDriverFactory().createClientDriver(), instanceOf(ClientDriver.class));
	}
}
