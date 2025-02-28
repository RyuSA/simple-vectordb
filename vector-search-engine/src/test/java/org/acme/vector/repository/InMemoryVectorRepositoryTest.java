package org.acme.vector.repository;

import org.acme.vector.model.Vector;
import org.acme.vector.model.VectorSearchResult;
import org.acme.vector.model.VectorSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryVectorRepositoryTest {
    
    private InMemoryVectorRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new InMemoryVectorRepository();
    }
    
    @Test
    void testEmptyRepository() {
        assertTrue(repository.isEmpty());
        assertEquals(0, repository.getDimension());
        
        VectorSummary summary = repository.getSummary();
        assertEquals(0, summary.getDimension());
        assertEquals(0, summary.getTotalVectors());
    }
    
    @Test
    void testAddVector() {
        Vector vector = new Vector(new double[]{0.1, 0.2, 0.3});
        repository.addVector(vector);
        
        assertFalse(repository.isEmpty());
        assertEquals(3, repository.getDimension());
        
        VectorSummary summary = repository.getSummary();
        assertEquals(3, summary.getDimension());
        assertEquals(1, summary.getTotalVectors());
    }
    
    @Test
    void testAddVectors() {
        List<Vector> vectors = Arrays.asList(
                new Vector(new double[]{0.1, 0.2, 0.3}),
                new Vector(new double[]{0.4, 0.5, 0.6})
        );
        
        repository.addVectors(vectors);
        
        assertFalse(repository.isEmpty());
        assertEquals(3, repository.getDimension());
        
        VectorSummary summary = repository.getSummary();
        assertEquals(3, summary.getDimension());
        assertEquals(2, summary.getTotalVectors());
    }
    
    @Test
    void testSearch() {
        // Add some vectors
        repository.addVector(new Vector(new double[]{1.0, 0.0, 0.0}));
        repository.addVector(new Vector(new double[]{0.0, 1.0, 0.0}));
        repository.addVector(new Vector(new double[]{0.0, 0.0, 1.0}));
        
        // Search for a vector similar to the first one
        Vector queryVector = new Vector(new double[]{0.9, 0.1, 0.0});
        List<VectorSearchResult> results = repository.search(queryVector, 2, 0.5);
        
        assertTrue(results.size() >= 1);
        if (results.size() > 1) {
            assertTrue(results.get(0).getScore() >= results.get(1).getScore()); // Results should be sorted
        }
        
        // The first vector should be the most similar
        assertArrayEquals(new double[]{1.0, 0.0, 0.0}, results.get(0).getEmbedding(), 0.001);
    }
    
    @Test
    void testSearchWithThreshold() {
        // Add some vectors
        repository.addVector(new Vector(new double[]{1.0, 0.0, 0.0}));
        repository.addVector(new Vector(new double[]{0.0, 1.0, 0.0}));
        repository.addVector(new Vector(new double[]{0.0, 0.0, 1.0}));
        
        // Search with a high threshold
        Vector queryVector = new Vector(new double[]{0.9, 0.1, 0.0});
        List<VectorSearchResult> results = repository.search(queryVector, 10, 0.9);
        
        // Only the first vector should be above the threshold
        assertEquals(1, results.size());
        assertArrayEquals(new double[]{1.0, 0.0, 0.0}, results.get(0).getEmbedding(), 0.001);
    }
    
    @Test
    void testSearchWithTopK() {
        // Add some vectors
        repository.addVector(new Vector(new double[]{1.0, 0.0, 0.0}));
        repository.addVector(new Vector(new double[]{0.9, 0.1, 0.0}));
        repository.addVector(new Vector(new double[]{0.8, 0.2, 0.0}));
        repository.addVector(new Vector(new double[]{0.7, 0.3, 0.0}));
        repository.addVector(new Vector(new double[]{0.6, 0.4, 0.0}));
        
        // Search with topK = 3
        Vector queryVector = new Vector(new double[]{1.0, 0.0, 0.0});
        List<VectorSearchResult> results = repository.search(queryVector, 3, 0.0);
        
        // Only the top 3 vectors should be returned
        assertEquals(3, results.size());
    }
    
    @Test
    void testSearchEmptyRepository() {
        Vector queryVector = new Vector(new double[]{1.0, 0.0, 0.0});
        List<VectorSearchResult> results = repository.search(queryVector, 10, 0.0);
        
        assertTrue(results.isEmpty());
    }
}