package com.challenge.rental_cars_spring_api.access;

import com.challenge.rental_cars_spring_api.core.queries.ListarCarrosQuery;
import com.challenge.rental_cars_spring_api.core.queries.dtos.ListarCarrosQueryResultItem;
import com.challenge.rental_cars_spring_api.infrastructure.openapi.CarrosRestControllerOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carros")
public class CarrosRestController implements CarrosRestControllerOpenApi {

    private final ListarCarrosQuery listarCarrosQuery;

    @Override
    @GetMapping
    public ResponseEntity<List<ListarCarrosQueryResultItem>> listarCarros() {
        List<ListarCarrosQueryResultItem> result = listarCarrosQuery.execute();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
