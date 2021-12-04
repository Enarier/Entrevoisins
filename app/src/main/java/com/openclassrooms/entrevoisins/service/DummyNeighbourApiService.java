package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    private List<Neighbour> mNeighbourFavorite = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public List<Neighbour> getNeighbourFavorite() {
        return mNeighbourFavorite;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
        if (doesExistFavorite(neighbour)) {
            mNeighbourFavorite.remove(neighbour);
        }
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * Check if a neighbour exists in the neighboursFavorite List
     * Add and remove neighbour inside neighboursFavorite List
     */

    public boolean doesExistFavorite(Neighbour neighbour) {
        return mNeighbourFavorite.contains(neighbour);
    }

    @Override
    public void addFavorite(Neighbour neighbour) {
        mNeighbourFavorite.add(neighbour);
    }

    @Override
    public void deleteFavorite(Neighbour neighbour) {
        mNeighbourFavorite.remove(neighbour);
    }

}
