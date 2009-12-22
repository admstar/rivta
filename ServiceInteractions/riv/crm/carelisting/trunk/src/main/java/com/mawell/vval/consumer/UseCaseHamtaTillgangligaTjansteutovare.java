package com.mawell.vval.consumer;

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
		List<Facility> hsaIDs = response.getHealthcareFacilities();
		System.out.println("Tillgängliga vårdenheter:");
		Iterator<Facility> hsaIterator = hsaIDs.iterator();

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
