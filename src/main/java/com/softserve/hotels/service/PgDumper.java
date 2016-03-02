package com.softserve.hotels.service;

public interface PgDumper {
    
    public void dump();
    
    public void restore();
    
    public void compress();
    
    public void decompress();
    
    public void fullBackup();
    
    public void fullRestore();

}