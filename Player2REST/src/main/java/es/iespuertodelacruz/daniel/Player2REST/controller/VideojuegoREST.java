package es.iespuertodelacruz.daniel.Player2REST.controller;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.daniel.Player2REST.dto.VideojuegoDTO;
import es.iespuertodelacruz.daniel.Player2REST.entity.Genero;
import es.iespuertodelacruz.daniel.Player2REST.entity.Videojuego;
import es.iespuertodelacruz.daniel.Player2REST.service.GeneroService;
import es.iespuertodelacruz.daniel.Player2REST.service.VideojuegoService;

@RestController
@RequestMapping("/api/v0/videojuego")
public class VideojuegoREST {
	// private Logger logger = (Logger) LoggerFactory.logger(getClass());
	@Autowired
	VideojuegoService videojuegoService;
	@Autowired
	GeneroService generoService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Videojuego> l = (List<Videojuego>) videojuegoService.findAll();
		List<VideojuegoDTO> listaVid = new ArrayList<>();
		for (Videojuego videojuego : l) {
			listaVid.add(new VideojuegoDTO(videojuego));
		}
		return new ResponseEntity<>(listaVid, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVideojuegoById(@PathVariable("id") Integer id) {

		Optional<Videojuego> optVideojuego = videojuegoService.findById(id);
		if (optVideojuego.isPresent()) {
			return ResponseEntity.ok(new VideojuegoDTO(optVideojuego.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
