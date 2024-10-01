package com.flex360.api_flex360.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex360.api_flex360.services.CarrinhoService;

@RestController
@RequestMapping("carrinho")
public class CarrinhoController {
    
    @Autowired
    private CarrinhoService carrinhoService;

}
