package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;

import java.util.Set;

public interface BlueprintFilter {

    Blueprint bluePrintFilter(Blueprint blueprint);

    Set<Blueprint> filter(Set<Blueprint> blueprints);


}