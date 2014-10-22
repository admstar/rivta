package com.mawell.nlt.consumer;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.w3._2005._08.addressing.AttributedURIType;

import riv.crm.carelisting.createlisting._1.rivtabp20.CreateListingResponderInterface;
import riv.crm.carelisting.createlisting._1.rivtabp20.InvalidFacilityException;
import riv.crm.carelisting.createlisting._1.rivtabp20.PersonNotFoundException;
import riv.crm.carelisting.createlisting._1.rivtabp20.TechnicalException;
import riv.crm.carelisting.createlistingresponder._1.CreateListingRequestType;
import riv.crm.carelisting.createlistingresponder._1.CreateListingResponseType;

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
 * Exempelkod för användningsfallet "Göra Tjänsteval".
 * 
 * @author Robert Siwerz,www.mawell.com.
 */
public class UseCasePerformAListing
{

   private void useCasePerformASpecificListing() throws MalformedURLException
   {
      // Hämtar referens till SEI (Service Endpoint Interface).
      QName serviceName = new QName("urn:riv:crm:carelisting:CreateListing:1:rivtabp20",
            "CreateListingResponderService");
      Service service = Service.create(new URL( "http://127.0.0.1:8088/mockCreateListingResponderBinding?WSDL"),
            serviceName);

      CreateListingResponderInterface listingSEI = service.getPort(CreateListingResponderInterface.class);

      try
      {
         AttributedURIType logicalAddress = new AttributedURIType();
         logicalAddress.setValue("01"); // Områdeskod

         // Skapar ett fråge objekt.
         CreateListingRequestType request = new CreateListingRequestType();
         request.setPersonId("195005055005");
         request.setHealthcareFacility("SE345345-ASD323");

         // Utför tjänstevalet.
         CreateListingResponseType response = listingSEI.createListing(logicalAddress, request);

         String status = (response.isSuccess() == true) ? "OK" : "FEL";
         System.out.println("Listnings status: " + status);
         System.out.println("Kommleterande information om listningsstatus: " + response.getComment());
         System.out.println("Kod från listningstjänsten: " + response.getSystemCode());
      } catch (PersonNotFoundException e)
      {
         System.out.println("Felkod:" + e.getFaultInfo().getCode());
      } catch (InvalidFacilityException e)
      {
         System.out.println("Felkod:" + e.getFaultInfo().getCode());
      } catch (TechnicalException e)
      {
         // Gör ett nytt försök...
      }
   }

   /**
    * Entry point i Java applikationen.
    * 
    * @param args
    *           kommando-prompt argument.
    */
   public static void main(String[] args)
   {
      try
      {
         UseCasePerformAListing exempelkod = new UseCasePerformAListing();
         exempelkod.useCasePerformASpecificListing();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
