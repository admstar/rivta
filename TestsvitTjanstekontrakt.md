# Översikt #
Som verktyg för att skapa och exekvera testsviterna används SoapUI som finns på http://www.soapui.org/.

För att hålla testsviterna på en rimlig storlek är rekommendationen att göra ett SoapUI-projekt per tjänstekontrakt.
Detta gör det möjligt för tjänsteleverantören att verifiera själva meddelandet i kontraktet.
Om domänen innehåller flera tjänstekontrakt som samverkar i en process, är ansatsen att göra ett separat SoapUI-projekt som verifiera flödet i processen genom flera kontrakt.

För varje tjänstekontrakt måste man skapa två SoapUI-projekt, där det första är själva testsviten och det andra innehåller en mock-tjänst som används vid utveckling och underhåll av sviten.

För att på enklast sätt få mock-tjänsten att returnera rätt svar på en Request används en HttpHeader (“x-mock-response”) som innehåller namnet på det svar som ska användas, varje Request i testsviten måste därför innehålla denna HttpHeader.

## Referensimplementation ##
För en inblick i hur en testsvit kan se ut är det enklast att titta på en av de befintliga testsviterna i, som du hittar under test-suite i katalogen för respektive kontrakt.

## Schematron-validering ##
För vissa affärsregler räcker det inte att använda XML Schema, och då är rekommendationen att skapa Schematron-regler som kan validera meddelanden ytterligare. För att hitta de konstruktioner där Schematron behövs får man jämföra Schema-definitionen av kontraktet med dess fältregler och se om det finns avvikande definitioner.
De områden där Schematron kan hjälpa till handlar oftast om regler som anger att "om fält A har ett värde får inte fält B anges".
Mer information om Schematron finns på http://www.schematron.com/.

För kontrakt som har regler av typen "För kompatibilitet med NPÖ 2.2.0 skall..." används en boolesk variabel
validateNPO som anger om NPÖ-reglerna skall valideras och en separat fil med dessa.

Se script-avsnittet nedan för meddelanden valideras mot Schematron-regler.

## Skapa testprojekt ##
  1. Skapa ett SoapUI-projekt för tjänstekontraktet, detta ska namnges med samma namn som kontraktet.
  1. Importera WSDL-filen för kontraktet.
  1. Lägg till en TestSuite i projektet.

## Skapa mockprojekt ##
  1. Skapa ytterligare ett SoapUI-projekt, som ska innehålla mock-tjänsterna. Detta ska namnges med samma namn som kontraktet med tillägget "Mock" sist.
  1. Importera WSDL-filen för kontraktet och välj "Create MockService" i guiden.
  1. Öppna "Mock Operation"-dialogen och välj "Script" som Dispatch. För att välja vilket svar mock-tjänsten ska returnera baserat på ovan nämnda HttpHeader, klistra in följande rader sist i script-editorn:
```
def responseName = mockRequest.requestHeaders['x-mock-response'][0]
log.debug("responseName: ${responseName}")

return responseName
```
  1. Scriptet kan även innehålla annan logik för att hämta värden från Request'en, men detta bör vara den minsta mängden kod som används för att välja rätt Response för givet Request.
  1. Du bör nu vara redo att skapa själva testfallen.

## Skapa testfall ##
  1. Lägg till ett TestCase under TestSuite'n.
  1. Lägg till ett "Script step" som namnges “Read data” under det nya TestCase't, som används för att läsa in testdata. Se exempel på script nedan.
  1. Lägg till ett "Test Request" efter Script-steget.
  1. Lägg till en HttpHeader “x-mock-response” för att låta mock-tjänsten kunna välja lämpligt svar på Request'en.
  1. Lägg till en HttpHeader “x-rivta-original-serviceconsumer-hsaid” med värdet “${httpHeaderHsaId}”. Värdet på variabel läses in av script-steget.
  1. Anpassa innehållet i Request'en så att det återspeglar det som ska verifieras av ditt testfall. Variabler refereras med ${careUnitHsaId}.
  1. Lägg till lämpliga verifieringar av svaret: Not Soap Fault, Respons SLA (15 sekunder), Schema Compliance, SOAP Response.
  1. Om det finns Schematron-regler för kontraktet, lägg till en "Script Assertion" som kontrollerar meddelandet mot dessa reglerna. Se script nedan.
  1. Lägg till ytterligare valideringar med Script, Xpath eller XQuery.

