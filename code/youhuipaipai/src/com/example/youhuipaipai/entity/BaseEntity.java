package com.example.youhuipaipai.entity;

import java.util.List;

/**
 * @author lvning
 * @version create time:2014-4-12_下午11:39:33
 * @Description TODO
 */
public  class BaseEntity<T> {
	public int result;
	public String info;
	public List<T> data;
}
