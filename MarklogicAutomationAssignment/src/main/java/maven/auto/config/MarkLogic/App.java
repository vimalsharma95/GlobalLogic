package maven.auto.config.MarkLogic;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	byte a=10;
    	byte b=10;
    	byte c;
    	c=10+10;
    	
    	String path  = System.getProperty("user.dir")+"/src/test/java/Utilities/shakespeare.2.00.xml";
        
    	String pa=path.replace('\\','/');
    	System.out.println(pa);
    }
}
