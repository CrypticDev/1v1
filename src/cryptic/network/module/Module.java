/**
 * 
 */
package cryptic.network.module;


/**
 * @author 598Johnn897
 *
 */
public abstract class Module  
{
	public Module()
	{
		
	}
	
	public ModuleInfo getInfo() throws ModuleException
	{
		if (this.getClass().getAnnotation(ModuleInfo.class) != null) {
			throw new ModuleException("ModuleInfo annotaion is null in class: " +
						this.getClass().getSimpleName() + "!");
		} else {
			return this.getClass().getAnnotation(ModuleInfo.class);
		}
	}
		
}
