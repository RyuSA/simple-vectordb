package org.acme.vector.model;

import java.util.List;

public class VectorSearchResponse {
    private List<VectorSearchResult> result;

    public VectorSearchResponse() {
    }

    public VectorSearchResponse(List<VectorSearchResult> result) {
        this.result = result;
    }

    public List<VectorSearchResult> getResult() {
        return result;
    }

    public void setResult(List<VectorSearchResult> result) {
        this.result = result;
    }
}