/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {

    @Autowired
    @Qualifier("InMemory")
    BlueprintsPersistence bpp;
    @Autowired
    @Qualifier("Subsampling")
    BlueprintFilter filter;
    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public Set<Blueprint> getAllBlueprints() throws BlueprintPersistenceException {
        return  bpp.getAllBlueprints();
    }

    /**
     *
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return bpp.getBlueprint(author,name);
    }

    /**
     *
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        return  bpp.getBlueprintsByAuthor(author);
    }

    public Set<Blueprint> getFilteredAllBlueprints() throws BlueprintPersistenceException {
        return  filter.filter(bpp.getAllBlueprints());
    }

    /**
     *
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the filtered blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getFilteredBlueprint(String author,String name) throws BlueprintNotFoundException {
        return filter.bluePrintFilter(getBlueprint(author, name));
    }

    public Set<Blueprint> getFilteredBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        return  filter.filter(bpp.getBlueprintsByAuthor(author));
    }
}
