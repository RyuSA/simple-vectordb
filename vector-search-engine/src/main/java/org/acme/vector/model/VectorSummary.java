package org.acme.vector.model;

public class VectorSummary {
    private int dimension;
    private int totalVectors;

    public VectorSummary() {
    }

    public VectorSummary(int dimension, int totalVectors) {
        this.dimension = dimension;
        this.totalVectors = totalVectors;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getTotalVectors() {
        return totalVectors;
    }

    public void setTotalVectors(int totalVectors) {
        this.totalVectors = totalVectors;
    }
}