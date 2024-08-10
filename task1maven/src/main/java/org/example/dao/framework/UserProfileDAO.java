package org.example.dao.framework;

import org.example.entity.UserProfile;
import org.example.model.UserError;
import org.example.model.UserModel;

import java.util.List;

public interface UserProfileDAO {

    List<UserProfile> getAllUserProfiles();
    UserProfile getUserProfileByNationalId(String nationalId);
    UserProfile getUserProfileById(int userId);

    UserError addUserProfile(UserProfile userProfile);

    UserError updateUserProfile(UserProfile userProfile);

    UserError deleteUserProfile(int userId);

    List<UserModel> getTopFiveSalaries();

}
