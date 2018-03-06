package com.watent.thread.mode.forkjoin.monkey;


import com.watent.thread.mode.forkjoin.service.IProcessPeach;
import com.watent.thread.mode.forkjoin.vo.Peach;

public class MonkeyProcessImpl implements IProcessPeach {

    @Override
    public void processPeach(Peach peach) {
        //看看桃子，放到口袋里
        inBag();
    }

    private void inBag() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
