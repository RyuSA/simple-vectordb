package org.acme.vector.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VectorUtilTest {
    
    @Test
    void testCosineSimilarity() {
        double[] v1 = {1.0, 0.0, 0.0};
        double[] v2 = {1.0, 0.0, 0.0};
        
        // Same vectors should have similarity 1.0
        assertEquals(1.0, VectorUtil.cosineSimilarity(v1, v2), 0.0001);
        
        double[] v3 = {0.0, 1.0, 0.0};
        
        // Orthogonal vectors should have similarity 0.0
        assertEquals(0.0, VectorUtil.cosineSimilarity(v1, v3), 0.0001);
        
        double[] v4 = {0.5, 0.5, 0.0};
        
        // Vectors at 45 degrees should have similarity 0.7071
        assertEquals(0.7071, VectorUtil.cosineSimilarity(v1, v4), 0.0001);
    }
    
    @Test
    void testCosineSimilarityWithDifferentDimensions() {
        double[] v1 = {1.0, 0.0, 0.0};
        double[] v2 = {1.0, 0.0};
        
        // Vectors with different dimensions should throw an exception
        assertThrows(IllegalArgumentException.class, () -> VectorUtil.cosineSimilarity(v1, v2));
    }
    
    @Test
    void testCosineSimilarityWithZeroVector() {
        double[] v1 = {1.0, 0.0, 0.0};
        double[] v2 = {0.0, 0.0, 0.0};
        
        // Similarity with zero vector should be 0.0
        assertEquals(0.0, VectorUtil.cosineSimilarity(v1, v2), 0.0001);
    }
    
    @Test
    void testValidateVectorDimensions() {
        List<double[]> vectors = Arrays.asList(
                new double[]{1.0, 2.0, 3.0},
                new double[]{4.0, 5.0, 6.0},
                new double[]{7.0, 8.0, 9.0}
        );
        
        // All vectors have the same dimension
        assertTrue(VectorUtil.validateVectorDimensions(vectors));
        
        List<double[]> mixedVectors = Arrays.asList(
                new double[]{1.0, 2.0, 3.0},
                new double[]{4.0, 5.0},
                new double[]{7.0, 8.0, 9.0}
        );
        
        // Vectors have different dimensions
        assertFalse(VectorUtil.validateVectorDimensions(mixedVectors));
    }
    
    @Test
    void testValidateVectorDimensionsWithEmptyList() {
        List<double[]> emptyList = Arrays.asList();
        
        // Empty list should be valid
        assertTrue(VectorUtil.validateVectorDimensions(emptyList));
    }
}