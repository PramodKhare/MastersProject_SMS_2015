package edu.neu.ccis.sms.dao.categories;

import java.util.List;

import edu.neu.ccis.sms.entity.categories.UserToMemberMapping;

public interface UserToMemberMappingDao {
    // Member
    public List<UserToMemberMapping> getAllUserToMemberMappings();

    public UserToMemberMapping getUserToMemberMapping(Long id);

    public void updateUserToMemberMapping(UserToMemberMapping modifiedUserToMemberMapping);

    public void deleteUserToMemberMapping(UserToMemberMapping userToMemberMapping);

    public void saveUserToMemberMapping(UserToMemberMapping newUserToMemberMapping);

	public List<UserToMemberMapping> getAllMembersForUser(Long userId);
}
