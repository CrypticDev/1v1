/**
 * 
 */
package cryptic.network.cmdframework.cmds;

import cryptic.network.CrypticMain;
import cryptic.network.cmdframework.Command;
import cryptic.network.cmdframework.CommandArgs;
import cryptic.network.cmdframework.CommandListener;

/**
 * @author 598Johnn897
 *
 */
public class LennyFace implements CommandListener
{

	@Command(
			name = "lenny",
				description = "( ͡° ͜ʖ ͡°)",
				usage = "( ͡° ͜ʖ ͡°)",
				aliases =
				{ "( ͡° ͜ʖ ͡°)", "lennyface", "hue", "creepy", })
	public void lenny(final CommandArgs info)
	{
		info.getSender().sendMessage("( ͡° ͜ʖ ͡°)");
		CrypticMain.get().getServer().getScheduler()
				.scheduleSyncDelayedTask(CrypticMain.get(), new Runnable()
				{
					public void run()
					{
						info.getSender().sendMessage("( ͡° ͜ʖ ͡°)");
					}
				}, 120L);
	}
}
