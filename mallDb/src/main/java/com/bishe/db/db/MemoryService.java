package com.bishe.db.db;


import com.bishe.db.domain.MemoryDb;


import java.util.*;



public class MemoryService {

    private static final int cacheSize = 1000;
    private static HashMap<String, MemoryDb> dataMap1 = new HashMap<>();
    private static LinkedHashMap<String, MemoryDb> dataMap = new LinkedHashMap<String, MemoryDb>(5,0.75F ,true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, MemoryDb> var){
            if(cacheSize <= dataMap.size()){
                return true;
            }
            return false;
        }
    };

    private static Set<MemoryDb> hotData = new HashSet<>();
    private static final int hotDataSize = 5;


    public void save(String key, MemoryDb memoryDb) {
        dataMap.put(key, memoryDb);
    }


    public String get(String key) {
        if (!dataMap.containsKey(key)) {
            return null;
        }
        if (dataMap.get(key).getFailureTime() != null && dataMap.get(key).getFailureTime().before(new Date())) {
            dataMap.remove(key);
            return null;
        }
        if (dataMap.get(key) != null) {
            if (dataMap.get(key).getCount() == null) {
                dataMap.get(key).setCount(1);
            }else {
                dataMap.get(key).setCount(dataMap.get(key).getCount() + 1);
            }
        }

        return dataMap.get(key).getValue();
    }

    private void putInHots(MemoryDb memoryDb){
        if (hotData.size() < hotDataSize){
            hotData.add(memoryDb);
            return;
        }
        MemoryDb minCountData = hotData.iterator().next();

        for (MemoryDb data : hotData) {
            if (data.getCount() < minCountData.getCount()) {
                minCountData = data;
            }
        }
        if (minCountData.getCount()>= memoryDb.getCount()) {
            return;
        }
        hotData.remove(minCountData);
        hotData.add(memoryDb);


    }


//    public Map<String, String> getHotData(int size){
//        hotData
//    }

}
