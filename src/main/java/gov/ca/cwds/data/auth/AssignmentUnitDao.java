package gov.ca.cwds.data.auth;


import gov.ca.cwds.data.persistence.auth.AssignmentUnit;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * DAO for {@link AssignmentUnit}.
 *
 * @author CWDS API Team
 */
@Transactional
@Repository
public interface AssignmentUnitDao extends ReadOnlyRepository<AssignmentUnit, String> {

}
