package com.mawell.nlt.consumer;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.w3._2005._08.addressing.AttributedURIType;

import riv.crm.carelisting._1.PersonQueueStatus;
import riv.crm.carelisting.getpersonqueuestatus._1.rivtabp20.GetPersonQueueStatusResponderInterface;
import riv.crm.carelisting.getpersonqueuestatus._1.rivtabp20.PersonNotFoundException;
import riv.crm.carelisting.getpersonqueuestatus._1.rivtabp20.TechnicalException;
import riv.crm.carelisting.getpersonqueuestatusresponder._1.GetPersonQueueStatusRequestType;
import riv.crm.carelisting.getpersonqueuestatusresponder._1.GetPersonQueueStatusResponseType;

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
 * Exempelkod för användningsfallet "Hämta köinformation för en person".
 * @author Daniel Berggren, www.mawell.com.
 */
public class UseCaseGetPersonQueueStatus
{
   
   public void useCaseGetPersonQueueStatus() throws MalformedURLException
   {
      // Hämtar referens till SEI (Service Endpoint Interface).                               
      QName serviceName = new QName("urn:riv:crm:carelisting:GetPersonQueueStatus:1:rivtabp20", "GetPersonQueueStatusResponderService");
      Service service = Service.create(new URL("http://localhost:8088/mockGetPersonQueueStatusResponderBinding?WSDL"),   serviceName);
  
      GetPersonQueueStatusResponderInterface listingSEI = service.getPort(GetPersonQueueStatusResponderInterface.class);
         
      // Hämta köstsatus för person 
      AttributedURIType logicalAddress = new AttributedURIType();
      logicalAddress.setValue("SE239482390-23SAD"); // HSAID till huvudmannen som skall svara på frågan.
      GetPersonQueueStatusRequestType request = new GetPersonQueueStatusRequestType();
      request.setPersonId("1212121212-1212");
      
      GetPersonQueueStatusResponseType response = null;
      
      try 
      {
         response = listingSEI.getPersonQueueStatus(logicalAddress, request);
      }
      catch(PersonNotFoundException e)
      {
    	  
      }
      catch(TechnicalException e)
      {
    	  
      }
      
      // Itererar över listan med listningstyper.
      System.out.println("Köstatus för personen är");
      if(response.getQueueStatus()==PersonQueueStatus.IN_QUEUE)
    	  System.out.println("i kö");
      else
    	  System.out.println("inte i kö");
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
         UseCaseGetPersonQueueStatus exempelkod = new UseCaseGetPersonQueueStatus();
         exempelkod.useCaseGetPersonQueueStatus();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
