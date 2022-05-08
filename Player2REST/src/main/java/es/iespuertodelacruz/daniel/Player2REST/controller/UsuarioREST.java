package es.iespuertodelacruz.daniel.Player2REST.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.daniel.Player2REST.dto.MensajeDTO;
import es.iespuertodelacruz.daniel.Player2REST.dto.UsuarioDTO;
import es.iespuertodelacruz.daniel.Player2REST.entity.Mensaje;
import es.iespuertodelacruz.daniel.Player2REST.entity.Usuario;
import es.iespuertodelacruz.daniel.Player2REST.service.MensajeService;
import es.iespuertodelacruz.daniel.Player2REST.service.UsuarioService;

@RestController
@RequestMapping("/api/v0/usuario")
public class UsuarioREST {
	// private Logger logger = (Logger) LoggerFactory.logger(getClass());
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<?> getAll() {
		List<Usuario> l = (List<Usuario>) usuarioService.findAll();
		List<UsuarioDTO> listaVid = new ArrayList<>();
		for (Usuario usuario : l) {
			listaVid.add(new UsuarioDTO(usuario));
		}
		return new ResponseEntity<>(listaVid, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUsuarioById(@PathVariable("id") Integer id) {

		Optional<Usuario> optUsuario = usuarioService.findById(id);
		if (optUsuario.isPresent()) {
			return ResponseEntity.ok(new UsuarioDTO(optUsuario.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuarioDto) {
		Usuario usuario = new Usuario();
		usuario.setNombre(usuarioDto.getNombre());
		usuario.setPassword(BCrypt.hashpw(usuarioDto.getPassword(), BCrypt.gensalt(10)));
		usuario.setRol("ROLE_USER");
		usuario.setActivo((byte) 1);
		Usuario usuarioC = null;
		try {
			usuarioC = usuarioService.save(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuarioC != null) {
			return new ResponseEntity<>(usuarioC, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Ya existe ese nick.", HttpStatus.CONFLICT);
		}

	}
	
	
	
}