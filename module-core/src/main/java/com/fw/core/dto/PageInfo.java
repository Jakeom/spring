package com.fw.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * PageInfo
 * @author sjpaik
 */
@Getter
@Setter
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalCount;
    private int pageIndex;
    private int pageSize;
    private List<T> data;

}
