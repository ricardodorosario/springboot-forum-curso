package com.pokolegas.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokolegas.forum.model.Curso;

public interface CursoRepository  extends JpaRepository<Curso, Long>  {

	Curso findByNome(String nomeCurso);

}
