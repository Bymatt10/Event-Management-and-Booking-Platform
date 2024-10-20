package com.example.backendeventmanagementbooking.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.example.backendeventmanagementbooking.utils.GenericResponse.GenerateResponse;

@Data
@Builder
@AllArgsConstructor
@Component
public class PaginationGeneric {
    public static ResponseEntity<GenericResponse<PaginationDto>> pageableRepository(Object repositoryValueObject) {

        try {
            final var pageTuts = (Page<?>) repositoryValueObject;

            final var paginationDto = new PaginationDto(pageTuts.getContent(),
                    pageTuts.getNumber(),
                    pageTuts.getTotalPages(),
                    pageTuts.getTotalElements());

            return GenerateResponse(HttpStatus.OK, paginationDto);

        } catch (Exception e) {
            return GenerateResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Data
    @AllArgsConstructor
    public static class PaginationDto {
        private Object value;
        private Integer currentPage;
        private Integer totalPages;
        private Long totalItems;
    }

}
