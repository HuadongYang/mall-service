package com.bishe.db.utils;

import com.bishe.db.domain.Command;
import com.bishe.db.enums.CommandEnum;
import io.netty.util.internal.StringUtil;

import java.util.Date;


public class CommandAnalysisUtil {
    private static final String split = "%%";

    public Command analysis(String request) {
        if (!checkRequest(request)) {
            return null;
        }

        String[] requestArr = request.split(split);
        Command command = new Command();
        command.setCommand(CommandEnum.getCommandEnumByCode(requestArr[0]));
        command.setKey(requestArr[1]);
        if (requestArr.length > 2){
            command.setValue(requestArr[2]);
        }
        if (requestArr.length > 3 && requestArr[3] != null){
            command.setExpireTime(new Date(Long.valueOf(requestArr[3])));
        }
        return  command;
    }

    private boolean checkRequest(String request){
        if (StringUtil.isNullOrEmpty(request)) {
            System.out.println("request is null");
            return false;
        }
        return true;
    }
}
