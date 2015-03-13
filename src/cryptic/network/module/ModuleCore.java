/**
 * 
 */
package cryptic.network.module;

import java.util.List;
import java.util.logging.Level;
import java.io.File;

import cryptic.network.CrypticMain;
import cryptic.network.lib.References;
import cryptic.network.util.ClassEnumerator;

/**
 * @author 598Johnn897
 *
 */
public class ModuleCore {

	List<Module> modules = null;
	
	public ModuleCore() {

	}

	public void init() {
		if (!new File(References.MODULE_DIRECTORY).exists())
			new File(References.MODULE_DIRECTORY).mkdir();
		
		try {
			modules = this.getModuleClasses(new File(References.MODULE_DIRECTORY));
		} catch (ModuleException e) {
			e.printStackTrace();
		}
	}

	public void enable() {

	}

	public void disable() {

	}

	public void loadModule(Module module) {

	}

	public void enableModule(Module module) {

	}

	public void disableModule(Module module) {

	}
	
	private List<Module> getModuleClasses(File file) throws ModuleException {
		List<Class<?>> classes = ClassEnumerator.getInstance().getClassesFromLocation(file);
		CrypticMain.get().clogger.log(Level.INFO, "Files: {0}", classes.size());
		
		if (classes == null || classes.size() == 0) 
			throw new ModuleException("No modules found!");
		else 
			CrypticMain.get().clogger.log(Level.INFO, "Found {0} files... searching for modules...", classes.size());
		for (Class<?> c : classes) {
			if (Module.class.isAssignableFrom(c) && !c.isInterface()
					&& !c.isEnum() && !c.isAnnotation()) {
				if (c.getAnnotation(ModuleInfo.class) != null) {
					
				} else {
					throw new ModuleException("Module class found but does not have ModuleInfo annotation!: " + c.getPackage());
				}
			}
		}
		throw new ModuleException("No modules found!");
	}

}
