package com.apap.tutorial5.service;

import java.util.List;

import com.apap.tutorial5.model.CarModel;

public interface CarService {
	void addCar(CarModel car);
	
	List<CarModel> getListCarByDealerIdSortByPrice(Long id);
	
	void deleteCar(long id);
	
	void updateCar(long id, CarModel carModel);
	
	CarModel getCar(long id);
}