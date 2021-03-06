package gov.ca.cwds.testapp.service;

import gov.ca.cwds.security.annotations.Authorize;
import gov.ca.cwds.testapp.domain.Case;
import gov.ca.cwds.testapp.domain.CaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry.rudenko on 10/6/2017.
 */
public class TestServiceImpl implements TestService {
  public void testArg( @Authorize("case:read:id") String id) {
    System.out.println();
  }

  @Override
  public void testCompositeObject(@Authorize("case:read:caseDTO.caseObject") CaseDTO caseDTO) {
    System.out.println();
  }

  @Override
  public void testCompositeObjectList(@Authorize("case:read:caseDTO.cases.id") CaseDTO caseDTO) {
    System.out.println();
  }

  @Override
  @Authorize("case:read:caseDTO.caseObject")
  public CaseDTO testReturnInstance() {
    Case caseObject = new Case(1L, "");
    CaseDTO caseDTO = new CaseDTO();
    caseDTO.setCaseObject(caseObject);
    return caseDTO;
  }

  @Override
  @Authorize("case:read:caseDTO.caseObject.id")
  public CaseDTO testReturnProtectedInstance() {
    Case caseObject = new Case(2L, "");
    CaseDTO caseDTO = new CaseDTO();
    caseDTO.setCaseObject(caseObject);
    return caseDTO;
  }

  @Override
  @Authorize("case:read:caseDTO.caseObject.id")
  public List<CaseDTO> testFilter() {
    List<CaseDTO> result = new ArrayList<>();
    CaseDTO caseDTO = new CaseDTO();
    caseDTO.setCaseObject(new Case(1L, "valid"));
    result.add(caseDTO);
    caseDTO = new CaseDTO();
    caseDTO.setCaseObject(new Case(2L, "name"));
    result.add(caseDTO);
    return result;
  }
}
