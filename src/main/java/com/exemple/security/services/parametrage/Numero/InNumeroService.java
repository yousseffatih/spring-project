package com.exemple.security.services.parametrage.Numero;

import com.exemple.security.entity.Numero;

public interface InNumeroService {

	String genrateNumero(String pfix, String sfix);

	String genrateNumero(Numero numero);

}