## Dokumentation ##
Dokumentation över testfallen genereras som en html-fil av build.gradle, som kan kopieras från annan testsvit.
För att kunna bygga måste man först bygga de projekt som finns under "rivta/tools/test/servicedomain-test-framework/trunk", så att man har dels support-logiken och test-suite-pluginen i sitt lokala repository.
(Lite oklart om detta gäller, men man måste i alla fall bygga en soapui-support-nnn.jar och checka in den i testsvitens root-katalog.)

För att bygga dokumentationen skriver man:
`gradle createDocument copyDependencies`

Resultatet hamnar i build/dist och filen TestSuiteDocumentation.html skall kopieras till testsvites root-katalog.

## Releasehantering ##
De genererade artefakterna soapui-support-nnn.jar och TestSuiteDocumentation.html skall checkas in, så att en release av tjänstekontraktet kan skapas utan att något bygg-steg utförs.

## Script ##
Här visas exempel på script som kan användas för att skapa en testsvit.

### Läsa testdata ###
```
import se.skl.rivta.testsupport.soapui.datasource.XmlDataReader
import com.eviware.soapui.support.GroovyUtils

def utils = new GroovyUtils(context)
def dataFile = new File(utils.projectPath + "/data.xml")
def source = new XmlDataReader(context, dataFile)

source.load("Date_Boundaries")
```

Den fil som innehåller testdata (i ovanstående exempel med namnet "data.xml") innehåller dels global data och dessutom data som är specifik för varje testfall (i ovanstående exempel med namnet "Date\_Boundaries"). Om samma fält finns i både det globala och testfallsspecifika datat, kommer det testfallsspecifika fältet att skriva över det globala värdet. Innehållet i filen ska ha format enligt:
```
<testsuite>
    <id>GetVaccinationHistory</id>
    <description>Beskrivning av testsuiten.</description>
    <globaldata>
        <webServiceUrl>http://localhost:8088/services</webServiceUrl>
    </globaldata>
    <testcase id="Date_Boundaries">
        <description>Beskrivning av testfallet</description>
        <data>
            <httpHeaderHsaId>112233</httpHeaderHsaId>
            <logicalAddress>112233</logicalAddress>
        </data>
    </testcase>
    <testcase id="CareUnitHsaId_Filter">
        <description>Beskrivning av testfallet</description>
        <data>
            <httpHeaderHsaId>112234</httpHeaderHsaId>
            <logicalAddress>112235</logicalAddress>
        </data>
    </testcase>
```
De element som anges inom elementen `globaldata` eller `data` kan sedan refereras antingen genom att ange t ex `${httpHeaderHsaId}` i ett webservice-request, eller `context.httpHeaderHsaId` i ett script-block.

