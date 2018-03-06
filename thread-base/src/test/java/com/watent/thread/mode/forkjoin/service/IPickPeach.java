package com.watent.thread.mode.forkjoin.service;

import com.watent.thread.mode.forkjoin.vo.Peach;

/**
 * 摘桃
 */
public interface IPickPeach {

    boolean pick(Peach[] src, int index);
}
