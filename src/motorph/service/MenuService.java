package motorph.service;

import java.util.List;
import motorph.model.core.Employee;

/**
 * Service class responsible for menu visibility rules.
 * Accepts menu name only, as instructed.
 */
public class MenuService {

    private Employee loggedInEmployee;

    public void setLoggedInEmployee(Employee employee) {
        this.loggedInEmployee = employee;
    }

    /**
     * Determines whether a menu item should be shown.
     */
    public boolean shouldShow(String menuName) {

        // Always show layout / UI-only items
        if (menuName == null || menuName.isEmpty()
                || "divider".equals(menuName)
                || "Log Out".equals(menuName)) {
            return true;
        }

        if (loggedInEmployee == null) {
            return false;
        }

        // Delegate permission decision to subclass (polymorphism)
        List<String> allowedMenus = loggedInEmployee.getMenuItems();

        return allowedMenus != null && allowedMenus.contains(menuName);
    }
}
