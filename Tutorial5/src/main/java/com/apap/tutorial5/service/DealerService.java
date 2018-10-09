package com.apap.tutorial5.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.DealerModel;

public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	
	void addDealer(DealerModel dealer);
	
	List<DealerModel> findAll();
	
	void deleteDealer(DealerModel dealer);
	
	void updateDealer(long id, Optional<DealerModel> dealer);
}
