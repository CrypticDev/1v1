/**
 * 
 */
package cryptic.network.module;

import java.util.ArrayList;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import cryptic.network.CrypticMain;
import cryptic.network.util.ClassEnumerator;

/**
 * @author 598Johnn897
 *
 */
public class Registry
{
	static ArrayList<Listener> listeners = new ArrayList<Listener>();
	static Listener addListener(Listener listener)
	{
		listeners.add(listener);
		return listener;
	}
	
	public static void registerModule(Module module) throws ModuleException
	{
		registerEvents(module);
		CrypticMain.get().cmdFramework.registerCommands(module);
	}

	public static void registerEvents(Object clazz)
	{
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(clazz);
		if (classes == null || classes.length == 0) return;
		for (Class<?> c : classes)
		{
			try
			{
				if (Listener.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation())
				{
					CrypticMain
							.get()
							.getServer()
							.getPluginManager()
							.registerEvents(addListener((Listener) c.newInstance()),
									CrypticMain.get());
				}
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void unregisterEvents(Object clazz)
	{
		/*
		Class<?>[] classes = ClassEnumerator.getInstance()
				.getClassesFromThisJar(clazz);
		if (classes == null || classes.length == 0) return;
		for (Class<?> c : classes)
		{
			try
			{
				if (Listener.class.isAssignableFrom(c) && !c.isInterface()
						&& !c.isEnum() && !c.isAnnotation())
				{
					HandlerList.unregisterAll((Listener) c.newInstance());
				}
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		*/
		
		for (Listener listener : listeners)
			HandlerList.unregisterAll(listener);
	}
}
