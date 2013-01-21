package cz.muni.fi.pa165.vozovna.editors;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.service.UserService;
import java.beans.PropertyEditorSupport;

/**
 * @author Eva Neduchalová, 359893
 */
public class UserEditor extends PropertyEditorSupport {

    private UserService userService;

    public UserEditor(UserService userService) {
        super();
        this.userService = userService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        UserDTO user = userService.getById((long) Integer.parseInt(text));
        setValue(user);
    }

    @Override
    public String getAsText() {
        UserDTO user = (UserDTO) getValue();
        if (user == null) {
            return null;
        } else {
            return user.getId().toString();
        }
    }
}
