package com.help.HelloPet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.help.HelloPet.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

}
