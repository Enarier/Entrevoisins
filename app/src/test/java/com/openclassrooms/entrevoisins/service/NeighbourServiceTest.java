package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addNeighbourInFavoriteWithSuccess() {
        Neighbour neighbourToAdd = service.getNeighbours().get(0);
        assertFalse(service.doesExistFavorite(neighbourToAdd));
        service.addFavorite(neighbourToAdd);
        assertTrue(service.doesExistFavorite(neighbourToAdd));
    }

    @Test
    public void deleteNeighbourInFavoriteWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(1);
        service.addFavorite(neighbour);
        assertTrue(service.doesExistFavorite(neighbour));
        service.deleteFavorite(neighbour);
        assertFalse(service.doesExistFavorite(neighbour));
    }

    @Test
    public void verifyFavoriteList() {
        Neighbour neighbour = service.getNeighbours().get(0);
        Neighbour neighbour2 = service.getNeighbours().get(1);

        service.getNeighbourFavorite().clear();
        service.addFavorite(neighbour);
        service.addFavorite(neighbour2);
        assertEquals(2, service.getNeighbourFavorite().size());
    }


}
