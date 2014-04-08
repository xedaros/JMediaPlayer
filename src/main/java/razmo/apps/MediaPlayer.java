package razmo.apps;

import javax.media.Manager;
import javax.media.PlugInManager;
import javax.media.Format;
import javax.media.Time;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.Player;
import java.awt.Component;

public class MediaPlayer
{
    protected static MediaPlayer INSTANCE = null;
    
    protected Player player = null;
    
    protected Playlist playlist = null;
    
    protected Boolean pause = false;
    
    protected Time pauseTime = null;
    
    private MediaPlayer()
    {
        playlist = new Playlist();
    }
    
    public static MediaPlayer getInstance()
    { 
        if (INSTANCE == null)
        {   
            synchronized(MediaPlayer.class)
            {
                if (INSTANCE == null)
                { 
                    INSTANCE = new MediaPlayer();
                }
            }
      }
        
      return INSTANCE;
    }
    
    protected void finalize() throws Throwable
    {
        stop();
        
        super.finalize();        
    } 
        
    public boolean initPlayer(Integer id)
    {
        if (playlist.hasMedium(id))
        {
            Medium medium = playlist.getMedium(id);
            MediaPlayer.getInstance().getPlaylist().setCurrent(id);
            
            try
            {
                addCodecs();
                
                Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, new Boolean(true));
                player = Manager.createRealizedPlayer(medium.getFile().toURI().toURL());
                
                return true;
            } 
            catch (Exception e)
            {   
                if (player != null)
                    player.close();
                
                player = null;
                return false;
            }          
        }
        else
        {
            return false;
        }
    }
    
    
    public Component getVisual()
    {
        return  player.getVisualComponent();
    }
    
    public void play()
    {
        if (player != null)
        {
            if (pause)
                player.setMediaTime(pauseTime);
            else
                player.setMediaTime(new Time(0)); 
            
            player.start();
            pause = false;
        }
    } 
    
    public void pause()
    {
        if (player != null)
        {
            pauseTime = player.getMediaTime();
            player.stop();
            pause = true;
        }
    }
    
    public void stop()
    {
        if (player != null)
        {
            player.stop();
            player.close();
            
            pause = false;
            pauseTime = null;
            player = null;
        }
    }
    
    public Time getDuration()
    {          
        Time duration = player.getDuration();
        
        if (duration == Player.DURATION_UNKNOWN)
        {
            duration = new Time(0);
        }
        
        return duration;
    }
    
    public Time getMediaTime()
    {
        Time mediaTime = (player != null) ? player.getMediaTime() : new Time(0);
        
        if (mediaTime == null)
        {
            mediaTime = new Time(0);
        }
        
        return mediaTime;
    }
    
    public boolean isPause()
    {
        return (pause && (player != null));
    }
    
    public boolean hasPlayerInstance()
    {
        return (player != null);
    }
    
    public Playlist getPlaylist()
    {
        return playlist;
    }
    
    public void addCodecs()
    {
        Format[] AudioFormats = {
                        new AudioFormat(AudioFormat.MPEGLAYER3)
        };

        Format[] VideoFormats = {
                        new VideoFormat("XVID"),
                        new VideoFormat("MPEG")
        };

        Format[] OutputFormats = {
                        new AudioFormat(AudioFormat.LINEAR)
        };

        PlugInManager.addPlugIn("net.sourceforge.jffmpeg.VideoDecoder", VideoFormats, OutputFormats, PlugInManager.CODEC);
        PlugInManager.addPlugIn("net.sourceforge.jffmpeg.AudioDecoder", AudioFormats, OutputFormats, PlugInManager.CODEC);
    }
}














