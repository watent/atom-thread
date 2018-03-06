package com.watent.thread.mode.forkjoin.pig;

import com.watent.thread.base.util.SleepUtils;
import com.watent.thread.mode.forkjoin.service.IProcessPeach;
import com.watent.thread.mode.forkjoin.vo.Peach;

public class PicProcessImpl implements IProcessPeach {

    @Override
    public void processPeach(Peach peach) {
        eat();
    }
    // //看看桃子，一口吃了
    private void eat() {
        SleepUtils.second(1);
    }
}