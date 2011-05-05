/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package se.skl.rivta.bp21.refapp.producer.eservicessupply.eoffering;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.riv.eservicesupply.eoffering.v1.AvailableEServicesType;
import se.skl.riv.eservicesupply.eoffering.v1.EServiceType;
import se.skl.riv.eservicesupply.eoffering.v1.GetAvailableEServicesResponseType;
import se.skl.riv.eservicesupply.eoffering.v1.GetAvailableEServicesType;
import se.skl.riv.eservicesupply.eoffering.v1.HealthcareFacilityInfoType;
import se.skl.riv.eservicesupply.eoffering.v1.rivtabp21.GetAvailableEServicesResponderInterface;
import se.skl.rivta.bp21.refapp.util.Util;
import se.skl.rivta.itintegration.registry.v1.LogicalAddressType;

@WebService(
	serviceName = "GetAvailableEServicesResponderService", 
	endpointInterface="se.skl.riv.eservicesupply.eoffering.v1.rivtabp21.GetAvailableEServicesResponderInterface", 
	portName = "GetAvailableEServicesResponderPort", 
	targetNamespace = "urn:riv:eservicesupply:eoffering:GetAvailableEServices:1:rivtabp21",
	wsdlLocation = "schemas/interactions/GetAvailableEServicesInteraction/GetAvailableEServicesInteraction_1.0_RIVTABP21.wsdl")
public class Responder implements GetAvailableEServicesResponderInterface {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	@Override
	public GetAvailableEServicesResponseType getAvailableEServices(
			LogicalAddressType logicalAddress,
			GetAvailableEServicesType parameters) {
		logger.info("Incoming client call");
		logger.info("LogicalAddress:To = " + logicalAddress.getTo());
		
		final GetAvailableEServicesResponseType response = new GetAvailableEServicesResponseType();
		
		final HealthcareFacilityInfoType hf = new HealthcareFacilityInfoType();
		hf.setHealthcareFacility(logicalAddress.getTo());
		hf.setHealthcareFacilityName("Healthcare facility X");
		
		final AvailableEServicesType service = new AvailableEServicesType();
		service.setHealthcareFacility(hf);
		
		final EServiceType eService = new EServiceType();
		eService.setCommonName("E-service namn");
		eService.setDescription("Beskrivning av E-service");
		eService.setUrl("http://tempuri.org");
		
		service.getEservice().add(eService);
		
		response.getAvailableEServices().add(service);
		
		return response;
	}
}