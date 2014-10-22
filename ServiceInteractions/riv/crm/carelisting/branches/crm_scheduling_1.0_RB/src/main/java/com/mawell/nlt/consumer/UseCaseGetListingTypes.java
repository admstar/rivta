package com.mawell.nlt.consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.w3._2005._08.addressing.AttributedURIType;

import riv.crm.carelisting.getlistingtypes._1.rivtabp20.GetListingTypesResponderInterface;
import riv.crm.carelisting.getlistingtypes._1.rivtabp20.TechnicalException;
import riv.crm.carelisting.getlistingtypes._1.rivtabp20.PersonNotFoundException;
import riv.crm.carelisting.getlistingtypesresponder._1.GetListingTypesRequestType;
import riv.crm.carelisting.getlistingtypesresponder._1.GetListingTypesResponseType;

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
 * Exempelkod för användningsfallet "Hämta tillgänglia listningstyper, t.ex {BVC, HLM}".
 * @author Robert Siwerz, www.mawell.com.
 */
public class UseCaseGetListingTypes
{
   
   public void useCaseGetListingTypes() throws MalformedURLException
   {
      // Hämtar referens till SEI (Service Endpoint Interface).                               
      QName serviceName = new QName("urn:riv:crm:carelisting:GetListingTypes:1:rivtabp20", "GetListingTypesResponderService");
      Service service = Service.create(new URL("http://127.0.0.1:8088/mockGetListingTypesResponderBinding?WSDL"),   serviceName);
  
      GetListingTypesResponderInterface listingSEI = service.getPort(GetListingTypesResponderInterface.class);
         
      // Hämta tillgängliga tjänsteutövare. 
      AttributedURIType logicalAddress = new AttributedURIType();
      logicalAddress.setValue("01"); // Områdeskod

      GetListingTypesRequestType request = new GetListingTypesRequestType();
      GetListingTypesResponseType response = null;
      
      try 
      {
         response = listingSEI.getListingTypes(logicalAddress, request);
      }
      catch (TechnicalException e)
      {
         // Gör ett nytt försök...
      }
      catch (PersonNotFoundException e)
      {
    	  // 
      }
      
      // Itererar över listan med listningstyper.
      System.out.println("Möjliga listningsval för personen");
      List<String> listingTypes = response.getListingType();
      
      for(String type : listingTypes)
      {
         System.out.println("Listningstyp: " + type);
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
         UseCaseGetListingTypes exempelkod = new UseCaseGetListingTypes();
         exempelkod.useCaseGetListingTypes();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
