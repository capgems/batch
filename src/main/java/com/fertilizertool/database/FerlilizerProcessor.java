package com.fertilizertool.database;

import org.springframework.batch.item.ItemProcessor;

import com.fertilizertool.model.Fertilizers;

public class FerlilizerProcessor implements ItemProcessor<Fertilizers, Fertilizers> {

	 @Override
	 public Fertilizers process(Fertilizers fertlilizers) throws Exception {
	  return fertlilizers;
	 }

	} 
