package com.mawell.vval.consumer;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.w3._2005._08.addressing.AttributedURIType;

import riv.crm.carelisting._1.Facility;
import riv.crm.carelisting._1.Resource;
import riv.crm.carelisting._1.SubjectOfCare;
import riv.crm.carelisting.getlisting._1.rivtabp20.GetListingResponderInterface;
import riv.crm.carelisting.getlisting._1.rivtabp20.PersonNotFound;
import riv.crm.carelisting.getlisting._1.rivtabp20.TechnicalException;
import riv.crm.carelisting.getlistingresponder._1.GetListingRequestType;
import riv.crm.carelisting.getlistingresponder._1.GetListingResponseType;


/**
 * Exempelkod för användningsfallet "Hämta tjänsteval".
 * 
 * @author Robert Siwerz, www.mawell.com.
 */
public class UseCaseHamtaTjansteval
{

   private void useCaseHamtaVardval() throws MalformedURLException
   {
      // Hämtar referens till SEI (Service Endpoint Interface).
      QName serviceName = new QName("urn:riv:crm:carelisting:GetListing:1:rivtabp20", "GetListingResponderService");
      Service service = Service.create(new URL("http://127.0.0.1:8088/mockGetListingResponderBinding?WSDL"), serviceName);

      GetListingResponderInterface listingSEI = service.getPort(GetListingResponderInterface.class);

      // Hämtar listningsinformation för angiven person.
      AttributedURIType logicalAddress = new AttributedURIType();
      logicalAddress.setValue("SE239482390-23SAD");   // HSAID till huvudmannen som skall svara på frågan.
      GetListingRequestType request = new GetListingRequestType();
      request.setPersonId("195005055005");
      GetListingResponseType response = null;
      
      try
      {
         response = listingSEI.getListing(logicalAddress, request);
      } catch (PersonNotFound e)
      {
         // Använd affärsregel för att hantera detta.
      } catch (TechnicalException e)
      {
         // Gör ett nytt försök...
      }

      // Skriver ut debug information
      SubjectOfCare patientData = response.getSubjectOfCare();
      // 1. Personnummer
      System.out.println("1. Personens personnummer: " + patientData.getPersonId());

      // 2. Tjänsteutövarens (t.ex. Vårdenhet) HSAID
      String hsaID = patientData.getListing().get(0).getHealthcareFacility().getFacilityId();
      System.out.println("2. Tjänsteutövaren (HSAID): " + hsaID);

      // 3. Hämtar mer detaljer om tjänsteutövaren.
      Facility facility = patientData.getListing().get(0).getHealthcareFacility();
      Resource resource = patientData.getListing().get(0).getResource();
      
      if(facility != null)
      {
         // tjänsteutövaren är en vårdenhet och innehåller följande data.
         // 1. Namn.
         System.out.println(" Vårdenhet:");
         
         // *** Hämtar ut data ***
         // 1. Namn
         // 2. Har vårdenheten kö just nu.
         System.out.println(" 1. Namn: " + facility.getName());
         
         // 2. Har vårdenheten kö just nu.
         String queue = (facility.isHasQueue())? "Ja" : "Nej";
         System.out.println(" 2. Är det kö just nu: " + queue);
         
      } else if (resource != null)
      {
         // Tjänsteutövaren är en specifik läkare och innehåller följande
         // data.
         // 1. Person id.
         // 2. Namn.
         System.out.println(" Läkare:");
         
         // *** Hämtar ut data ***
         // 1. Person id.
         System.out.println(" 1. Person id: " + resource.getResourceId());

         // 2. Namn.
         System.out.println(" 2. Namn: " + resource.getName());
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
         UseCaseHamtaTjansteval exempelkod = new UseCaseHamtaTjansteval();
         exempelkod.useCaseHamtaVardval();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
