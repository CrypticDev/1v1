/**
 * 
 */
package cryptic.network;

import static cryptic.network.lib.References.CRYPTIC_JSON;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONException;
import org.json.JSONObject;

import cryptic.network.cmdframework.CommandFramework;
import cryptic.network.lib.References;
import cryptic.network.module.ModuleCore;
import cryptic.network.module.Registry;
import cryptic.network.util.JsonCache;

/**
 * @author 598Johnn897
 *
 */
public class CrypticMain extends JavaPlugin
{
	private static CrypticMain instance;
	public static CrypticMain get()
	{
		Validate.notNull(instance);
		return instance;
	}
	
	public final CrypticLogger clogger = new CrypticLogger(this);
	public CommandFramework cmdFramework;
	
	@Override public void onLoad()
	{
		instance = this;
		if (!this.getDataFolder().exists())
		{
			File dir = this.getDataFolder();
			dir.mkdir();
		}
		
		try {
			CRYPTIC_JSON = new JsonCache(new File(CrypticMain.get().getDataFolder().getAbsolutePath() + References.CRYPTIC_JSON_FILE), new URL(References.CRYPTIC_JSON_URL), 15).getJson();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			try {
				CRYPTIC_JSON = new JSONObject().put("error", e.toString());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			References.MODULE_DIRECTORY = References.WORKING_DIRECTORY + CRYPTIC_JSON.getString("module_directory");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		try {
			clogger.log(Level.INFO, CRYPTIC_JSON.getJSONObject("messages").getString("loading"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override public void onEnable()
	{
		try {
			clogger.log(Level.INFO, CRYPTIC_JSON.getJSONObject("messages").getString("enabled"));
			
			cmdFramework = new CommandFramework(get());
			cmdFramework.registerCommands(get());
			cmdFramework.registerHelp();
			
			Registry.registerEvents(get());
			
			new ModuleCore().init();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override public void onDisable()
	{
		try {
			clogger.log(Level.INFO, CRYPTIC_JSON.getJSONObject("messages").getString("disabled"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
