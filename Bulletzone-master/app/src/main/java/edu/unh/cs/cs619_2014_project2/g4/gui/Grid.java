package edu.unh.cs.cs619_2014_project2.g4.gui;

import java.util.ArrayList;

import edu.unh.cs.cs619_2014_project2.g4.game.Entity;

/**
 * Created by Tay on 11/13/2014.
 */

public class Grid  {

    private ArrayList<Entity> m_entities;
        public Grid()
        {
            m_entities = new ArrayList<Entity>();
        }

        public ArrayList<Entity> getEntities()
        {
            return m_entities;
        }
}