### Schematron-validering ###
```
import se.skl.rivta.testsupport.soapui.validation.SchematronValidator
import se.skl.rivta.testsupport.soapui.ContextHelper
import com.eviware.soapui.support.GroovyUtils

def utils = new GroovyUtils(context)
def contextHelper = new ContextHelper(context)
def validator = new SchematronValidator()

def failures = validator.validateMessage(messageExchange.response.getResponseContent(),
	new File(utils.projectPath + "/constraints.xml"))

failures.each { failure -> contextHelper.fail(failure.text) }
```
### Validering av meddelandets innehåll ###
Detta script validerar att svaret endast innehåller samma PatientId och PatientIdType som skickades i Requesten.
```
import com.eviware.soapui.support.XmlHolder
import se.skl.rivta.testsupport.soapui.ContextHelper
import com.eviware.soapui.support.GroovyUtils

def utils = new GroovyUtils(context)
def contextHelper = new ContextHelper(context)

def holder = new XmlHolder( messageExchange.response.responseContent )
holder.namespaces['soapenv'] = 'http://schemas.xmlsoap.org/soap/envelope/'
holder.namespaces['urn'] = 'urn:riv:clinicalprocess:logistics:logistics:GetCareContactsResponder:2'
holder.namespaces['urn1'] = 'urn:riv:clinicalprocess:logistics:logistics:2'

def careContacts = holder.getDomNodes('/soapenv:Envelope/soapenv:Body/urn:GetCareContactsResponse/urn:careContact')

careContacts.each {c -> 
	def contactHolder = new XmlHolder( c )
	holder.namespaces.each {key, value -> contactHolder.namespaces[key] = value}

	def careContactId = contactHolder.getNodeValue('/urn:careContact/urn1:careContactHeader/urn1:careContactId')
	def patientId = contactHolder.getNodeValue('/urn:careContact/urn1:careContactHeader/urn1:patientId/urn1:id')
	def patientIdType = contactHolder.getNodeValue('/urn:careContact/urn1:careContactHeader/urn1:patientId/urn1:type')
	
	if (patientId != context.patientId) {
		contextHelper.fail("Response contains a contact with other PatientId. PatientId: ${patientId} CareContactId: ${careContactId}")
	}
	if (patientIdType != context.patientIdType ) {
		contextHelper.fail("Response contains a contact with other PatientIdType. PatientIdType: ${patientIdType} CareContactId: ${careContactId}")
	}
}
```
### Mock-script ###
#### Validering av inkommande Request ####
Vid utveckling av testsviterna är det smidigt att få hjälp med Schema-validering av de requests man skapar, använd då nedanstående script.
Vid valideringsfel kommer scriptet att försöka returnera ett svar som heter `SoapFault-Request`, med valideringsfelen i variabeln `validationMessages`.
```
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlContext
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlValidator
import com.eviware.soapui.impl.wsdl.panels.mockoperation.WsdlMockRequestMessageExchange

def mockService = mockOperation.mockService

def wsdlcontext = new WsdlContext("http://localhost:${mockService.port}/${mockService.path}?WSDL");
def validator = new WsdlValidator(wsdlcontext);
def msgExchange = new WsdlMockRequestMessageExchange(mockRequest, mockOperation); 
def errors = validator.assertRequest(msgExchange, false);

def msg = ""
if (errors.length > 0) {
	errors.each {
		log.error("Incoming request contains error ${it.toString()}")
		msg += "${it.toString()}\n"
	}
	context.validationMessages = msg
	return "SoapFault-Request"
}
```
#### Hämta värden från request ####
Detta script är ett exempel för hur mock-tjänsten hämtar värden från Request'en, som sedan blir tillgängliga att använda i svaret som returneras.
```
import com.eviware.soapui.support.XmlHolder

def holder = new XmlHolder( mockRequest.requestContent )
holder.namespaces['soapenv'] = 'http://schemas.xmlsoap.org/soap/envelope/'
holder.namespaces['urn'] = 'urn:riv:itintegration:registry:1'
holder.namespaces['urn1'] = 'urn:riv:clinicalprocess:logistics:logistics:GetCareContactsResponder:2'
holder.namespaces['urn2'] = 'urn:riv:clinicalprocess:logistics:logistics:2'

def careUnitHSAid = holder.getNodeValue('/soapenv:Envelope/soapenv:Body/urn1:GetCareContacts/urn1:careUnitHSAid')
def patientId = holder.getNodeValue('/soapenv:Envelope/soapenv:Body/urn1:GetCareContacts/urn1:patientId/urn2:id')
def patientIdType = holder.getNodeValue('/soapenv:Envelope/soapenv:Body/urn1:GetCareContacts/urn1:patientId/urn2:type')

context.patientId = patientId
context.patientIdType = patientIdType
context.careUnitHSAid = careUnitHSAid

def responseName = mockRequest.requestHeaders['x-mock-response'][0]
log.debug("responseName: ${responseName}")

return responseName
```