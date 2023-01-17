package com.kodilla.prices.view;

import com.kodilla.prices.external.prices.UserDto;
import com.kodilla.prices.external.prices.UserService;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.textfield.TextField;




@Route("addUser")
public class AddUser extends FormLayout {
    private final transient UserService userService;
    private transient UserDto userDto = new UserDto();

    @PropertyId("name")
    private final TextField name = new TextField("Name");
    @PropertyId("lastName")
    private final TextField lastName = new TextField("Last Name");
    @PropertyId("mail")
    private final TextField mail = new TextField("mail");
    @PropertyId("login")
    private final TextField login = new TextField("login");
    @PropertyId("password")
    private final TextField password = new TextField("password");

    private final Button create = new Button("Create");
    private final Binder<UserDto> binder = new Binder<>(UserDto.class);

    public AddUser(UserService userService){
        this.userService = userService;
        HorizontalLayout fields = new HorizontalLayout(name, lastName, mail, login, password);
        HorizontalLayout buttons = new HorizontalLayout(create);
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        create.addClickListener(event -> create());

        binder.bindInstanceFields(this);
        add(fields,buttons);

    }
    private void create(){
        try{
            binder.writeBean(userDto);
            userService.addOrUpdateUser(userDto.toDomain());
            create.getUI().flatMap(ui -> ui.navigate(OfferList.class));
        }catch(ValidationException e){
            throw new RuntimeException(e);
        }
    }

}
