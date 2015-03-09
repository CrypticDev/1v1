/**
 * 
 */
package cryptic.network.module;

/**
 * @author 598Johnn897
 *
 */
public @interface ModuleInfo {

	String id();
	
	String name();
	
	String version();
	
	String author() default "";
	
	String[] authors() default "";
	
	String description() default "";
	
	String[] depend() default "";
	
}
