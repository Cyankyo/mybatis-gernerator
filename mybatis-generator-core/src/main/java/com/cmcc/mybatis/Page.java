/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.cmcc.mybatis;

import java.io.Serializable;

/**
 * 分页对象.
 *
 */public final class Page implements Serializable {

    /**
     * 默认的序列化版本 id.
     */
    private static final long serialVersionUID = 1L;
    /**
     * 分页查询开始记录位置.
     */
    private int start;
    /**
     * 分页查看下结束位置.
     */
    private int               end;
    /**
     * 每页显示记录数.
     */
    private int count = 20;
    /**
     * 查询结果总记录数.
     */
    private long               totalRecords;
    /**
     * 当前页码.
     */
    private int               pageNo;
    /**
     * 总共页数.
     */
    private int               pageCount;    public Page() {
    }

    /**
     * 构造函数.
     *
     * @param begin
     * @param length
     */
    public Page(int begin, int length) {
        this.start = begin;
        this.count = length;
        this.end = this.start + this.count;
        this.pageNo = (int) Math.floor((this.start * 1.0d) / this.count) + 1;
    }

    /**
     * @param begin
     * @param length
     */
    public Page(int begin, int length, int totalRecords) {
        this(begin, length);
        this.totalRecords = totalRecords;
    }

    /**
     * 设置页数，自动计算数据范围.
     *
     * @param pageNo
     */
    public Page(int pageNo) {
        this.pageNo = pageNo;
        pageNo = pageNo > 0 ? pageNo : 1;
        this.start = this.count * (pageNo - 1);
        this.end = this.count * pageNo;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }
    /**
     * @param end
     * the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * @param start
     * the start to set
     */
    public void setStart(int start) {
        this.start = start;
        if (this.count != 0) {
            this.pageNo = (int) Math.floor((this.start * 1.0d) / this.count) + 1;
        }
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count
     * the count to set
     */
    public void setCount(int count) {
        this.count = count;
        if (this.start != 0) {
            this.pageNo = (int) Math.floor((this.start * 1.0d) / this.count) + 1;
        }
    }

    /**
     * @return the totalRecords
     */
    public long getTotalRecords() {
        return totalRecords;
    }

    /**
     * @param totalRecords
     * the totalRecords to set
     */
    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
        this.pageCount = (int) Math.floor((this.totalRecords * 1.0d) / this.count);
        if (this.totalRecords % this.count != 0) {
            this.pageCount++;
        }
    }

    /**
     * @return the pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo
     * the pageNo to set
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        pageNo = pageNo > 0 ? pageNo : 1;
        this.start = this.count * (pageNo - 1);
        this.end = this.count * pageNo;
    }

    /**
     * @return the pageCount
     */
    public int getPageCount() {
        if (pageCount == 0) {
            return 1;
        }
        return pageCount;
    }

    /**
     * @param pageCount
     * the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("start=").append(start).append(", end=")
                .append(end).append(", count=").append(count).append(", totalRecords=").append(
                        totalRecords).append(", pageNo=").append(pageNo).append(", pageCount=")
                .append(pageCount);        return builder.toString();
    }
}