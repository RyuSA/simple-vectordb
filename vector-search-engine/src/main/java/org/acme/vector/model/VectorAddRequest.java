package org.acme.vector.model;

import java.util.List;

public class VectorAddRequest {
    private List<double[]> embedding;

    public VectorAddRequest() {
    }

    public VectorAddRequest(List<double[]> embedding) {
        this.embedding = embedding;
    }

    public List<double[]> getEmbedding() {
        return embedding;
    }

    public void setEmbedding(List<double[]> embedding) {
        this.embedding = embedding;
    }
}