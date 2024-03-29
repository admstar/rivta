This is a Windows bat file for generating .Net WCF service contracts (C#) and client proxies from the WSDL files.
-----------------------------------------------------------------------------------------------------------------
The purpose is to give producer- and consumer projects that use .Net and WCF a head-start for setting up the build system to generate contract-first classes and interfaces for the crm-scheduling service domain.

You need the .Net SDK to run the batfile. It's part of Visual Studio. You may also download it separately from this url (the download support all windows versions from XP to Windows 7): http://msdn.microsoft.com/en-us/dd299405.aspx

How to run the bat-file:

1) Install .Net SDK or Microsoft Visual Studio. Make sure the bin-directory of your .Net SDK installation is on your PATH
2) Open a command window and "cd" to the directory where this howto-file is located.
3) Enter "generate-src.bat" on the commandline and process enter

The generated c# class file and the generated WCF app.config file will appear in a sub-directory created by the bat file: generated-src\GetCareContactInteraction.cs. GetCareContactInteraction.cs contains C# interfaces for all scheduling service contracts (all wsdl-files).

Now that you have the client proxies for calling a scheduling service from a .Net application, and the service contract interfaces required develop the scheduling services, you you may want to see a complete example. There isn't a complete example (consumer + producer) for these specific service contracts, but there is a complete example based on a sample service contract that you can use as a pattern / template. Since the way of working is the same, regardless of service contract, this is hopefully what you need to get up and running in .Net with the scheduling service contracts. See bellow for links to the example application. 

Producer- and consumer examples:

- set-up instructions: https://code.google.com/p/rivta/wiki/RefAppBasicProfileWcf
- You can browse the source code of the .Net sample application here: https://code.google.com/p/rivta/source/checkout
