package com.jel.tech.common.memoize;

/**
 * 需要消耗时间计算的任务
 * @date 2017年8月16日
 */
public interface Computable<A,V> {

	V compute(A arg) throws InterruptedException;
}
