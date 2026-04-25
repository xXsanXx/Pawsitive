package com.nastena.pawsitive.dto;

public class ShelterUpdateStatusRequest {

    private Long requestId;
    private AdoptionStatus status;

    public ShelterUpdateStatusRequest() {}

    public ShelterUpdateStatusRequest(Long requestId, AdoptionStatus status) {
        this.requestId = requestId;
        this.status = status;
    }

    public Long getRequestId() {
        return requestId;
    }

    public AdoptionStatus getStatus() {
        return status;
    }
}
