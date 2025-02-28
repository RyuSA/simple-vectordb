package org.acme.vector.util;

public class VectorUtil {
    
    /**
     * Calculate the cosine similarity between two vectors
     * @param v1 First vector
     * @param v2 Second vector
     * @return The cosine similarity between the two vectors
     * @throws IllegalArgumentException if the vectors have different dimensions
     */
    public static double cosineSimilarity(double[] v1, double[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (int i = 0; i < v1.length; i++) {
            dotProduct += v1[i] * v2[i];
            norm1 += Math.pow(v1[i], 2);
            norm2 += Math.pow(v2[i], 2);
        }
        
        // Avoid division by zero
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
    
    /**
     * Validate that all vectors in a list have the same dimension
     * @param vectors The list of vector arrays to validate
     * @return true if all vectors have the same dimension, false otherwise
     */
    public static boolean validateVectorDimensions(Iterable<double[]> vectors) {
        Integer dimension = null;
        
        for (double[] vector : vectors) {
            if (dimension == null) {
                dimension = vector.length;
            } else if (dimension != vector.length) {
                return false;
            }
        }
        
        return true;
    }
}