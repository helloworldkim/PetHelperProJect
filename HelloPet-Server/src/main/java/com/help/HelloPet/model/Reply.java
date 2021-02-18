package com.help.HelloPet.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 200)
	private String content;
	
//	@ManyToOne
//	@JoinColumn(name = "boardid")
//	private Board board;
	private int boardid;
	
//	@ManyToOne
//	@JoinColumn(name = "userid")
//	private User user;
	private int userid;
	
	@JsonFormat(pattern = "yyyy-MM-dd")// 데이터 포맷을 등록하면 받아서 자동으로 변환해줌
	@CreationTimestamp
	private Timestamp createDate;
}
