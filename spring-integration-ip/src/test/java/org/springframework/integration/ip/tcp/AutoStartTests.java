/*
 * Copyright 2002-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.ip.tcp;

import org.junit.jupiter.api.Test;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.util.TestingUtilities;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Gary Russell
 * @since 2.1
 *
 */
@SpringJUnitConfig
@DirtiesContext
public class AutoStartTests {

	@Autowired
	AbstractServerConnectionFactory cfS1;

	@Autowired
	TcpReceivingChannelAdapter tcpNetIn;

	@Test
	public void testNetIn() throws Exception {
		DirectFieldAccessor dfa = new DirectFieldAccessor(cfS1);
		assertThat(dfa.getPropertyValue("serverSocket")).isNull();
		startAndStop();
		assertThat(dfa.getPropertyValue("serverSocket")).isNull();
		startAndStop();
		assertThat(dfa.getPropertyValue("serverSocket")).isNull();
	}

	/**
	 * @throws InterruptedException
	 */
	private void startAndStop() throws InterruptedException {
		tcpNetIn.start();
		TestingUtilities.waitListening(cfS1, null);
		tcpNetIn.stop();
		TestingUtilities.waitStopListening(cfS1, null);
	}

}
