package edu.neu.ccis.sms.dao.categories;

import java.util.List;

import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;

/**
 * DAO interface for UserToMemberMapping Entity bean
 * 
 * @author Pramod R. Khare
 * @date 9-May-2015
 * @lastUpdate 7-June-2015
 */
public interface UserToMemberMappingDao {
    // Member
    public List<UserToMemberMapping> getAllUserToMemberMappings();

    public UserToMemberMapping getUserToMemberMapping(Long id);

    public void updateUserToMemberMapping(UserToMemberMapping modifiedUserToMemberMapping);

    public void deleteUserToMemberMapping(UserToMemberMapping userToMemberMapping);

    public void saveUserToMemberMapping(UserToMemberMapping newUserToMemberMapping);

	public List<UserToMemberMapping> getAllMembersForUser(Long userId);
}
