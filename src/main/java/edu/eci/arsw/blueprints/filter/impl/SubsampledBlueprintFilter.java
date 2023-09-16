package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

import java.util.List;
@Component
@Qualifier("Subsampling")
public class SubsampledBlueprintFilter implements BlueprintFilter {
    @Override
    public Blueprint bluePrintFilter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredList = new ArrayList<>();



        // Recorremos la lista de puntos y conservamos los los datos que se encuentran en la posicion par.
        for (int i = 0; i < points.size(); i++) {
            if (i % 2 == 0){
                filteredList.add(points.get(i));
            }

        }

        blueprint.setPoints(filteredList);
        return blueprint;
    }

    @Override
    public Set<Blueprint> filter(Set<Blueprint> blueprints) {
        Set<Blueprint> filteredBlueprints = new HashSet<>();

        // Filtramos los planos aplicando el filtro a cada plano.
        for (Blueprint blueprint : blueprints) {
            filteredBlueprints.add(bluePrintFilter(blueprint));
        }

        return filteredBlueprints;
    }
}
