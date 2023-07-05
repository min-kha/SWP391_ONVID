package group5.swp391.onlinelearning.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class ThymeleafBaseCRUD {

    /**
     * Sets the base information for displaying, creating or updating a specific
     * entity in CRUD.
     *
     * @param model  The Spring Model object to pass data from the controller to the
     *               view.
     * @param title  The title of the web page.
     * @param object The specific object (entity) to be displayed or updated.
     */
    public void setBaseForEntity(Model model, Object object, String title) {
        model.addAttribute("entity", object);
        model.addAttribute("title", title);
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        model.addAttribute("fields", fields);
    }

    /**
     * Sets the base information for displaying a list of objects using Thymeleaf in
     * CRUD.
     *
     * @param model The Spring Model object to pass data from the controller to the
     *              view.
     * @param list  The list of objects (entities) to be displayed.
     * @param title The title of the web page.
     */
    public void setBaseForList(Model model, List<? extends Object> list, String title) {
        model.addAttribute("title", title);
        model.addAttribute("entities", list);
        List<Field> fields = Arrays.asList(list.get(0).getClass().getDeclaredFields());
        model.addAttribute("fields", fields);
    }
}
