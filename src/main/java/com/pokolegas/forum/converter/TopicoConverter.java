package com.pokolegas.forum.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pokolegas.forum.dto.TopicoFormDto;
import com.pokolegas.forum.dto.TopicoFormUpdateDto;
import com.pokolegas.forum.model.Curso;
import com.pokolegas.forum.model.Topico;
import com.pokolegas.forum.repository.CursoRepository;
import com.pokolegas.forum.repository.TopicoRepository;

@Component
public class TopicoConverter {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TopicoRepository topicoRepository;

	public Topico from(TopicoFormDto topicoForm){
		Curso curso = cursoRepository.findByNome(topicoForm.getNomeCurso());
		Topico topico = new Topico(topicoForm.getTitulo(), topicoForm.getMensagem(), curso);
		return topico;
	}

	public Optional<Topico> from(Long id, TopicoFormUpdateDto topicoFormUpdate){
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			topico.get().setTitulo(topicoFormUpdate.getTitulo());
			topico.get().setMensagem(topicoFormUpdate.getMensagem());
		}
		return topico;
	}

}
