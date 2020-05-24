package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.User;
import us.lacchain.crossborder.management.model.UserView;
import us.lacchain.crossborder.management.clients.request.AddUserRequest;
import us.lacchain.crossborder.management.exception.UserExistsException;
import java.util.List;

public interface IUserService {

    boolean insert(AddUserRequest request)throws UserExistsException,Exception;
    UserView getUser(String dltAddress);

}