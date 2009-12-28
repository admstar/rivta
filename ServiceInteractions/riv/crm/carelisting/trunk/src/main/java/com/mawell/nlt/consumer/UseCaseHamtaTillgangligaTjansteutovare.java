package com.mawell.nlt.consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.w3._2005._08.addressing.AttributedURIType;

import riv.crm.carelisting._1.Facility;
import riv.crm.carelisting.getavailablefacilities._1.rivtabp20.GetAvailableFacilitiesResponderInterface;
import riv.crm.carelisting.getavailablefacilities._1.rivtabp20.TechnicalException;
import riv.crm.carelisting.getavailablefacilitiesresponder._1.GetAvailableFacilitiesRequestType;
import riv.crm.carelisting.getavailablefacilitiesresponder._1.GetAvailableFacilitiesResponseType;


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
 * Exempelkod för användningsfallet "Hämta tillgänglia tjänsteutövare".
 * Tjänsteutövare kan vara en vårdenhet.
 * @author Robert Siwerz, www.mawell.com.
 */
public class UseCaseHamtaTillgangligaTjansteutovare
{
	/**
	 * Exempelkod för Use Case "Hämtar tillgängliga tjänsteutövare".
	 * 
	 * @author Robert Siwerz,www.mawell.com.
	 * @throws Fel vid kommunikation med tjänsten.
	 */
	public void useCaseHamtaTillgangligaVardenheter() throws MalformedURLException
	{
		// Hämtar referens till SEI (Service Endpoint Interface).                               
		QName serviceName = new QName("urn:riv:crm:carelisting:GetAvailableFacilities:1:rivtabp20", "GetAvailableFacilitiesResponderService");
		Service service = Service.create(new URL("http://127.0.0.1:8088/mockGetAvailableFacilitiesResponderBinding?WSDL"),	serviceName);
  
		GetAvailableFacilitiesResponderInterface listingSEI = service.getPort(GetAvailableFacilitiesResponderInterface.class);
		   
		// Hämta tillgängliga tjänsteutövare. 
		AttributedURIType logicalAddress = new AttributedURIType();
		logicalAddress.setValue("SE239482390-23SAD"); // HSAID till huvudmannen som skall svara på frågan.
		GetAvailableFacilitiesRequestType request = new GetAvailableFacilitiesRequestType();
		GetAvailableFacilitiesResponseType response = null;
		
		try 
		{
		   response = listingSEI.getAvailableFacilities(logicalAddress, request);
		} catch (TechnicalException e)
		{
		   // Gör ett nytt försök...
		}
 
		// Skriver att tillgängliga tjänsteutövare. 
		List<Facility> facilities = response.getHealthcareFacilities();
		System.out.println("Tillgängliga vårdenheter:");
		Iterator<Facility> hsaIterator = facilities.iterator();

		while (hsaIterator.hasNext()) 
		{
		   Facility facility = hsaIterator.next(); 
			System.out.println("HSAID: " + facility.getFacilityId() + ", namn: " + facility.getName());
		} 
	}

	/**
	 * Entry point i Java applikationen.
	 * 
	 * @param args kommando-prompt argument.
	 */
	public static void main(String[] args)
	{
		try
		{
			UseCaseHamtaTillgangligaTjansteutovare exempelkod = new UseCaseHamtaTillgangligaTjansteutovare();
			exempelkod.useCaseHamtaTillgangligaVardenheter();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
