/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    Point[] pts;
    Blueprint bp;
    Point[] pts2;
    Blueprint bp2;
    BlueprintsServices bps;

    @Before
    public void initialConfiguration() throws BlueprintPersistenceException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        bps = ac.getBean(BlueprintsServices.class);
        // Data test
        pts = new Point[]{new Point(138, 174),new Point(259, 100)};
        bp = new Blueprint("Jordy", "Santiago", pts);
        bps.addNewBlueprint(bp);
        pts2 = new Point[]{new Point(427, 843),new Point(612, 729)};
        bp2 = new Blueprint("Jordy", "Ximena", pts2);
        bps.addNewBlueprint(bp2);
    }

    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ignored){
            
        }
                
        
    }

    @Test
    public void checkBlueprintsByAuthorTest() throws BlueprintNotFoundException {
        assertTrue(bps.getBlueprintsByAuthor("Jordy").contains(bp2));
        assertTrue(bps.getBlueprintsByAuthor("Jordy").contains(bp));
    }

    @Test
    public void noCheckBlueprintsByAuthorTest() throws BlueprintNotFoundException {
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        assertFalse(bps.getBlueprintsByAuthor("Jordy").contains(bp0));
    }

    @Test
    public void getBlueprintTest() throws BlueprintNotFoundException {
        assertEquals(bps.getBlueprint("Jordy", "Santiago"), bp);
        assertEquals(bps.getBlueprint("Jordy", "Ximena"), bp2);
    }

    @Test
    public void noGetBlueprintTest() throws BlueprintNotFoundException {
        assertNotEquals(bps.getBlueprint("Jordy", "Santiago"), bp2);
    }
}
