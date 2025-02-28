package org.acme.vector;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.vector.model.VectorAddRequest;
import org.acme.vector.model.VectorSearchRequest;
import org.acme.vector.model.VectorSearchResponse;
import org.acme.vector.model.VectorSummaryResponse;
import org.acme.vector.service.VectorService;

@Path("/vector")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VectorResource {

    @Inject
    VectorService vectorService;

    @GET
    public VectorSummaryResponse getSummary() {
        return vectorService.getSummary();
    }

    @POST
    public Response addVectors(VectorAddRequest request) {
        try {
            vectorService.addVectors(request);
            return Response.ok().build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/search")
    public Response search(VectorSearchRequest request) {
        try {
            VectorSearchResponse response = vectorService.search(request);
            return Response.ok(response).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
