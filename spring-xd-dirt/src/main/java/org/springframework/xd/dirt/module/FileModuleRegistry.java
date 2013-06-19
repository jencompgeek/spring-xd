/*
 * Copyright 2013 the original author or authors.
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

package org.springframework.xd.dirt.module;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.xd.module.JarModule;
import org.springframework.xd.module.Module;
import org.springframework.xd.module.SimpleModule;

/**
 * @author Mark Fisher
 */
public class FileModuleRegistry implements ModuleRegistry {

	private final File directory;

	public FileModuleRegistry(String directory) {
		File f = new File(directory);
		Assert.isTrue(f.isDirectory(), "not a directory: " + f.getAbsolutePath());
		this.directory = f;
	}

	@Override
	public Module lookup(String name, String type) {
		 if(new File(directory, type + File.separator + name + ".xml").exists()) {
			 return fileModule(name, type);
		 }
		 return jarModule(name,type);
	}

	protected Module fileModule(String name, String type) {
		SimpleModule module = new SimpleModule(name, type);
		module.addComponents(loadResource(name, type));
		return module;
	}

	protected Module jarModule(String name, String type) {
		JarModule module = new JarModule(name, type, new File(directory, type + File.separator + name + ".jar"));
		return module;
	}

	private Resource loadResource(String name, String type) {
		File file = new File(directory, type + File.separator + name + ".xml");
		return new FileSystemResource(file);
	}

}
