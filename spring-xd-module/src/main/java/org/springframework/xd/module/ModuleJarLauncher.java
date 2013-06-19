package org.springframework.xd.module;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import org.springframework.bootstrap.launcher.JarLauncher;
import org.springframework.bootstrap.launcher.jar.RandomAccessJarFile;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ModuleJarLauncher extends JarLauncher {
	
	private static final String RUNNER_CLASS = ModuleJarLauncher.class.getPackage().getName()
			+ ".ModuleRunner";
	
	private File jarFile;
	
	private List<Resource> sources;
	
	private String name;
	
	private ApplicationContext parent;

	public ModuleJarLauncher(File jarFile, String name, ApplicationContext parent,  List<Resource> sources) {
		super();
		this.jarFile = jarFile;
		this.name=name;
		this.parent = parent;
		this.sources = sources;
	}
	

	@Override
	protected String getMainClass(RandomAccessJarFile jarFile) throws Exception {
		return ModuleApplicationRunner.class.getName();
	}
	
	@Override
	protected ClassLoader createClassLoader(URL[] urls) throws Exception {
		return new URLClassLoader(urls, getClass().getClassLoader());
	}

	@Override
	public void launch(String[] args) {
		try {
			launch(args, jarFile);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	protected Runnable createMainMethodRunner(String mainClass, String[] args,
			ClassLoader classLoader) throws Exception {
		Class<?> runnerClass = classLoader.loadClass(RUNNER_CLASS);
		Constructor<?> constructor = runnerClass.getConstructor(ApplicationContext.class, Object[].class, String[].class);
		// TODO search path more relative than META-INF/spring
		// Have to create the ClassPathResource for the file here in order to use the right classloader
		sources.add(new ClassPathResource("/META-INF/spring/" + name + ".xml", classLoader));
		return (Runnable) constructor.newInstance(parent, sources.toArray(), args);
	}

}
