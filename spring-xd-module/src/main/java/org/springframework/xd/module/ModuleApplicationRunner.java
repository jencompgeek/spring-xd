package org.springframework.xd.module;

import org.springframework.context.ApplicationContext;

public class ModuleApplicationRunner {

	public static void run(Object[] sources, String[] args, ApplicationContext parent) {
		ModuleSpringApplication application = new ModuleSpringApplication(parent, sources);
		application.setWebEnvironment(false);
		application.setShowBanner(false);
		// Args are converted Properties, could do that here too
		application.run(args);
	}
}
