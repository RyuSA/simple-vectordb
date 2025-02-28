package org.acme.vector.model;

import java.util.Arrays;
import java.util.Objects;

public class Vector {
    private double[] embedding;

    public Vector() {
    }

    public Vector(double[] embedding) {
        this.embedding = embedding;
    }

    public double[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(double[] embedding) {
        this.embedding = embedding;
    }

    public int getDimension() {
        return embedding != null ? embedding.length : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Arrays.equals(embedding, vector.embedding);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(embedding);
    }
}