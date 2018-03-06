package com.watent.thread.principle.future.mode;

public class Client {

    public Data request(final String queryStr) {

        final FutureData futureData = new FutureData();

        new Thread(() -> {
            RealData realData = new RealData(queryStr);
            futureData.setRealData(realData);
        }).start();

        return futureData;
    }


}
