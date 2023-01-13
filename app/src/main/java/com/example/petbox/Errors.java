package com.example.petbox;

public enum Errors {
    NON_EXISTENT_USER(1, "Такого пользователя не существует!"),
    SUCCESSFUL_AUTHORIZATION(2, "Вы успешно авторизовались!"),
    NON_SUCCESSFUL_AUTHORIZATION(3, "Авторизация не успешна!"),
    SUCCESSFUL_REGISTRATION(4, "Вы успешно зарегистрировались!"),
    NON_SUCCESSFUL_REGISTRATION(5, "Регистрация не успешна!"),
    EXISTENT_USER(6, "Такой пользователь уже существует!"),
    SOMETHING_IS_WRONG(7, "Что-то пошло не так!"),
    SUCCESSFUL_CREATION(8,"Успешно добавлено!"),
    SUCCESSFUL_EDIT(9,"Успешно отредактировано"),
    SUCCESSFUL_DELETE(10,"Успешно удалено!");


    private int code;
    private String message;

    Errors(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
