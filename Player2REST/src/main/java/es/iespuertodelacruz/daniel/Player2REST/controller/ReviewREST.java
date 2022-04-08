package es.iespuertodelacruz.daniel.Player2REST.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.daniel.Player2REST.dto.ReviewDTO;
import es.iespuertodelacruz.daniel.Player2REST.entity.Review;
import es.iespuertodelacruz.daniel.Player2REST.entity.Usuario;
import es.iespuertodelacruz.daniel.Player2REST.entity.Videojuego;
import es.iespuertodelacruz.daniel.Player2REST.service.ReviewService;
import es.iespuertodelacruz.daniel.Player2REST.service.UsuarioService;
import es.iespuertodelacruz.daniel.Player2REST.service.VideojuegoService;

@RestController
@RequestMapping("/api/v0/review")
public class ReviewREST {
	// private Logger logger = (Logger) LoggerFactory.logger(getClass());
	@Autowired
	ReviewService reviewService;
	@Autowired
	VideojuegoService videojuegoService;
	@Autowired
	UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Review> l = (List<Review>) reviewService.findAll();
		List<ReviewDTO> listaVid = new ArrayList<>();
		for (Review review : l) {
			listaVid.add(new ReviewDTO(review));
		}
		return new ResponseEntity<>(listaVid, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getReviewById(@PathVariable("id") Integer id) {
		
		Optional<Review> optReview = reviewService.findById(id);
		if (optReview.isPresent()) {
			return ResponseEntity.ok(new ReviewDTO(optReview.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}