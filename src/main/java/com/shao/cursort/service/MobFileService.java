package com.shao.cursort.service;

import com.shao.cursort.result.Result;

public interface MobFileService {

    public Result listFile(String folderId, long userId, int page , int limit ) ;

   // public Result

    public boolean createRoot(long userId);

}
