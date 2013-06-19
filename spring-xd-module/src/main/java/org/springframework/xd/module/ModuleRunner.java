package org.springframework.xd.module;

import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

public class ModuleRunner implements Runnable {

	private static final String RUNNER_CLASS_NAME = "org.springframework.xd.module.ModuleApplicationRunner";

	private String[] args;

	private Object[] sources;
	
	private ApplicationContext parent;
	
	public ModuleRunner(ApplicationContext parent, Object[] sources, String[] args) {
		this.parent = parent;
		this.sources = sources;
		this.args = args;
	    this.parent = parent;
	}
	
	@Override
	public void run() {
		try {
			Class<?> runClass = Thread.currentThread().getContextClassLoader()
					.loadClass(RUNNER_CLASS_NAME);
			Method runMethod = runClass.getDeclaredMethod("run", Object[].class, String[].class, ApplicationContext.class);
			if (runMethod == null) {
				throw new IllegalStateException(RUNNER_CLASS_NAME
						+ " does not have arun method");
			}
			runMethod.invoke(null, new Object[] { sources, args, parent });
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}
