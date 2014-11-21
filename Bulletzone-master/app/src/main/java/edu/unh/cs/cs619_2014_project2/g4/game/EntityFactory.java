package edu.unh.cs.cs619_2014_project2.g4.game;

/**
 * Created by Tay on 11/8/2014.
 */


public class EntityFactory {
    int grid[][];
    long tankid;

    public EntityFactory(int grid[][], long tankid){
    this.grid = grid;
        this.tankid = tankid;

    }

    public Entity[] createEntities() {

       Entity[] entity = new Entity[257];

int count = 0;
        for (int x = 0; x < 16; x++){
            for(int y = 0; y < 16; y++){
                if(grid[x][y] == 1000){
                    entity[count] = new IWall(x,y);
                }else if(grid[x][y] > 1000 && grid[x][y] < 2000){
                    entity[count] = new Wall(x,y, grid[x][y]-1000);
                }else if(grid[x][y] > 2000000 && grid[x][y] < 3000000){
                    entity[count] = new Bullet(x,y,30, (int) tankid);
                }else if(grid[x][y] > 10000000 && grid[x][y] < 20000000) {
                    entity[count] = new Tank(x, y, 120, (int) tankid, 2);
                } else if(grid[x][y] == 0){
                        entity[count] = new Blank(x,y);
                }
                else
                    entity[count] = new Blank(x,y);


count++;
            }
        }

        return entity;
    }

    @Override
    public String toString()
    {
        return "EF";
    }
}