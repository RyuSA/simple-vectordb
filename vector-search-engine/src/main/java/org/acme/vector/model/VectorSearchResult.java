package org.acme.vector.model;

public class VectorSearchResult {
    private double[] embedding;
    private double score;

    public VectorSearchResult() {
    }

    public VectorSearchResult(double[] embedding, double score) {
        this.embedding = embedding;
        this.score = score;
    }

    public double[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(double[] embedding) {
        this.embedding = embedding;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}