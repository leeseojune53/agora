package com.example.agora.Redis;

public interface RedisUtilService {
    public String getData(String key);
//    public void setData(String key, String value);
    public void setDataExpire(String key, String value, long exp);
//    public void deleteData(String key);
}
