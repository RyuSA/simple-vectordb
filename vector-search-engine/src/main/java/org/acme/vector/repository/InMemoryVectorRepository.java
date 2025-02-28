package org.acme.vector.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.vector.model.Vector;
import org.acme.vector.model.VectorSearchResult;
import org.acme.vector.model.VectorSummary;
import org.acme.vector.util.VectorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@ApplicationScoped
public class InMemoryVectorRepository implements VectorRepository {
    
    private final List<Vector> vectors = new CopyOnWriteArrayList<>();
    private int dimension = 0;
    
    @Override
    public void addVector(Vector vector) {
        if (isEmpty()) {
            dimension = vector.getDimension();
        }
        vectors.add(vector);
    }
    
    @Override
    public void addVectors(List<Vector> newVectors) {
        if (!newVectors.isEmpty() && isEmpty()) {
            dimension = newVectors.get(0).getDimension();
        }
        vectors.addAll(newVectors);
    }
    
    @Override
    public List<VectorSearchResult> search(Vector queryVector, int topK, double threshold) {
        if (isEmpty()) {
            return Collections.emptyList();
        }
        
        List<VectorSearchResult> results = new ArrayList<>();
        
        for (Vector vector : vectors) {
            double similarity = VectorUtil.cosineSimilarity(queryVector.getEmbedding(), vector.getEmbedding());
            if (similarity >= threshold) {
                results.add(new VectorSearchResult(vector.getEmbedding(), similarity));
            }
        }
        
        // Sort by similarity score in descending order
        results.sort(Comparator.comparingDouble(VectorSearchResult::getScore).reversed());
        
        // Limit to topK results
        if (results.size() > topK) {
            results = results.subList(0, topK);
        }
        
        return results;
    }
    
    @Override
    public VectorSummary getSummary() {
        return new VectorSummary(dimension, vectors.size());
    }
    
    @Override
    public boolean isEmpty() {
        return vectors.isEmpty();
    }
    
    @Override
    public int getDimension() {
        return dimension;
    }
}