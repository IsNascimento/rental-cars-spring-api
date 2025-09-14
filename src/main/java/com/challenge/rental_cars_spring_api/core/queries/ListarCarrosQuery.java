package com.challenge.rental_cars_spring_api.core.queries; 
import java.util.List; 
import org.springframework.stereotype.Service; 
import com.challenge.rental_cars_spring_api.core.domain.Carro; 
import com.challenge.rental_cars_spring_api.infrastructure.repositories.CarroRepository; 
import lombok.RequiredArgsConstructor; 

@Service 
@RequiredArgsConstructor 
public class ListarCarrosQuery { 
	private final CarroRepository carroRepository; 
	public List<Carro> execute() { 
		return carroRepository.findAll(); } 
	}
