package com.exemple.security.constants;


public class GlobalConstants {

	// Status
	public static final String STATUT_ARCHIVE = "-2";
	public static final String STATUT_DELETE = "-1";
	public static final String STATUT_INACTIF = "0";
	public static final String STATUT_ACTIF = "1";
	public static final String STATUT_PLANIFIER = "2";
	public static final String STATUT_VALIDER = "3";
	public static final String STATUT_CLOTURE = "4";

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
	            case STATUT_CLOTURE:
	            	return "cloture";
	            case STATUT_ARCHIVE:
	            	return "archive";
	            default:
	                return "inconnue";
	        }
	    }
}
