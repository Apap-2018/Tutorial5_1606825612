package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		dealer.setListCar(new ArrayList<CarModel>());
		dealer.getListCar().add(new CarModel());
		
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"add"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute DealerModel dealer, Model model) {
		dealer.getListCar().add(new CarModel());
	    model.addAttribute("dealer", dealer);
	    return "addCar";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"delete"}, method=RequestMethod.POST)
	public String removeRow(@ModelAttribute DealerModel dealer, Model model, HttpServletRequest req) {
		int indexRow = Integer.parseInt(req.getParameter("delete"));
		dealer.getListCar().remove(indexRow);
		
		model.addAttribute("dealer", dealer);
		return "addCar";		
	}
	

	
	
	@RequestMapping(value = "/car/add/{dealerId}", params={"save"}, method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute DealerModel dealer) {
		DealerModel currentDealer = dealerService.getDealerDetailById(dealer.getId()).get();
		//kita harus ngesave id dealer dulu sebelum ngesave car, karena car hubungannya total
		for (CarModel car:dealer.getListCar()) {
			car.setDealer(currentDealer);
			carService.addCar(car);
		}
		return "add";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String deleteCar(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car.getId());
		}
		return "delete-car";
	}
		
	@RequestMapping(value = "/car/update/{id}", method = RequestMethod.GET)
	private String updateCar(@PathVariable(value = "id") long id, Model model) {
		CarModel car = carService.getCar(id);
		model.addAttribute("car", car);
		return "update-car";	
	}
	
	@RequestMapping(value = "/car/update/{id}", method = RequestMethod.POST)
	private String updateCarSubmit(@PathVariable(value = "id") long id, @ModelAttribute CarModel car) {
		carService.updateCar(id, car);
		return "update";
	}
}
