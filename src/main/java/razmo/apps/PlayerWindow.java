package razmo.apps;

import java.awt.Component;
import java.awt.Frame;
import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FillLayout;

public class PlayerWindow
{
    private Shell shell = null;
    private Display display = null;
    private Button btnPlay = null;
    private Button btnStop = null;
    private Button btnPrevious = null;
    private Button btnNext = null;
    private Button btnSwitch = null;

    private Table playlistView = null;
    private Frame screenFrame = null;

    private MenuItem mntmAddSongs = null;
    private MenuItem mntmQuit = null;                 
    
    private MenuItem mntmDelete = null;
    private MenuItem mntmPlay = null;
    
    private Composite main_composite = null;
    private Composite screen_composite = null;
    private Composite playlist_composite = null;
    private StackLayout main_composite_layout = null;
    private FormData fd_main_composite = null;
    private FormData fd_screen_composite = null; 
    private FormData fd_controle_composite = null;
        
    private boolean display_playlist = true;

    /**
     * Open the window.
     */
    public void open()
    {                
        display = Display.getDefault();

        createContents();
        createListeners();

        shell.open();
        shell.layout();

        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     * @wbp.parser.entryPoint
     */
    protected void createContents()
    {        
        shell = new Shell(display, SWT.SHELL_TRIM);
        shell.setMinimumSize(new Point(600, 400));
        shell.setSize(450, 300);
        shell.setText("Sound Player");
        shell.setLayout(new FormLayout());

        fd_main_composite = new FormData();
        fd_main_composite.bottom = new FormAttachment(0, 300);
        fd_main_composite.right = new FormAttachment(0, 600);
        fd_main_composite.top = new FormAttachment(0);
        fd_main_composite.left = new FormAttachment(0);
        
        main_composite = new Composite(shell, SWT.NONE);
        main_composite_layout = new StackLayout();
        main_composite.setLayout(main_composite_layout);
        main_composite.setLayoutData(fd_main_composite);
        
        playlist_composite = new Composite(main_composite, SWT.NONE);
        GridLayout gl_playlist_composite = new GridLayout(1, false);
        gl_playlist_composite.marginRight = 6;
        gl_playlist_composite.verticalSpacing = 0;
        gl_playlist_composite.marginWidth = 0;
        gl_playlist_composite.marginHeight = 0;
        gl_playlist_composite.horizontalSpacing = 0;
        playlist_composite.setLayout(gl_playlist_composite);
                
        playlistView = new Table(playlist_composite, SWT.BORDER | SWT.FULL_SELECTION);
        GridData gd_playlistView = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_playlistView.heightHint = 318;
        playlistView.setLayoutData(gd_playlistView);
        playlistView.setLinesVisible(true);
        
        
                
        TableColumn NameColumn = new TableColumn(playlistView, SWT.NONE);
        NameColumn.setResizable(false);
        NameColumn.setWidth(569);
        NameColumn.setText("Name");  
        
        Menu menu_3 = new Menu(playlistView);
        playlistView.setMenu(menu_3);

        mntmPlay = new MenuItem(menu_3, SWT.NONE);        
        mntmPlay.setText("Play");
        
        mntmDelete = new MenuItem(menu_3, SWT.NONE);
        mntmDelete.setText("Delete");
        
        Composite controle_composite = new Composite(shell, SWT.NONE);
        fd_controle_composite = new FormData();
        fd_controle_composite.bottom = new FormAttachment(0, 350);
        fd_controle_composite.right = new FormAttachment(0, 600);
        fd_controle_composite.top = new FormAttachment(0, 300);
        fd_controle_composite.left = new FormAttachment(0);
        controle_composite.setLayoutData(fd_controle_composite);
        controle_composite.setLayout(new GridLayout(6, false));
        switchToPlaylist();
        
        btnPlay = new Button(controle_composite, SWT.NONE);
        btnPlay.setEnabled(false);
        btnPlay.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/play.png"));

        btnStop = new Button(controle_composite, SWT.NONE);
        btnStop.setEnabled(false);
        btnStop.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/stop.png"));

        btnPrevious = new Button(controle_composite, SWT.NONE);
        btnPrevious.setEnabled(false);
        btnPrevious.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/skip_previous.png"));

        btnNext = new Button(controle_composite, SWT.NONE);
        btnNext.setEnabled(false);
        btnNext.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/skip_next.png"));

        btnSwitch = new Button(controle_composite, SWT.NONE);
        btnSwitch.setEnabled(false);
        btnSwitch.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/list_2.png"));
        btnSwitch.setVisible(true);
        
        Composite composite = new Composite(controle_composite, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        composite.setLayout(new FillLayout(SWT.HORIZONTAL));

        Menu menu = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menu);

        MenuItem mntmFile_1 = new MenuItem(menu, SWT.CASCADE);
        mntmFile_1.setText("File");

        Menu menu_2 = new Menu(mntmFile_1);
        mntmFile_1.setMenu(menu_2);

        mntmAddSongs = new MenuItem(menu_2, SWT.NONE);

        mntmAddSongs.setText("Add media");
        mntmAddSongs.setAccelerator(SWT.CTRL+'O');

        new MenuItem(menu_2, SWT.SEPARATOR);

        mntmQuit = new MenuItem(menu_2, SWT.NONE);
        mntmQuit.setText("Quit");
    }
    
    protected void resize()
    {
        Integer width = shell.getBounds().width;
        Integer height = shell.getBounds().height;

        fd_main_composite.bottom = new FormAttachment(0, height - 100);
        fd_main_composite.right = new FormAttachment(0, width);
        
        fd_controle_composite.top = new FormAttachment(0, height - 100);
        fd_controle_composite.right = new FormAttachment(0, width - 5);
        fd_controle_composite.bottom = new FormAttachment(0, height - 50);
    }
    
    protected void switchToScreen()
    {
        main_composite_layout.topControl = screen_composite;
        main_composite.layout();
        
        display_playlist = false;
    }
    
    protected void switchToPlaylist()
    {
        main_composite_layout.topControl = playlist_composite;
        main_composite.layout();
        
        display_playlist = true;
    }
    
    protected void createListeners()
    {
        shell.addControlListener(new ControlAdapter() {
            @Override
            public void controlResized(ControlEvent e) {
                resize();
            }
        });
        
        mntmPlay.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        
        mntmDelete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Integer id = playlistView.getSelectionIndex();
                
                MediaPlayer.getInstance().getPlaylist().removeMedium(id);
                playlistView.remove(id);
            }
        });
        
        btnSwitch.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                
                if (display_playlist)
                {
                    switchToScreen();                   
                }
                else
                {
                    switchToPlaylist();
                }                
            }
        });
        
        playlistView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                playlistPlay();
            }
        }); 

        btnPlay.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {                
                playlistPlay();
            }
        });

        btnNext.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                playlistViewNext();
            }
        });

        btnPrevious.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                playlistViewPrevious();
            }
        });

        btnStop.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                MediaPlayer.getInstance().stop();
                
                btnStop.setEnabled(false);
                btnPrevious.setEnabled(false);
                btnNext.setEnabled(false);
                btnPlay.setEnabled(false);
                btnSwitch.setEnabled(false);
                
                if (MediaPlayer.getInstance().getPlaylist().count() > 0)
                {
                    btnPlay.setEnabled(true);
                }
                
                switchToPlaylist();
                
                screenFrame.dispose();
                screen_composite.dispose();
                btnPlay.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/play.png"));
            }
        });

        shell.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent arg0) {
                MediaPlayer.getInstance().stop();
                screenFrame.dispose();
                screen_composite.dispose();
                
                try
                {
                    MediaPlayer.getInstance().finalize();
                } 
                catch (Throwable e)
                {
                }                            
            }
        });

        mntmQuit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });

        mntmAddSongs.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {

                FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);
                String extensions[] = {"*.avi;*.mp3"};
                fileDialog.setFilterExtensions(extensions);
                fileDialog.open();

                String files[] = fileDialog.getFileNames();
                String directory =  fileDialog.getFilterPath();

                for (String file : files)
                {
                    String path = directory+File.separator+file;

                    if (MediaPlayer.getInstance().getPlaylist().hasMedium(path) == false)
                    {   
                        TableItem item = new TableItem(playlistView, SWT.None);
                        String[] data = {file};
                        item.setText(data);

                        Integer id = playlistView.indexOf(item);

                        MediaPlayer.getInstance().getPlaylist().addMedium(id, file, path);    
                    }                    
                }   

                if (MediaPlayer.getInstance().getPlaylist().count() > 0)
                {
                    btnPlay.setEnabled(true);
                }
            }
        });
    }
    
    public void playlistPlay()
    {
        if (playlistView.getItemCount() != 0)
        {
            Integer index = playlistView.getSelectionIndex();

            if (index == -1)
            {                        
                index = 0;            
                playlistView.select(index);
            }

            if (MediaPlayer.getInstance().getPlaylist().hasMedium(index))
            {
                if (index == MediaPlayer.getInstance().getPlaylist().getCurrent() && MediaPlayer.getInstance().hasPlayerInstance())
                {
                    if (MediaPlayer.getInstance().isPause())
                    {
                        MediaPlayer.getInstance().play();
                        btnPlay.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/pause.png"));
                    }
                    else
                    {                        
                        MediaPlayer.getInstance().pause();
                        btnPlay.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/play.png"));
                    }
                }
                else
                { 
                    MediaPlayer.getInstance().stop();
                    
                    if (MediaPlayer.getInstance().initPlayer(index))   
                    {
                        Component media_visual = MediaPlayer.getInstance().getVisual();
                      
                        if (media_visual != null)
                        {  
                            screen_composite = new Composite(main_composite, SWT.BACKGROUND | SWT.EMBEDDED);
                            fd_screen_composite = new FormData();
                            fd_screen_composite.bottom = new FormAttachment(0, 300);
                            fd_screen_composite.left = new FormAttachment(0);
                            fd_screen_composite.top = new FormAttachment(0);
                            fd_screen_composite.right = new FormAttachment(0, 600);
                            screen_composite.setLayoutData(fd_screen_composite);
                        
                            screenFrame = SWT_AWT.new_Frame(screen_composite);
                            screenFrame.add(media_visual);
                          
                            switchToScreen();
                            btnSwitch.setEnabled(true);
                        }
                        else
                        {
                            btnSwitch.setEnabled(false);
                        }
  
                        MediaPlayer.getInstance().play();
                        btnPlay.setImage(SWTResourceManager.getImage("/home/jamal/Development/java/JMediaPlayer/images/pause.png"));
                        
                        btnStop.setEnabled(true);
                        btnPrevious.setEnabled(true);
                        btnNext.setEnabled(true);
                    }
                    else
                    {
                        MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR);
                        messageBox.setText("Error");
                        messageBox.setMessage("Can't read this file");
                        messageBox.open();
                    }
                }
            }
        }
    }

    public void playlistViewNext()
    {
        int index = MediaPlayer.getInstance().getPlaylist().next();

        if (index != -1)
        {
            playlistView.setSelection(index);
        }     
    }

    public void playlistViewPrevious()
    {
        int index = MediaPlayer.getInstance().getPlaylist().previous();

        if (index != -1)
        {
            playlistView.setSelection(index);
        }        
    }   
}
