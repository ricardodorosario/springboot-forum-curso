package com.pokolegas.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pokolegas.forum.converter.TopicoConverter;
import com.pokolegas.forum.dto.TopicoDetalheDto;
import com.pokolegas.forum.dto.TopicoDto;
import com.pokolegas.forum.dto.TopicoFormDto;
import com.pokolegas.forum.dto.TopicoFormUpdateDto;
import com.pokolegas.forum.model.Topico;
import com.pokolegas.forum.repository.TopicoRepository;

/*
 * Com essa anotação, não precisa marcar o @ResponseBody nos métodos.
 * */
@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private TopicoConverter topicoConverter;

	//@RequestMapping(method = RequestMethod.GET, value="/topicos")
	@GetMapping
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso, @PageableDefault(sort="titulo", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao){
		Page<Topico> topicos;
		if(nomeCurso == null) {
			topicos= topicoRepository.findAll(paginacao);
		} else {
			topicos= topicoRepository.findByCursoNome(nomeCurso, paginacao);
		}
		Page<TopicoDto> topicosDto = topicos.map(topico -> { return new TopicoDto(topico); });
		return topicosDto;

	}

	//@RequestMapping(method = RequestMethod.POST, value="/topicos")
	@PostMapping
	public ResponseEntity<TopicoDto > cadastrar(@RequestBody @Valid TopicoFormDto topicoForm, UriComponentsBuilder uriBuilder) {
		Topico topico = topicoConverter.from(topicoForm);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicoDetalheDto> detalhar( @PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new TopicoDetalheDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoFormUpdateDto topicoFormUpdate, UriComponentsBuilder uriBuilder ){
		Optional<Topico> topico = topicoConverter.from(id, topicoFormUpdate);
		if(topico.isPresent()) {
			topicoRepository.save(topico.get());
			return ResponseEntity.ok(new TopicoDto(topico.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover( @PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}


}
