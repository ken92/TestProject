package edu.unh.cs.cs619_2014_project2.g1.game;


/**
 * Created by Eridan on 11/19/2014.
 */
public class GridParser {
    private int width, height;
    private EntityHandler entityHandler;
    private GridHandler gridHandler;

    public GridParser(int width, int height, GridHandler gridHandler, EntityHandler entityHandler) {
        this.width = width;
        this.height = height;
        this.gridHandler=gridHandler;
        this.entityHandler=entityHandler;
    }

    public void parseGrid(int grid[][]) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //  [0]     Empty field entity
                //  [1000]  Indestructible wall
                //  [1000 - 2000]   Destructible wall. HP = number - 1000
                //  [2,000,000 - 3,000,000] Bullet. If value is 2ABCDEF, then the ID of the tank who fired the bullet is ABC and the damage of the bullet is DEF
                //  [10,000,000 - 20,000,000]   Tank. If the value is 1ABCDEFG, then the ID of the tank is ABC, it has DEF life and its direction is G.
                //                                 (E.G., value = 12220071, tankId=222, life=007, direction=2) Directions: UP=0, RIGHT=2, DOWN=4, LEFT=6
                if (grid[i][j] == 1000) {
                    WallLogic wall = entityHandler.indestructibleWallAt(i,j);
                    if(wall!=null)
                        gridHandler.placeEntity(i,j,wall);
                    //gridString[i][j] = "I";
                }
                else if (grid[i][j] > 1000 && grid[i][j] < 2000) {
                    //gridString[i][j] = "W";
                    WallLogic wall = entityHandler.normalWallAt(i,j,(grid[i][j])-1000);
                    if(wall!=null)
                        gridHandler.placeEntity(i,j,wall);
                }
                else if (grid[i][j] > 2000000 && grid[i][j] < 3000000) {
                    //gridString[i][j] = "B";
                }
                else if (grid[i][j] > 10000000 && grid[i][j] < 20000000) {
                    //gridString[i][j] = "T";
                    String tankString = Integer.toString(grid[i][j]);
                    String tankID=tankString.substring(1,4);
                    int tankHealth=Integer.parseInt(tankString.substring(4,7));
                    int tankDirection=Integer.parseInt(tankString.substring(7,8));
                    TankLogic tank = entityHandler.tankAt(i,j,tankID,tankHealth,tankDirection);
                    if(tank!=null)
                        gridHandler.placeEntity(i,j,tank);
                }
                //count++;
            }
        }
    }
}
