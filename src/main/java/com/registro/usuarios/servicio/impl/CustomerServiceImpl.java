package com.registro.usuarios.servicio.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.registro.usuarios.servicio.CustomerService;
import com.registro.usuarios.util.BaseResponse;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public BaseResponse importCustomerData(MultipartFile importFile) {


		return null;
	}
	
	

}
