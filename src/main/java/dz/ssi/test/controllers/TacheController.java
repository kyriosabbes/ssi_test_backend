package dz.ssi.test.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dz.ssi.test.models.EtatTache;
import dz.ssi.test.models.Tache;
import dz.ssi.test.repository.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TacheController {

	@Autowired
	TacheRepository tacheRepository;

	@GetMapping("/taches")
	public ResponseEntity<List<Tache>> getAllTaches(@RequestParam(required = false) String title) {
		try {
			List<Tache> taches = new ArrayList<Tache>();

			if (title == null)
				tacheRepository.findAll().forEach(taches::add);
			else
				tacheRepository.findByTitleContaining(title).forEach(taches::add);

			if (taches.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(taches, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/taches/{id}")
	public ResponseEntity<Tache> getTacheById(@PathVariable("id") long id) {
		Optional<Tache> tacheData = tacheRepository.findById(id);

		if (tacheData.isPresent()) {
			return new ResponseEntity<>(tacheData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/taches")
	public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
		try {
			Tache _tache = tacheRepository
					.save(new Tache(tache.getTitle(), tache.getDescription(), EtatTache.PROGRAMMEE));
			return new ResponseEntity<>(_tache, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/taches/{id}")
	public ResponseEntity<Tache> updateTache(@PathVariable("id") long id, @RequestBody Tache tache) {
		Optional<Tache> tacheData = tacheRepository.findById(id);

		if (tacheData.isPresent()) {
			Tache _tache = tacheData.get();
			_tache.setTitle(tache.getTitle());
			_tache.setDescription(tache.getDescription());
			_tache.setEtat(tache.getEtat());
			return new ResponseEntity<>(tacheRepository.save(_tache), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/taches/{id}")
	public ResponseEntity<HttpStatus> deleteTache(@PathVariable("id") long id) {
		try {
			tacheRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/taches")
	public ResponseEntity<HttpStatus> deleteAllTaches() {
		try {
			tacheRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/*@GetMapping("/taches/published")
	public ResponseEntity<List<Tache>> findByPublished() {
		try {
			List<Tache> taches = tacheRepository.findByPublished(true);

			if (taches.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(taches, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/

}
