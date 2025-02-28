package org.acme.vector.model;

public class VectorSearchRequest {
    private double[] embedding;
    private int topK;
    private double threshold;

    public VectorSearchRequest() {
    }

    public VectorSearchRequest(double[] embedding, int topK, double threshold) {
        this.embedding = embedding;
        this.topK = topK;
        this.threshold = threshold;
    }

    public double[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(double[] embedding) {
        this.embedding = embedding;
    }

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}