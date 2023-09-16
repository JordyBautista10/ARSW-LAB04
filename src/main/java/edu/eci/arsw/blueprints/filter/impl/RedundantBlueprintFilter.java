package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Qualifier("redundant")
public class RedundantBlueprintFilter implements BlueprintFilter {
    @Override
    public Blueprint bluePrintFilter(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredList = new ArrayList<>();
        // suprimimos los puntos consecutivos que se encuentren repetidos.
        for (int i = 0; i < points.size() ; i++) {
            if(i == points.size()-1){
                filteredList.add(points.get(i));
            }else {
                if (!points.get(i).equals(points.get(i + 1))) {
                    filteredList.add(points.get(i));
                }
            }
        }
        blueprint.setPoints(filteredList);
        return blueprint;
    }

    @Override
    public Set<Blueprint> filter(Set<Blueprint> blueprints) {
        // Creamos un nuevo conjunto para almacenar los planos filtrados.
        Set<Blueprint> filteredBlueprints = new HashSet<>();
        //Filtramos los planos que no cumplen con los criterios de filtrado.
        for (Blueprint blueprint : blueprints) {
            Blueprint filteredBlueprint = bluePrintFilter(blueprint);
            if (filteredBlueprint != null) {
                filteredBlueprints.add(filteredBlueprint);
            }
        }
        //Devolvemos el conjunto de planos filtrados.
        return filteredBlueprints;
    }


}
