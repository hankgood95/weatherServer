package com.wook.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Items {
	private List<Item> item;

	public List<Item> getItem() {
		return item;
	}

}