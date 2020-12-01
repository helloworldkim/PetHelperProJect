package com.help.HelloPet.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "body")
public class Body {
	
	@XmlElementWrapper(name="items")
	private List<Item> items;
	 
}
@Data
@XmlRootElement(name="item")
class Item{
	
	private String orgCd;
	private String orgdownNm;
	
}