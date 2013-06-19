package org.springframework.xd.module;

import org.springframework.bootstrap.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class ModuleSpringApplication extends SpringApplication {
	
	private ApplicationContext parent;

	public ModuleSpringApplication(ApplicationContext parent, Object... sources) {
		super(sources);
		this.parent = parent;
	}


	protected void postProcessApplicationContext(ApplicationContext context) {
		((ConfigurableApplicationContext)context).setParent(parent);
		super.postProcessApplicationContext(context);
	}
}
