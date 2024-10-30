package com.exemple.security.services.parametrage.Numero;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemple.security.entity.Numero;
import com.exemple.security.repository.NumeroRepository;

@Service
public class NumeroService implements InNumeroService{

	@Autowired
	private NumeroRepository numeroRepository;


	@Override
	public String genrateNumero(Numero numero)
	{

		String code = "00000000000000000000"+ numero.getVeleur();

		String pfix = "" , sfix = "";


		if(numero.getPfix() !=null)
		{
			pfix = numero.getPfix();
		}

		if(numero.getSfix() !=null)
		{
			sfix = numero.getSfix();
		}

		code = code.substring(code.length() - numero.getPosition() , code.length());

		code = pfix + code + sfix;

		return code ;

	}


	@Override
	public String genrateNumero(String pfix, String sfix) {
		// TODO Auto-generated method stub
		return null;
	}
}
