package org.acme.vector;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VectorResourceTest {
    
    @Test
    @Order(1)
    void testGetSummaryEmptyRepository() {
        given()
            .when().get("/vector")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("summary.dimension", is(0))
                .body("summary.totalVectors", is(0));
    }
    
    @Test
    @Order(2)
    void testAddVectors() {
        List<double[]> embeddings = new ArrayList<>();
        embeddings.add(new double[]{0.1, 0.2, 0.3});
        embeddings.add(new double[]{0.4, 0.5, 0.6});
        
        Map<String, Object> request = new HashMap<>();
        request.put("embedding", embeddings);
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/vector")
            .then()
                .statusCode(200);
    }
    
    @Test
    @Order(3)
    void testGetSummaryAfterAddingVectors() {
        given()
            .when().get("/vector")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("summary.dimension", is(3))
                .body("summary.totalVectors", is(2));
    }
    
    @Test
    @Order(4)
    void testAddVectorsWithDifferentDimension() {
        List<double[]> embeddings = new ArrayList<>();
        embeddings.add(new double[]{0.1, 0.2, 0.3, 0.4}); // 4 dimensions instead of 3
        
        Map<String, Object> request = new HashMap<>();
        request.put("embedding", embeddings);
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/vector")
            .then()
                .statusCode(400);
    }
    
    @Test
    @Order(5)
    void testSearchVectors() {
        Map<String, Object> request = new HashMap<>();
        request.put("embedding", new double[]{0.1, 0.2, 0.3});
        request.put("topK", 5);
        request.put("threshold", 0.5);
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/vector/search")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("result.size()", is(2));
    }
    
    @Test
    @Order(6)
    void testSearchVectorsWithInvalidTopK() {
        Map<String, Object> request = new HashMap<>();
        request.put("embedding", new double[]{0.1, 0.2, 0.3});
        request.put("topK", 0); // Invalid: must be positive
        request.put("threshold", 0.5);
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/vector/search")
            .then()
                .statusCode(400);
    }
    
    @Test
    @Order(7)
    void testSearchVectorsWithInvalidThreshold() {
        Map<String, Object> request = new HashMap<>();
        request.put("embedding", new double[]{0.1, 0.2, 0.3});
        request.put("topK", 5);
        request.put("threshold", 1.5); // Invalid: must be between 0 and 1
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/vector/search")
            .then()
                .statusCode(400);
    }
    
    @Test
    @Order(8)
    void testSearchVectorsWithDifferentDimension() {
        Map<String, Object> request = new HashMap<>();
        request.put("embedding", new double[]{0.1, 0.2, 0.3, 0.4}); // 4 dimensions instead of 3
        request.put("topK", 5);
        request.put("threshold", 0.5);
        
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post("/vector/search")
            .then()
                .statusCode(400);
    }
}