package se.skl.rivta.bp21.refapp.producer.crm.scheduling;

import java.util.Date;

import javax.jws.WebService;

import se.skl.riv.itintegration.monitoring.v1.ConfigurationType;
import se.skl.riv.itintegration.monitoring.v1.PingForConfigurationResponseType;
import se.skl.riv.itintegration.monitoring.v1.PingForConfigurationType;
import se.skl.riv.itintegration.monitoring.v1.rivtabp21.PingForConfigurationResponderInterface;

@WebService(
		serviceName = "PingForConfigurationResponderService", 
		endpointInterface="se.skl.riv.itintegration.monitoring.v1.rivtabp21.PingForConfigurationResponderInterface", 
		portName = "PingForConfigurationResponderPort", 
		targetNamespace = "urn:riv:itintegration:monitoring:PingForConfiguration:1:rivtabp21",
		wsdlLocation = "schemas/interactions/PingForConfiguration/PingForConfigurationInteraction_1.0_RIVTABP21.wsdl")
public class PingResponder implements PingForConfigurationResponderInterface {

	@Override
	public PingForConfigurationResponseType pingForConfiguration(
			String logicalAddress, PingForConfigurationType parameters) {
		final PingForConfigurationResponseType response = new PingForConfigurationResponseType();
		
		response.setPingDateTime(new Date().toString());
		response.setVersion("1.0");
		
		final ConfigurationType c1 = new ConfigurationType();
		c1.setName("os");
		c1.setValue("Mac OSX");
		
		response.getConfiguration().add(c1);
		
		return response;
	}
}
