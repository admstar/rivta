package com.mawell.vval.consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashSet;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.axis2.databinding.types.URI;

import com.sun.media.sound.HsbParser;

import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.TechnicalException;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.AttributedURIType;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.GetAvailableServiceProviders;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.GetAvailableServiceProvidersRequestType;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.GetAvailableServiceProvidersResponse;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.HsaIdType;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.ServiceProvider;
import rivtabp20._1.getavailableserviceproviders.carelisting.crm.riv.GetAvailableServiceProvidersResponderServiceStub.To;



/**
 * Exempelkod för användningsfallet "Hämta tillgänglia tjänsteutövare".
 * Tjänsteutövare kan vara en vårdenhet.
 * @author Robert Siwerz, www.mawell.com.
 */
public class UseCaseHamtaTillgangligaTjansteutovareAxis
{
   /**
    * Exempelkod för Use Case "Hämtar tillgängliga tjänsteutövare".
    * 
    * @author Robert Siwerz,www.mawell.com.
    * @throws Fel vid kommunikation med tjänsten.
    */
   public void useCaseHamtaTillgangligaVardenheter() throws MalformedURLException
   {
      GetAvailableServiceProvidersResponse  response = null;
      
      try 
      {
         GetAvailableServiceProvidersResponderServiceStub stub = new GetAvailableServiceProvidersResponderServiceStub("http://127.0.0.1:8088/mockGetAvailableServiceProvidersResponderBinding?WSDL");
         GetAvailableServiceProviders getAvailableServiceProviders0 = new GetAvailableServiceProviders();
         GetAvailableServiceProvidersRequestType data = new GetAvailableServiceProvidersRequestType();
         HsaIdType hsaId = new HsaIdType();
         hsaId.setHsaIdType("121212-1212");
         data.setRegion(hsaId);
         getAvailableServiceProviders0.setGetAvailableServiceProviders(data);
         
         To to1 = new To();
         AttributedURIType logicalAddress = new AttributedURIType();
         URI uri = new URI();
         uri.setPath("SE239482390-23SAD");
         logicalAddress.setAnyURI(uri); // HSAID till huvudmannen som skall svara på frågan.
         to1.setTo(logicalAddress);
         
         // Hämta tillgängliga tjänsteutövare. 
         response = stub.getAvailableServiceProviders(getAvailableServiceProviders0, to1);
      } catch (TechnicalException e) 
      {
         // Gör ett nytt försök...
      } catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return;
      }
 
      // Skriver att tillgängliga tjänsteutövare. 
      ServiceProvider[] hsaIDs = response.getGetAvailableServiceProvidersResponse().getServiceProvider();
      System.out.println("Tillgängliga tjänsteutövare:");

      for (int i =0; i < hsaIDs.length; i++) 
      {  
         ServiceProvider serviceProvider = hsaIDs[i]; 
         System.out.println("HSAID: " + serviceProvider.getServiceProviderId() + ", " + serviceProvider.getName() + ", typ = " + serviceProvider.getServiceProviderType().getValue());
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
         UseCaseHamtaTillgangligaTjansteutovareAxis exempelkod = new UseCaseHamtaTillgangligaTjansteutovareAxis();
         exempelkod.useCaseHamtaTillgangligaVardenheter();
      } catch (Exception e)
      {
         e.printStackTrace();
      }
   } 
}
