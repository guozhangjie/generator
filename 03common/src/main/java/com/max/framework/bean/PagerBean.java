package com.max.framework.bean;

import java.io.Serializable;

/**
 * 分页BEAN.
 * @author 马青松
 * @since 1.0
 */
public class PagerBean implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 页码.
     */
    private int pageNum;
    /**
     * 一页显示记录数.
     */
    private int pageSize;
    /**
     * 总记录条数.
     */
    private long total;
    /**
     * 总页数.
     */
    private int pages;

    /**
     * 默认构造器(使用默认页码大小)
     */
    public PagerBean() {
        // this.pageSize =
        // StringUtil.convertStringtoInt(ResourceUtil.getResourceInfo("system.perPageCount"));
    }

    /**
     * 构造器.
     * @param pageNum 页码
     * @param pageSize 一页显示记录数
     */
    public PagerBean(final int pageNum, final int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;

        if (this.pageNum < 1) {
            this.pageNum = 1;
        }
    }

    /**
     * 设置 total.
     * @param total the total to set
     */
    public final void setTotal(final long total) {
        this.total = total;
        this.pages = (int) this.total / this.pageSize;
        if (this.total % this.pageSize != 0) {
            this.pages++;
        }
        if (this.pageNum > this.pages) {
            this.pageNum = this.pages;
        }
        if (this.pageNum < 1) {
            this.pageNum = 1;
        }

    }

    /**
     * 获得开始记录索引号.
     * @return the startRow
     */
    public final int getStartRow() {
        // mssql
        // return pageNum > 0 ? (pageNum - 1) * pageSize + 1 : 1;
        // mysql
        return pageNum > 0 ? (pageNum - 1) * pageSize : 0;
    }

    /**
     * 获得最后记录索引号.
     * @return the endRow
     */
    public final int getEndRow() {
        // mssql
        // return this.pageNum * pageSize;
        // mysql
        return this.pageNum * pageSize - 1;
    }

    /**
     * 设置开始记录索引号.
     * @param startRow 开始记录索引号.
     */
    public final void setStartRow(int startRow) {
    }

    /**
     * 设置最后记录索引号.
     * @param endrow 设置最后记录索引号.
     */
    public final void setEndRow(int endrow) {
    }

    /**
     * 获得查询页码.
     * @return the pageNum
     */
    public final int getPageNum() {
        return pageNum;
    }

    /**
     * 设置查询页码.
     * @param pageNum the pageNum to set
     */
    public final void setPageNum(final int pageNum) {
        if (pageNum < 1) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

    /**
     * 获得一页显示记录数.
     * @return the pageSize
     */
    public final int getPageSize() {
        return pageSize;
    }

    /**
     * 设置一页显示记录数.
     * @param pageSize the pageSize to set
     */
    public final void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获得总记录数.
     * @return the total
     */
    public final long getTotal() {
        return total;
    }

    /**
     * 获得总页数.
     * @return the pages
     */
    public final int getPages() {
        return pages;
    }

    /**
     * 设置总页数.
     * @param pages 设置总页数.
     */
    public final void setPages(int pages) {
    }
}
