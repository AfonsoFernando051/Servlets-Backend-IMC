package service;

import javax.servlet.ServletException;

import model.DadosPessoa;

public class IndicadoresService {
	
	/**
	 * Define o calculo de tipo 1
	 */
	private static final String CALCULO_TIPO_1 = "TIPO_1";

	/**
	 * Define o calculo de tipo 2
	 */
	private static final String CALCULO_TIPO_2 =  "TIPO_2";
	
	/**
	 * Calcula o IMC
	 */
	public static double calcularIndicador(DadosPessoa user, String calculoConfig) throws ServletException {
		double imc = 0;
		double k  = 0;
		double e  = 0;

		if(calculoConfig.equalsIgnoreCase(CALCULO_TIPO_1)) {
			k = 1;
			e = 2;
		}else if(calculoConfig.equalsIgnoreCase(CALCULO_TIPO_2)){
			k = 1.3;
			e = 2.5;
		}else {
            throw new ServletException("Tipo de cálculo inválido: " + calculoConfig);
		}
		
		 if (user.getAltura() <= 0 || user.getPeso() <= 0) {
		        throw new ServletException("Altura ou peso inválido.");
		    }
		 
		double denominador = k * user.getPeso();
		double divisor = Math.pow(user.getAltura(), e);
		imc = denominador/divisor;
		return imc;
	}

	/**
	 * Valida numeros
	 */
	public static  double validarAltura(String parameter) {
	    if (parameter == null || parameter.isEmpty()) {
	        return -1f;
	    }
	    
	    try {
	    	parameter = parameter.replace(",", ".");
	    	return Double.parseDouble(parameter);
	    } catch (NumberFormatException e) {
	        return -1f;
	    }
	}

	/**
	 * Valida numeros
	 */
	public static  double validarPeso(String parameter) {
	    if (parameter == null || parameter.isEmpty()) {
	        return 0;
	    }
	    
	    try {
	    	parameter = parameter.replace(",", ".");
	    	return Double.parseDouble(parameter);
	    } catch (NumberFormatException e) {
	        return -1f;
	    }
	}
	
	/**
	 * Valida booleanos
	 */
	public static boolean validarBooleanos(String bool) {
	    return bool != null && bool.equalsIgnoreCase("on");
	}
	
	/**
	 * Calcula o peso ideal da pessoa com base no genero
	 */
	public static double calcularPesoIdeal(DadosPessoa user) {
	    double pesoIdeal = 0;
	    double alturaCm = user.getAltura() * 100; // Convertendo para centímetros
	    double fatorCorrecao = user.isMasculino() ? 4 : 2;

        pesoIdeal = (alturaCm - 100) - ((alturaCm - 150) / fatorCorrecao);

	    return pesoIdeal;
	}

}
