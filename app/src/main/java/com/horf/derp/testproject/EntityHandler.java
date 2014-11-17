package com.horf.derp.testproject;

/**
 * Created by Eridan on 11/14/2014.
 */
public class EntityHandler {
    private GridHandler gridHandler;
    private TankGraphics playerTankGraphics;
    //private TankLogic playerTankLogic;

    private static final int UP=R.integer.up;
    private static final int DOWN=R.integer.down;
    private static final int LEFT=R.integer.left;
    private static final int RIGHT=R.integer.right;

    public EntityHandler(GridHandler gridHandler) {
        this.gridHandler = gridHandler;
        createTestTank();
    }

    private void createTestTank() {
        playerTankGraphics = new TankGraphics(gridHandler, R.integer.player, 80, 80);
        gridHandler.placeEntity(3, 2, playerTankGraphics);
        playerTankGraphics.setCoordinates(3, 2);
        int ready=gridHandler.getCanvasReadyState();
        while(ready==0) {
            try{Thread.sleep(100);}
            catch(Exception e){}
            ready=gridHandler.getCanvasReadyState();
        }
    }

    public void turnPlayerTank(int direction) {
        playerTankGraphics.turn(direction);
    }
    public void movePlayerTank(int direction) {
        int tankDirection = playerTankGraphics.getDirection();
        int tankX=playerTankGraphics.getX();
        int tankY=playerTankGraphics.getY();
        if(direction==UP) {
            switch(tankDirection) {
                case UP:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY-1);
                    break;
                case DOWN:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY+1);
                    break;
                case LEFT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX-1, tankY);
                    break;
                case RIGHT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX+1, tankY);
                    break;
                default: return;
            }
        }
        else if(direction==DOWN) {
            switch(tankDirection) {
                case UP:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY+1);
                    break;
                case DOWN:
                    gridHandler.moveEntityAt(tankX, tankY, tankX, tankY-1);
                    break;
                case LEFT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX+1, tankY);
                    break;
                case RIGHT:
                    gridHandler.moveEntityAt(tankX, tankY, tankX-1, tankY);
                    break;
                default: return;
            }
        }
    }
}
