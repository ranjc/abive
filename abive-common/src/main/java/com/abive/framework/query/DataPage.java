package com.abive.framework.query;

/**
 * <h2>数据分页封装</h2>
 *
 * <pre>
 *  数据库物理分页分两步，第一步：获取目标数据的总条数，第二步：根据指定分页参数获取数据
 *  {@code
 *          ......
 *          // 获取目标数据总条数
 *          int count = dao.countForObject(organization);
 *          // 根据count,index,pageSize,构造DataPage<T>对象
 *          DataPage<Organization> dataPage = new DataPage<Organization>(count, index, pageSize);
 *          // 根据dataPage计算得到物理数据分页起始行
 *          organization.setStart_(dataPage.getStartRecord());
 *          // 查询数据条数
 *          organization.setRows_(dataPage.getPageSize());
 *          // 将查询到数据放入dataPage 对象
 *          dataPage.addAll( dao.listForObject(organization.getPmpOrganizationDomain()) );
 *          ......
 * }
 *  服务层统一返回 DataPage<T> 对象
 * </pre>
 * @param <E>
 */

import java.util.ArrayList;
import java.util.List;

public final class DataPage<E> extends ArrayList<E> {
    private static final long serialVersionUID  = 1L;
    public final static int   DEFAULT_PAGE      = 1;
    public static final int   DEFAULT_PAGE_SIZE = 10;  // 默认分页条数
    public final static int   MAX_PAGE_SIZE     = 2000;
    private int               totalRecord;
    private int               totalPage;
    /** 默认每页记录数 */
    private int               pageSize          = 20;
    private int               pageIndex;

    /**
     * @param totalRecord 总记录数
     * @param pageIndex 当前页
     * @param pageSize 每页记录数
     */
    public DataPage(Integer totalRecord, Integer pageIndex, Integer pageSize) {
        this.totalRecord = null == totalRecord ? 0 : totalRecord;
        this.pageIndex = null == pageIndex || pageIndex < 1 ? 1 : pageIndex;
        if (null != pageSize && 0 < pageSize) {
            this.pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        }
        int remainder = this.totalRecord % this.pageSize;
        int totalPage = this.totalRecord / this.pageSize;
        this.totalPage = remainder == 0 ? totalPage : totalPage + 1;
    }

    /**
     * @param totalRecord 总记录数
     * @param pageIndex 当前页
     */
    public DataPage(Integer totalRecord, Integer pageIndex) {
        this(totalRecord, pageIndex, null);
    }

    /**
     * 查询起始行
     * @deprecated {@link #getOffset()}
     * @return 起始行
     */
    public int getStartRecord() {
        return (this.pageIndex - 1) * pageSize;
    }

    public int getOffset() {
        return (this.pageIndex - 1) * pageSize;
    }

    /**
     * 填充数据
     * @param list
     */
    public void addAll(List<E> list) {
        super.addAll(list);
    }

    /**
     * 是否还有上一页
     * @deprecated {@link #hasPreviousPage()}
     * @return
     */
    public boolean hasForwardPage() {
        return pageIndex > 1;
    }

    /**
     * 是否还有上一页
     * @return
     */
    public boolean hasPreviousPage() {
        return pageIndex > 1;
    }

    /**
     * 是否还有下一页
     * @return
     */
    public boolean hasNextPage() {
        return pageIndex < totalPage;
    }

    /**
     * 获取下一页
     * @return
     */
    public int getNextPage() {
        int nextPage = this.pageIndex + 1;
        return nextPage > this.totalPage ? this.totalPage : nextPage;
    }

    /**
     * 获取前一页
     * @deprecated {@link #getPreviousPage()}
     * @return
     */
    public int getForwardPage() {
        int forwardPage = this.pageIndex - 1;
        return forwardPage <= 0 ? 1 : forwardPage;
    }

    /**
     * 获取前一页
     * @return
     */
    public int getPreviousPage() {
        int forwardPage = this.pageIndex - 1;
        return forwardPage <= 0 ? 1 : forwardPage;
    }

    /**
     * 总页数
     * @return
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 每页记录数
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取总记录数
     * @return
     */
    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 输出JSON格式
     *
     * <pre>
     *      <code>E</code>必须实现toString()方法返回JSON数据
     * </pre>
     * @return
     */
    public String toJson() {
        return "{" + "\"nextPage\":" + "\"" + this.getNextPage() + "\"," + "\"size\":" + "\"" + this.size() + "\","
                + "\"totalPage\":" + "\"" + this.totalPage + "\"," + "\"totalRecord\":" + "\"" + this.totalRecord
                + "\"," + "\"pageIndex\":" + "\"" + this.pageIndex + "\"," + "\"pageSize\":" + "\"" + this.pageSize
                + "\"" + "}";
    }

    public String toString() {
        return "{" + "\"nextPage\":" + "\"" + this.getNextPage() + "\"," + "\"size\":" + "\"" + this.size() + "\","
                + "\"totalPage\":" + "\"" + this.totalPage + "\"," + "\"totalRecord\":" + "\"" + this.totalRecord
                + "\"," + "\"pageIndex\":" + "\"" + this.pageIndex + "\"," + "\"pageSize\":" + "\"" + this.pageSize
                + "\"" + "}";
    }
}
