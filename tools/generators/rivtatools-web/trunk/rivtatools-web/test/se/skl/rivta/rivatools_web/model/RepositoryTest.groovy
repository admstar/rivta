package se.skl.rivta.rivatools_web.model;

import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.Repository;
import se.skl.rivta.rivtatools_web.model.RivtaProfileEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;
import groovy.util.GroovyTestCase;

class RepositoryTest extends GroovyTestCase {
	
	public void testAddServiceInteraction() {
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "riv",  "insuranceprocess", "healthreporting", 1, 1, "RegisterMedicalCertificate", "Interaction to transfer a new report for sickness","LogicalAddress is the organization id for F�rs�kringskassan.",false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP20);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 1)
		assertNotNull(si)
	}
	
	public void testGetServiceInteraction_NotFound()  {
		Repository rep = new Repository();
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		assertNull(si)
	}
	
	public void testAddServiceInteraction_Duplicate() {
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "riv", "insuranceprocess", "healthreporting", 1, 1, "RegisterMedicalCertificate", "Interaction to transfer a new report for sickness","LogicalAddress is the organization id for F�rs�kringskassan.",false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP20);
		try {
			rep.addServiceInteraction("Fake Org", "riv", "insuranceprocess", "healthreporting", 1, 1, "RegisterMedicalCertificate", "Interaction to transfer a new report for sickness", false, MEPEnum.InOut, RivtaProfileEnum.RIVTABP20);
			fail("Should have raised runtime exception due to duplicate add")
		} catch (RuntimeException e) {
			
		}
	}	

}
