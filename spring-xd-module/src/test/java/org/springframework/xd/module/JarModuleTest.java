package org.springframework.xd.module;

import java.io.File;

import org.junit.Test;

public class JarModuleTest {

	@Test
	public void Deployment() throws Exception {
		Module module = new JarModule("feed", "source", new File(
				"/Users/pivotal/Desktop/modules/source/feed.jar"));
		module.start();
		// While this is running you will start seeing
		// "Dispatcher has no subscribers" as we are putting feeds on the output
		// channel
		Thread.sleep(1000000);
	}

}
