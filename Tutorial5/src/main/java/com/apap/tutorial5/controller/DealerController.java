 package com.apap.tutorial5.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value="/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value="/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value= "dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam("dealerId") long dealerId, Model model) {
		 DealerModel dealerView = dealerService.getDealerDetailById(dealerId).get();
		 List<CarModel> archiveCar = carService.getListCarByDealerIdSortByPrice(dealerId);
		 long addCardId = dealerId;
		 model.addAttribute("dealer", dealerView);
		 model.addAttribute("idDealer", addCardId);
		 model.addAttribute("listCar", archiveCar);
		 return "view-dealer";
	}
	
	@RequestMapping(value= "dealer/viewAll", method = RequestMethod.GET)
	private String viewAllDealer(Model model) {
		List<DealerModel> dealer = dealerService.findAll();
		model.addAttribute("dealer", dealer);
		return "dealer-viewAll";
	}
	
	@RequestMapping(value = "dealer/delete", method = RequestMethod.GET)
	private String deleteDealer(@RequestParam("dealerId") long dealerId, Model model) {
		Optional<DealerModel> dealer = dealerService.getDealerDetailById(dealerId);
		if(dealer.isPresent()) {
			dealerService.deleteDealer(dealer.get());
			return "delete-dealer";
		}
		return "error";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value = "dealerId") long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		model.addAttribute("dealer", dealer);
		return "update-dealer";
	}
	
	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.POST)
	private String updateDealerSubmit(@PathVariable(value = "id") long dealerId, @ModelAttribute Optional<DealerModel> dealer) {
		if(dealer.isPresent()) {
			dealerService.updateDealer(dealerId, dealer);
			return "update";
		}
		return "error";
	}
}
