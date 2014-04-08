package razmo.apps;

import java.io.File;

public class Medium
{
    protected Integer id = null;

    protected String name = null;
    
    protected String path = null;
    
    public Medium()
    {        
    }
    
    public Medium(Integer id, String name, String path)
    {
        this.name = name;
        this.path = path;                        
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }
    
    public File getFile()
    {
        return new File(path);
    }

}
