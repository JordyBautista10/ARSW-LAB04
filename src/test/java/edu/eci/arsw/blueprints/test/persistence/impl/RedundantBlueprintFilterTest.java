package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import java.util.Set;
import static org.junit.Assert.assertEquals;



@Component
public class RedundantBlueprintFilterTest {
    public BlueprintsServices services;

    @Before
    public void setUp() throws Exception {
        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        services =ac.getBean(BlueprintsServices.class);
    }

    @Test
    public void shouldFilterRedundantBlueprint() {

        Point[] pts = new Point[]{new Point(2, 2), new Point(2, 2), new Point(20, 20), new Point(20, 20)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            services.addNewBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }

        // Obtenemos el plano filtrado.
        Blueprint filteredBlueprint = null;
        try {
            filteredBlueprint = services.getBlueprint("john", "thepaint");
        } catch (BlueprintNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Verificamos que el plano filtrado tenga solo dos puntos.
        assertEquals(filteredBlueprint.getPoints().size(), 4);
    }


    @Test
    public void shouldFilterRedundantBlueprints() {

        Point[] pts = new Point[]{new Point(4, 4), new Point(4, 4), new Point(12, 12), new Point(12, 12), new Point(10, 101), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint", pts);
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts);

        try {
            services.addNewBlueprint(bp1);
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }
        try {
            services.addNewBlueprint(bp2);
        } catch (BlueprintPersistenceException e) {
            throw new RuntimeException(e);
        }

        // Obtenemos los planos filtrados.
        Set<Blueprint> filteredBlueprints = null;
        try {
            filteredBlueprints = services.getBlueprintsByAuthor("john");
        } catch (BlueprintNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Verificamos que todos los planos filtrados tengan solo seis puntos.
        for (Blueprint filteredBlueprint : filteredBlueprints) {
            assertEquals(filteredBlueprint.getPoints().size(), 6);
        }
    }


}