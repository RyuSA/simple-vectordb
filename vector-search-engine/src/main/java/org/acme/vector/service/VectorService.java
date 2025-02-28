package org.acme.vector.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.acme.vector.model.*;
import org.acme.vector.repository.VectorRepository;
import org.acme.vector.util.VectorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class VectorService {
    
    @Inject
    VectorRepository repository;
    
    public VectorSummaryResponse getSummary() {
        return new VectorSummaryResponse(repository.getSummary());
    }
    
    public void addVectors(VectorAddRequest request) {
        List<double[]> embeddings = request.getEmbedding();
        
        if (embeddings == null || embeddings.isEmpty()) {
            throw new BadRequestException("No vectors provided");
        }
        
        // Validate that all vectors have the same dimension
        if (!VectorUtil.validateVectorDimensions(embeddings)) {
            throw new BadRequestException("All vectors must have the same dimension");
        }
        
        // If repository is not empty, validate that the vectors have the same dimension as the repository
        if (!repository.isEmpty() && embeddings.get(0).length != repository.getDimension()) {
            throw new BadRequestException("Vector dimension must match repository dimension: " + repository.getDimension());
        }
        
        // Convert to Vector objects and add to repository
        List<Vector> vectors = embeddings.stream()
                .map(Vector::new)
                .collect(Collectors.toList());
        
        repository.addVectors(vectors);
    }
    
    public VectorSearchResponse search(VectorSearchRequest request) {
        double[] embedding = request.getEmbedding();
        int topK = request.getTopK();
        double threshold = request.getThreshold();
        
        // Validate request
        if (embedding == null || embedding.length == 0) {
            throw new BadRequestException("Query vector is required");
        }
        
        if (topK <= 0) {
            throw new BadRequestException("topK must be a positive integer");
        }
        
        if (threshold < 0 || threshold > 1) {
            throw new BadRequestException("threshold must be between 0 and 1");
        }
        
        // If repository is not empty, validate that the query vector has the same dimension as the repository
        if (!repository.isEmpty() && embedding.length != repository.getDimension()) {
            throw new BadRequestException("Query vector dimension must match repository dimension: " + repository.getDimension());
        }
        
        // Perform search
        List<VectorSearchResult> results = repository.search(new Vector(embedding), topK, threshold);
        
        return new VectorSearchResponse(results);
    }
}