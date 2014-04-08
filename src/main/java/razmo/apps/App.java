package razmo.apps;

/**
 * Player
 *
 */
public class App 
{
    public static void main( String[] args )
    {        
        try
        {
            PlayerWindow playerWindow = new PlayerWindow();
            
            playerWindow.open();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        } 
    }
}