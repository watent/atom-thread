package com.watent.thread.mode.forkjoin.pig;

import com.watent.thread.mode.forkjoin.service.IPickPeach;
import com.watent.thread.mode.forkjoin.service.IProcessPeach;
import com.watent.thread.mode.forkjoin.vo.Color;
import com.watent.thread.mode.forkjoin.vo.Peach;
import com.watent.thread.mode.forkjoin.vo.Size;

public class PigPickImpl implements IPickPeach {

    private IProcessPeach processPeach;

    public PigPickImpl(IProcessPeach processPeach) {
        this.processPeach = processPeach;
    }

    @Override
    public boolean pick(Peach[] src, int index) {

        if (src[index].getColor() == Color.RED &&
                src[index].getSize() == Size.BIG) {

            processPeach.processPeach(src[index]);
            return true;
        } else {
            return false;
        }

    }
}
