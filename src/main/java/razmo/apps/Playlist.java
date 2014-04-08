package razmo.apps;

import java.util.Collection;
import java.util.HashMap;

public class Playlist
{
    private int current = -1;
    
    private HashMap<Integer, Medium> media = new HashMap<Integer, Medium>();
    
    public Playlist()
    {
    }

    public Collection<Medium> getMedia()
    {
        return media.values();
    }
    
    public void addMedium(Medium medium)
    {
        media.put(medium.getId(), medium);
    }
    
    public void addMedium(Integer id, String name, String path)
    {
        media.put(id, new Medium(id, name, path));
    }
    
    public void removeMedium(Integer id)
    {
        media.remove(id);
    }
    
    public int previous()
    {
        if (count() > 0)
        {
            if (current != -1)
            {
                current--;
                
                if (current < 0)
                {
                    current = count()-1;
                }
            }
        }
        
        return current;
    }
    
    public int next()
    {
        if (count() > 0)
        {
            if (current != -1)
            {
                current++;
                
                if (current >= count())
                {
                    current = 0;
                }                               
            }
        }         
        
        return current;
    }
    
    public int getCurrent()
    {
        return current;
    }

    public void setCurrent(Integer id)
    {
        current = id;
    }
    
    public int count()
    {
        return media.size();
    }
    
    public Medium getMedium(int id)
    {
        return media.get(id);
    }
    
    public boolean hasMedium(int id)
    {
        return media.containsKey(id);
    }
    
    public boolean hasMedium(String path)
    {
        boolean hasMedium = false;
        
        for (Medium medium : media.values())
        {
            if (medium.getPath().equals(path))
            {
                hasMedium = true;
                break;
            }
        }
        
        return hasMedium;
    }
}
