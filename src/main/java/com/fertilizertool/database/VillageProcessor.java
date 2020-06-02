package com.fertilizertool.database;

import org.springframework.batch.item.ItemProcessor;

import com.fertilizertool.model.VillageMaster;

public class VillageProcessor implements ItemProcessor<VillageMaster, VillageMaster> {

 @Override
 public VillageMaster process(VillageMaster villageMaster) throws Exception {
  return villageMaster;
 }

} 
