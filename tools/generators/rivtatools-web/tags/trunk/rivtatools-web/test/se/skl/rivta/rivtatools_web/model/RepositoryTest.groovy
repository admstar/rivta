package se.skl.rivta.rivtatools_web.model;

import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.Repository;
import se.skl.rivta.rivtatools_web.model.RivtaProfileEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;
import groovy.util.GroovyTestCase;

class RepositoryTest extends GroovyTestCase {
	
	public void testAddServiceInteraction() {
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "riv",  "clinicalprocess", "activityprescription", "actoutcome", 1, 1, "GetMedicationHistory", "Interaction to transfer a new report for sickness","LogicalAddress is the source system to request.",false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP21);
		ServiceInteraction si = rep.getServiceInteraction("GetMedicationHistory","riv", "clinicalprocess", "activityprescription", "actoutcome", 1, 1)
		assertNotNull(si)
	}
	
	public void testGetServiceInteraction_NotFound()  {
		Repository rep = new Repository();
		ServiceInteraction si = rep.getServiceInteraction("GetMedicationHistory","riv", "clinicalprocess", "activityprescription", "actoutcome", 1, 0)
		assertNull(si)
	}
	
	public void testAddServiceInteraction_Duplicate() {
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "riv", "clinicalprocess", "activityprescription", "actoutcome", 1, 1, "GetMedicationHistory", "Interaction to get information about a patients pharmacy","LogicalAddress is the source system to request.",false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP21);
		try {
			rep.addServiceInteraction("Fake Org", "riv", "clinicalprocess", "activityprescription", "actoutcome", 1, 1, "GetMedicationHistory", "Interaction to get information about a patients pharmacy","LogicalAddress is the source system to request.",false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP21);
			fail("Should have raised runtime exception due to duplicate add")
		} catch (RuntimeException e) {
			
		}
	}	

}
