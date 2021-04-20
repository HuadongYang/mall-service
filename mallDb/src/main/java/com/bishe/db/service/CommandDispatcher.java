package com.bishe.db.service;

import com.bishe.db.db.MemoryService;
import com.bishe.db.domain.Command;
import com.bishe.db.domain.MemoryDb;
import com.bishe.db.enums.CommandEnum;
import com.bishe.db.utils.CommandAnalysisUtil;

public class CommandDispatcher {

    private static CommandAnalysisUtil commandAnalysisUtil = new CommandAnalysisUtil();
    private static MemoryService memoryService = new MemoryService();

    public String dispatcher(String request){
        Command command = commandAnalysisUtil.analysis(request);
        if (command == null){
            return null;
        }
        if (command.getCommand().equals(CommandEnum.GET)) {
            String value = memoryService.get(command.getKey());

            return command.getKey() + "%%" + memoryService.get(command.getKey());
        } else {
            memoryService.save(command.getKey(), new MemoryDb(command.getKey(), command.getValue(), command.getExpireTime()));
        }
        return null;
    }


}
