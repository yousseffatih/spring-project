package com.exemple.security.constants;


public class GlobalConstants {
	
	public static final String STATUT_DELETE = "-1";
	public static final String STATUT_INACTIF = "0";
	public static final String STATUT_ACTIF = "1";
	public static final String STATUT_PLANIFIER = "2";
	public static final String STATUT_VALIDER = "3";
	
	
	public static final int HTTPSTATUT_RESPONSE_ERORR = 450;
	
	
	  public static String getStatusDescription(String status) {
	        switch (status) {
	            case STATUT_DELETE:
	                return "delete";
	            case STATUT_INACTIF:
	                return "inactive";
	            case STATUT_ACTIF:
	                return "active";
	            case STATUT_PLANIFIER:
	                return "planifier";
	            case STATUT_VALIDER:
	                return "valider";
	            default:
	                return "inconnue";
	        }
	    }
}
