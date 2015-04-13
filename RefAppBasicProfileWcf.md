# RIV Teknisk anvisning - Referensapplikation - Microsoft .Net WCF #



## Environment ##

The reference application requires the following to be pre installed:

  * Microsoft Visual Studio
  * Microsoft .Net WCF 3.5
  * httpcfg-utility (Windows XP only)
    * If not already installed then download and install from [Windows XP Service Pack 2 Support Tools](http://www.microsoft.com/downloads/details.aspx?FamilyId=49AE8576-9BB9-4126-9761-BA8011FABF38&displaylang=en)
  * Any Subversion client
    * [download](http://subversion.tigris.org/)

## Create test certificates ##

Open a command prompt and run the following commands:

```
 makecert -sk RivTaTestCA   -ss Root -sky signature -sr localmachine -n "CN=RivTaTestCA"   -r  RivTaTestCA.cer
 makecert -sk RivTaProducer -ss MY   -sky exchange  -sr localmachine -n "CN=localhost"     -ic RivTaTestCA.cer -is Root RivTaProducer_localhost.cer
 makecert -sk RivTaConsumer -ss MY   -sky exchange  -sr currentuser  -n "CN=RivTaConsumer" -ic RivTaTestCA.cer -is Root RivTaConsumer.cer
```

This will create and install:

  * A Root CA certificate for test
  * A producer certificate for test signed by the test root CA certificate
  * A consumer certificate for test signed by the test root CA certificate

**NOTE**: In Windows 7, the command prompt needs to be run as administrator in order to create certificates.

![http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-win7-cmd-asadmin-rivtabp21.jpg](http://rivta.googlecode.com/svn/wiki/images/refApp/wcf-win7-cmd-asadmin-rivtabp21.jpg)

## Register usage of the producer certificate for the https port ##

  * Configure Microsoft Management Console to display Certificates (for Windows 7 see below)
    * In a command prompt window, type the following command: mmc
    * In the File menu, click Add/Remove Snap-In.
    * In the Add/Remove Snap-In dialog box, click Add.
    * In the Add Standalone Snap-In dialog box, select the Certificates snap-in and then click Add.
    * In the Certificates Snap-In dialog box, select Computer account and then click Next.
    * In the Select Computer dialog box, select Local computer and then click Finish.
    * In the Add Standalone Snap-In dialog box, click Close.
    * In the Add/Remove Snap-In dialog box, click OK.

  * In Windows 7:
    * In a command prompt window, type the following command: mmc
    * In the File menu, click Add/Remove Snap-In.
    * In the Available Snap-ins dialog box, select the Certificates snap-in and then click Add.
    * In the Certificates Snap-In dialog box, select Computer account and then click Next.
    * In the Select Computer dialog box, select Local computer and then click Finish.
    * In the Add/Remove Snap-In  dialog box, click OK.

  * Identify the thumbprint for the producer certificate
    * In the Console Root window, expand the Certificates node, expand the Personal folder, and then click the Certificates folder. The producer certificate that you created by using the makecert utility should be displayed as localhost:

![http://rivta.googlecode.com/svn/wiki/images/wcf-producer-cert.png](http://rivta.googlecode.com/svn/wiki/images/wcf-producer-cert.png)


  * Double-click the producer certificate, `RivTaProducer`.
  * In the Certificate window, click the Details tab. Scroll to the bottom of the window displaying the details of the certificate. Click the Thumbprint property, and make a note of the hexadecimal string displayed in the lower window:

![http://rivta.googlecode.com/svn/wiki/images/wcf-producer-cert-thumbprint.png](http://rivta.googlecode.com/svn/wiki/images/wcf-producer-cert-thumbprint.png)

  * Register the producer certificate for the https port (assuming the thumbprint from above)

  * For Windows XP and Windows 2003 server: In a command prompt window, type the following command:

  * httpcfg set ssl -i 0.0.0.0:12000 -h  a6b7f3f7d4861439a528884ca6b781de1bcd3f30

  * For Windows Vista, Windows 7 and Windows 2008 server: In a command prompt window, type the following command:

  * netsh http add sslcert ipport=0.0.0.0:12000 certhash=a6b7f3f7d4861439a528884ca6b781de1bcd3f30 appid={00112233-4455-6677-8899-AABBCCDDEEFF} clientcertnegotiation=enable

**NOTE**: For Windows 7, you have to run command prompt as administrator.

**NOTE**: replace the thumbprint a6b7f3f7d4861439a528884ca6b781de1bcd3f30 with yours thumbprint as displayed in the Microsoft Management Console but with the spaces removed

  * If the certificates don't work and you need to re-create them, the certificate file can be deleted from the port by typing the following command: "netsh http delete sslcert ipport=0.0.0.0:12000"

## Checkout and build the reference application ##

http://code.google.com/p/rivta/source/checkout

## Running the reference applications, Basic Profile v2.0, v2.1 ##

  * [Instructions Basic Profile v2.0](RefAppBasicProfile20WcfRunningInstructions.md)
  * [Instructions Basic Profile v2.1](RefAppBasicProfile21WcfRunningInstructions.md)

## Overview of Visual Studio projects ##

  * schemas
    * Contains XML Schemas and WSDL artefacts.
    * Also responsible for generating C# proxy code.

  * producer
    * Service producer.

  * consumer
    * Service consumer.