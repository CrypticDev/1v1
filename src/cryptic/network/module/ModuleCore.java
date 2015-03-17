/**
 * 
 */
package cryptic.network.module;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import cryptic.network.CrypticMain;
import cryptic.network.lib.References;
import cryptic.network.util.ClassEnumerator;

/**
 * @author 598Johnn897
 *
 */
public class ModuleCore
{

	List<Module> modules = new ArrayList<Module>();

	List<Module> loaded_modules = new ArrayList<Module>();

	List<Module> enabled_modules = new ArrayList<Module>();

	HashMap<String, Module> byId = new HashMap<String, Module>();
	HashMap<String, ModuleInfo> infoById = new HashMap<String, ModuleInfo>();

	public ModuleCore()
	{
		if (!new File(References.MODULE_DIRECTORY).exists()) new File(
				References.MODULE_DIRECTORY).mkdir();
	}

	public void init() throws ModuleException
	{
		try
		{
			modules = this.getModuleClasses(new File(
					References.MODULE_DIRECTORY));
		}
		catch (ModuleException e)
		{
			e.printStackTrace();
		}

		for (Module module : modules)
		{
			byId.put(module.getInfo().id(), module);
		}

		load();

		enable();
	}

	public void load() throws ModuleException
	{
		for (Module module : modules)
		{
			loadModule(module);
		}
	}

	public void enable() throws ModuleException
	{
		for (Module module : loaded_modules)
		{
			enableModule(module);
		}
	}

	public void disable() throws ModuleException
	{
		for (Module module : enabled_modules)
		{
			disableModule(module);
		}
	}

	public void loadModule(Module module) throws ModuleException
	{
		try
		{
			module.load();
		}
		catch (Exception e)
		{
			throw new ModuleException(
					"An Error has occurred while loading module: "
							+ module.getInfo().name());
		}
		finally
		{
			CrypticMain.get().clogger.log(Level.INFO,
					"Loaded module {0} v{1} id{2}!", new Object[]
					{ module.getInfo().name(), module.getInfo().version(),
							module.getInfo().id() });
		}
	}

	public void enableModule(Module module) throws ModuleException
	{
		try
		{
			module.load();
		}
		catch (Exception e)
		{
			throw new ModuleException(
					"An Error has occurred while enabling module: "
							+ module.getInfo().name());
		}
		finally
		{
			CrypticMain.get().clogger.log(Level.INFO,
					"Loaded module {0} v{1} id{2}!", new Object[]
					{ module.getInfo().name(), module.getInfo().version(),
							module.getInfo().id() });
		}
	}

	public void disableModule(Module module) throws ModuleException
	{
		try
		{
			module.load();
		}
		catch (Exception e)
		{
			throw new ModuleException(
					"An Error has occurred while disabling module: "
							+ module.getInfo().name());
		}
		finally
		{
			CrypticMain.get().clogger.log(Level.INFO,
					"Loaded module {0} v{1} id{2}!", new Object[]
					{ module.getInfo().name(), module.getInfo().version(),
							module.getInfo().id() });
		}
	}

	private List<Module> getModuleClasses(File file) throws ModuleException
	{
		List<Module> modules = new ArrayList<Module>();

		List<Class<?>> classes = ClassEnumerator.getInstance()
				.getClassesFromLocation(file);

		if (classes == null || classes.size() == 0) throw new ModuleException(
				"No modules found!");
		else CrypticMain.get().clogger.log(Level.INFO,
				"Found {0} files... searching for modules...", classes.size());

		for (Class<?> c : classes)
		{
			if (Module.class.isAssignableFrom(c) && !c.isInterface()
					&& !c.isEnum() && !c.isAnnotation())
			{
				if (c.getAnnotation(ModuleInfo.class) != null)
				{
					try
					{
						modules.add((Module) c.newInstance());
					}
					catch (InstantiationException | IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					throw new ModuleException(
							"Module class found but does not have ModuleInfo annotation!: "
									+ c.getPackage());
				}
			}
		}

		CrypticMain.get().clogger.log(Level.INFO, "Loaded {0} modules!",
				modules.size());

		return modules;
	}

}
