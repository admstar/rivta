package com.mawell.nlt.consumer;

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
      logicalAddress.setValue("01"); // Områdeskod

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
      System.out.println("1. Sökandes personnummer: " + patientData.getPersonId());

      // 2. Tjänsteutövarens (t.ex. Vårdenhet) HSAID
      String hsaID = patientData.getListing().get(0).getHealthcareFacility().getFacilityId();
      System.out.println("2. Tjänsteutövaren (HSAID): " + hsaID);

      // 3. Hämtar mer detaljer om tjänsteutövaren.
      Facility facility = patientData.getListing().get(0).getHealthcareFacility();
      Resource resource = patientData.getListing().get(0).getResource();
            
      if(facility != null)
      {
         // tjänsteutövaren är en vårdenhet och innehåller följande data.

          System.out.println(" Vårdenhet:");
          // 1. Namn.
          System.out.println(" 1. Namn: " + facility.getFacilityName());
         
         // 2. Har vårdenheten kö just nu.
         String queue = (facility.isHasQueue())? "Ja" : "Nej";
         System.out.println(" 2. Är det kö just nu: " + queue);
      }
      if (resource != null)
      {
         // Tjänsteutövaren är en specifik läkare och innehåller följande
         System.out.println(" Vårdgivare:");
         
         // *** Hämtar ut data ***
         // 1. Person id.
         System.out.println(" 1. Person id: " + resource.getResourceId());
         // 1. Namn.
         System.out.println(" 2. Namn: " + resource.getResourceName());

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
