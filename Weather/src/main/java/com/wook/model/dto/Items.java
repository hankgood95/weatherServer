package com.wook.model.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Items {
	private List<Item> item;

	public List<Item> getItem() {
		return item;
	}

	@Override
	public String toString() {
		return "Items [item=" + item + "]";
	}
	
}
