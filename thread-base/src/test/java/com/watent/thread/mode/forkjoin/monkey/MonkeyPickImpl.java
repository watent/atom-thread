package com.watent.thread.mode.forkjoin.monkey;


import com.watent.thread.mode.forkjoin.service.IPickPeach;
import com.watent.thread.mode.forkjoin.service.IProcessPeach;
import com.watent.thread.mode.forkjoin.vo.Color;
import com.watent.thread.mode.forkjoin.vo.Peach;
import com.watent.thread.mode.forkjoin.vo.Size;

public class MonkeyPickImpl implements IPickPeach {

    private IProcessPeach processPeach;

    public MonkeyPickImpl(IProcessPeach processPeach) {
        this.processPeach = processPeach;
    }

    @Override
    public boolean pick(Peach[] src, int index) {

        if (src[index].getColor() == Color.RED &&
                src[index].getSize() == Size.BIG &&
                src[index].getYear() >= 6000) {
            
            processPeach.processPeach(src[index]);
            return true;
        } else {
            return false;
        }
    }
}
