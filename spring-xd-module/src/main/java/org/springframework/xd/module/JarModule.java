package org.springframework.xd.module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.bootstrap.launcher.JarLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public class JarModule extends AbstractModule {
	
	private File jarFile;
	
	private Properties properties = new Properties();
	
	private ApplicationContext parent;
	
	private List<Resource> sources = new ArrayList<Resource>();

	public JarModule(String name, String type, File jarFile) {
		super(name, type);
		this.jarFile = jarFile;
	}

	@Override
	public void start() {
		JarLauncher launcher = new ModuleJarLauncher(jarFile, getName(), parent, sources);
		// TODO test using a PPC in a module
		launcher.launch(convertProperties());
	}

	@Override
	public void stop() {
		// TODO extend JarLauncher with ThreadPool to kill module thread
		
	}
	
	private String[] convertProperties() {
		List<String> convertedProps = new ArrayList<String>();
		for(Object key: properties.keySet()) {
			convertedProps.add("--" + key + "=" + properties.getProperty((String)key));
		}
		return convertedProps.toArray(new String[convertedProps.size()]);
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public void setParentContext(ApplicationContext parentContext) {
		this.parent = parentContext;
		
	}

	@Override
	public void addComponents(Resource resource) {
		sources.add(resource);
	}

	@Override
	public void addProperties(Properties properties) {
		this.properties.putAll(properties);
		
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

}
