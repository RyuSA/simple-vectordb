package org.acme.vector.model;

public class VectorSummaryResponse {
    private VectorSummary summary;

    public VectorSummaryResponse() {
    }

    public VectorSummaryResponse(VectorSummary summary) {
        this.summary = summary;
    }

    public VectorSummary getSummary() {
        return summary;
    }

    public void setSummary(VectorSummary summary) {
        this.summary = summary;
    }
}