package indicadores;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DadosPessoa;
import service.IndicadoresService;

/**
 * Servlet implementation class CalculoIndicadores
 */
public class CalculoIndicadores extends HttpServlet {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
       
	/**
	 * Define o calculo de tipo 1
	 */
	private static final String CALCULO_TIPO_1 = "TIPO_1";

	/**
	 * Define o calculo de tipo 2
	 */
	private static final String CALCULO_TIPO_2 =  "TIPO_2";

	/**
	 * Tipo de calculo definido nas configuracoes
	 */
    private String calculoConfig;

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalculoIndicadores() {
        super();
      
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		  calculoConfig = config.getInitParameter("tipoCalculo");
	        if(null == calculoConfig || calculoConfig.isEmpty()) {
	        	calculoConfig = CALCULO_TIPO_1;
	        }	
	        
	        if (!calculoConfig.equals(CALCULO_TIPO_1) && !calculoConfig.equals(CALCULO_TIPO_2)) {
	            throw new ServletException("Tipo de cálculo inválido: " + calculoConfig);
	        }

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String title = "<h1> Calculo de Indicadores de Saude </h1>";
		StringWriter resposta = new StringWriter();
		resposta.write(title);
		String erro = "<h2>Dados Inválidos, por favor, tente novamente.</h2>";
		
		double numberAltura = IndicadoresService.validarAltura(request.getParameter("altura"));
		double numberPeso = IndicadoresService.validarPeso(request.getParameter("peso"));
		boolean isMasc = IndicadoresService.validarBooleanos(request.getParameter("isMasculino"));
		
		if(numberAltura < 0 || numberPeso < 0) {
			resposta.write(erro);
		}else {
			   DadosPessoa user = new DadosPessoa.Builder()
		                .altura(numberAltura)
		                .peso(numberPeso)
		                .masculino(isMasc)
		                .build();
			   
			   double numberResponse = 0;
			   if(numberPeso != 0) {
				   numberResponse = IndicadoresService.calcularIndicador(user, calculoConfig);  
					resposta.write("<h2>Resultado do Cálculo de IMC: " + numberResponse + "</h2>");
			   }else {
				   numberResponse = IndicadoresService.calcularPesoIdeal(user);
					resposta.write("<h2>Resultado do Cálculo de peso Ideal: " + numberResponse + "</h2>");
			   }
		}
		
		resposta.write("<button onclick=\"history.back()\">Voltar</button>");
        response.getWriter().write(resposta.toString());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
