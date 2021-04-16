package com.bishe.db.enums;

public enum CommandEnum {
    SET("set"),
    GET("get");

    private String code;
    private CommandEnum(String code) {
        this.code = code;
    }

    private String getCode(){
        return code;
    }

    public static CommandEnum getCommandEnumByCode(String code){
        for(CommandEnum commandEnum : CommandEnum.values()){
            if (commandEnum.getCode().equals(code)){
                return commandEnum;
            }
        }
        return null;
    }

}
