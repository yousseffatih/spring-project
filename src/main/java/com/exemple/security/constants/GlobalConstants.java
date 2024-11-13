package com.exemple.security.constants;


public class GlobalConstants {

	// Status
	public static final String STATUT_ANNULER = "-3";
	public static final String STATUT_ARCHIVE = "-2";
	public static final String STATUT_DELETE = "-1";
	public static final String STATUT_INACTIF = "0";
	public static final String STATUT_ACTIF = "1";
	public static final String STATUT_PLANIFIER = "2";
	public static final String STATUT_VALIDER = "3";
	public static final String STATUT_CLOTURE = "4";
	public static final String STATUT_REALISE = "5";
	public static final String STATUT_REPORTE = "6";

	public static final String PFIX_ACTIVITE = "AC";
	public static final String PFIX_AFFECTATIONS = "AF";
	public static final String PFIX_EMPLOYES = "EM";
	public static final String PFIX_FONCTION = "FO";
	public static final String PFIX_ORGANISME = "ORG";
	public static final String PFIX_ORGEXPDEST = "ORED";
	public static final String PFIX_PROCESSUS_COURRIER = "PC";
	public static final String PFIX_COURRIER = "CO";
	public static final String PFIX_PROCESSUS_MODEL = "PM";
	public static final String PFIX_RESEAUX = "RE";
	public static final String PFIX_ROLE = "RO";
	public static final String PFIX_USER_ROLE = "UR";
	public static final String PFIX_USER = "US";
	public static final String PFIX_VILLE = "VI";
	public static final String PFIX_TYPE_COURRIER = "TC";



	public static final int HTTPSTATUT_RESPONSE_ERORR = 450;

	public static final int HTTPSTATUT_REINITIALISER_PASSWORD = 451;


	  public static String getStatusDescription(String status) {
	        switch (status) {
	            case STATUT_DELETE:
	                return "delete";
	            case STATUT_INACTIF:
	                return "inactif";
	            case STATUT_ACTIF:
	                return "actif";
	            case STATUT_PLANIFIER:
	                return "planifier";
	            case STATUT_VALIDER:
	                return "valider";
	            case STATUT_CLOTURE:
	            	return "cloture";
	            case STATUT_ARCHIVE:
	            	return "archive";
	            case STATUT_ANNULER:
	            	return "annuler";
	            case STATUT_REALISE:
	            	return "realise";
	            case STATUT_REPORTE:
	            	return "reporte";
	            default:
	                return "inconnue";
	        }
	    }

	  public static String getStatusFromDescription(String description) {
		    switch (description) {
		        case "delete":
		            return STATUT_DELETE;
		        case "inactif":
		            return STATUT_INACTIF;
		        case "actif":
		            return STATUT_ACTIF;
		        case "planifier":
		            return STATUT_PLANIFIER;
		        case "valider":
		            return STATUT_VALIDER;
		        case "cloture":
		            return STATUT_CLOTURE;
		        case "realise":
		            return STATUT_REALISE;
		        case "reporte":
		            return STATUT_REPORTE;
		        case "annuler":
		            return STATUT_ANNULER;
		        case "archive":
		            return STATUT_ARCHIVE;
		        default:
		            return "x";
		    }
		}
}
