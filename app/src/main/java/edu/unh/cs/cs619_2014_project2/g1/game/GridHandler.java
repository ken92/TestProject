package edu.unh.cs.cs619_2014_project2.g1.game;

import edu.unh.cs.cs619_2014_project2.g1.R;
import edu.unh.cs.cs619_2014_project2.g1.ServerEventHandler;
import edu.unh.cs.cs619_2014_project2.g1.gui.CanvasGrid;
import edu.unh.cs.cs619_2014_project2.g1.gui.MapEntityGraphics;

/**
 * Created by Eridan on 11/14/2014.
 */
public class GridHandler {
    private static final String LOG_TAG="GridHandler";
    private static final int UP=R.integer.up;
    private static final int DOWN= R.integer.down;
    private static final int LEFT=R.integer.left;
    private static final int RIGHT=R.integer.right;

    private static CanvasGrid canvas=null;
    private EntityHandler entityHandler;
    private GridParser gridParser;
    private ShakeEventManager shakeEventManager;

    float x1, x2, y1, y2;

    public GridHandler(ServerEventHandler eventHandler) {
        if(canvas==null)
            canvas=new CanvasGrid(eventHandler);
        //setContentView(canvas);
        entityHandler=new EntityHandler(this,eventHandler);
        gridParser=new GridParser(R.integer.grid_width, R.integer.grid_height, this, entityHandler);

        /*shakeEventManager = new ShakeEventManager();
        shakeEventManager.setListener(this);
        shakeEventManager.init(this);*/
    }

    public void placeEntity(int x, int y, MapEntityGraphics entity) {
        canvas.putEntity(x, y, entity);
    }
    public void placeEntity(int x, int y, MapEntityLogic entity) {
        canvas.putEntity(x, y, entity.getGraphics());
    }

    public void moveEntityAt(int fromX, int fromY, int toX, int toY) {
        canvas.moveEntityAt(fromX, fromY, toX, toY);
    }
    public int getCanvasReadyState() {
        return canvas.getReadyState();
    }

    public CanvasGrid getCanvas() {return canvas;}


    protected void onPause() {
        canvas.pauseThread();
        //shakeEventManager.deregister();
        //BusProvider.getInstance().unregister(eventHandler);
        //Log.i(LOG_TAG, "onPause");
    }
    protected void onResume() {
        canvas.resumeThread();
        //shakeEventManager.register();
        //BusProvider.getInstance().register(eventHandler);
        //Log.i(LOG_TAG, "onResume");
    }

    public void updateGrid(int[][] grid){
        gridParser.parseGrid(grid);
    }

    public void setPlayerTankID(long id) {
        entityHandler.setPlayerTankID(id);
    }

}
