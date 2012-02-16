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

package se.skl.rivta.bp21.refapp.producer.crm.scheduling;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.interoperability.headers._1.ActorType;
import se.skl.riv.crm.scheduling.v1.MakeBookingResponseType;
import se.skl.riv.crm.scheduling.v1.MakeBookingType;
import se.skl.riv.crm.scheduling.v1.ResultCodeEnum;
import se.skl.riv.crm.scheduling.v1.rivtabp21.MakeBookingResponderInterface;
import se.skl.rivta.bp21.refapp.util.Util;

@WebService(serviceName = "MakeBookingResponderService", endpointInterface = "se.skl.riv.crm.scheduling.v1.rivtabp21.MakeBookingResponderInterface", portName = "MakeBookingResponderPort", targetNamespace = "urn:riv:crm:scheduling:MakeBooking:1:rivtabp21", wsdlLocation = "schemas/interactions/MakeBookingInteraction/MakeBookingInteraction_1.1_RIVTABP21.wsdl")
public class Responder implements MakeBookingResponderInterface {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	@Override
	public MakeBookingResponseType makeBooking(String logicalAddress,
			ActorType actor, MakeBookingType parameters) {
		logger.info("Incoming client call");
		logger.info("Logical address: " + logicalAddress);

		final MakeBookingResponseType response = new MakeBookingResponseType();
		response.setBookingId("booking-" + logicalAddress);
		response.setResultCode(ResultCodeEnum.OK);
		response.setResultText("Booking was made successfully");

		return response;
	}
}