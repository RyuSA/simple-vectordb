package org.acme.vector.repository;

import org.acme.vector.model.Vector;
import org.acme.vector.model.VectorSearchResult;
import org.acme.vector.model.VectorSummary;

import java.util.List;

public interface VectorRepository {
    
    /**
     * Add a vector to the repository
     * @param vector The vector to add
     */
    void addVector(Vector vector);
    
    /**
     * Add multiple vectors to the repository
     * @param vectors The vectors to add
     */
    void addVectors(List<Vector> vectors);
    
    /**
     * Search for similar vectors
     * @param queryVector The vector to search for
     * @param topK The maximum number of results to return
     * @param threshold The minimum similarity score threshold
     * @return A list of search results
     */
    List<VectorSearchResult> search(Vector queryVector, int topK, double threshold);
    
    /**
     * Get a summary of the repository
     * @return A summary of the repository
     */
    VectorSummary getSummary();
    
    /**
     * Check if the repository is empty
     * @return true if the repository is empty, false otherwise
     */
    boolean isEmpty();
    
    /**
     * Get the dimension of vectors in the repository
     * @return The dimension of vectors in the repository, or 0 if the repository is empty
     */
    int getDimension();
}