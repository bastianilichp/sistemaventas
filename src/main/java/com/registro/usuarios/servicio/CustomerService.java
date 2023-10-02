package com.registro.usuarios.servicio;

import org.springframework.web.multipart.MultipartFile;

import com.registro.usuarios.util.BaseResponse;

public interface CustomerService {
	
	BaseResponse importCustomerData(MultipartFile importFile);

}
