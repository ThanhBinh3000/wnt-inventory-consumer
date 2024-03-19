package vn.com.gsoft.inventory.service;

import vn.com.gsoft.inventory.model.system.Profile;

public interface BaseService {
    Profile getLoggedUser() throws Exception;

}
