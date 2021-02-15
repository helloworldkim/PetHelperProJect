package com.help.HelloPet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.help.HelloPet.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
