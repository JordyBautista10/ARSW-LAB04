package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw.blueprints"})
public class Main {

    public static void main(String[] args) throws BlueprintPersistenceException, BlueprintNotFoundException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        BlueprintsServices bps = ac.getBean(BlueprintsServices.class);

        Point[] pts0 = new Point[]{new Point(2, 2), new Point(2, 2), new Point(20, 20), new Point(2, 2)};
        Blueprint bp0 = new Blueprint("Juliana", "thepaint", pts0);
        bps.addNewBlueprint(bp0);
        Point[] pts = new Point[]{new Point(138, 174)};
        Blueprint bp = new Blueprint("Jordy", "Santiago", pts);
        bps.addNewBlueprint(bp);
        System.out.println(bps.getBlueprint("Jordy", "Santiago"));
        System.out.println(bps.getFilteredAllBlueprints());
    }

}