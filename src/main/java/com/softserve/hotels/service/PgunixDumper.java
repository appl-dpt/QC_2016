package com.softserve.hotels.service;

public class PgunixDumper implements PgDumper {

    @Override
    public void dump() {
        System.out.println("Unix");
    }

	@Override
	public void restore() {
		
	}

    @Override
    public void compress() {
        
    }

    @Override
    public void decompress() {
        
    }

    @Override
    public void fullBackup() {
        
    }

    @Override
    public void fullRestore() {
        
    }

}
