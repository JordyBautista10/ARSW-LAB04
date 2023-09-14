package edu.eci.arsw.blueprints.test.persistence.impl;
import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.filter.impl.SubsampledBlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
public class SubsamplingFilterTest {

    private InMemoryBlueprintPersistence ibpp;
    private BlueprintFilter filter;

    @Before
    public void setUp() throws Exception {
        ibpp = new InMemoryBlueprintPersistence();
        filter = new SubsampledBlueprintFilter();
    }


    @Test
    public void shouldFilterMultipleBlueprintsBySubsampling() {
        try {
            Point[] pts = new Point[]{new Point(1, 1), new Point(1, 2), new Point(0, 4), new Point(0, 5), new Point(12, 101), new Point(0, 4)};
            Blueprint bp1 = new Blueprint("Mariana", "thepaint", pts);
            Blueprint bp2 = new Blueprint("Mariana", "thepaint2", pts);

            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);

            Set<Blueprint> blueprints = filter. filter(ibpp.getBlueprintsByAuthor("Mariana"));
            blueprints.forEach(blueprint -> {
                assertEquals(blueprint.getPoints().size(), 3);
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Fallo que no deberia de activarse");
        }
    }
}
