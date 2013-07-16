
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Page.java
 * <p>
 * 2012-7-31 下午5:21:32
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    // -- 分页参数 --//
    protected int             pageNo           = 1;
    protected int             pageSize         = 20;

    private final int         maxpage          = 1000;

    // -- 返回结果 --//
    protected List<T>         result           = new ArrayList<T>();
    protected Integer         totalCount       = -1;

    private String            pageUrl;

    // -- 构造函数 --//
    public Page(){}

    public Page(int pageNo, int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Page(int pageSize){
        this.pageSize = pageSize;
    }

    // -- 分页参数访问函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final Integer pageNo) {
        if (pageNo == null || pageNo < 1) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }

    }

    public int getStartPage() {

        int startPage;
        if (getTotalCount() <= maxpage) {

            startPage = 1;
            return startPage;
        }
        if (pageNo + 5 <= maxpage) {
            startPage = 1;
            return startPage;
        }

        if (pageNo + 5 >= getTotalPages()) {
            startPage = (int) getTotalPages() - maxpage + 1;
            return startPage;
        }

        startPage = pageNo + 5 - maxpage + 1;

        return startPage;
    }

    public int getEndPage() {
        int endPage;
        if (getTotalPages() <= maxpage) {

            endPage = (int) getTotalPages();
            return endPage;
        }
        if (pageNo + 5 <= maxpage) {
            endPage = maxpage;
            return endPage;
        }

        if (pageNo + 5 >= getTotalPages()) {
            endPage = (int) getTotalPages();
            return endPage;
        }

        endPage = pageNo + 5;

        return endPage;
    }

    /**
     * 返回Page对象自身的setPageNo函数,可用于连续设置。
     */
    public Page<T> pageNo(final int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    /**
     * 获得每页的记录数量, 默认为-1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 返回Page对象自身的setPageSize函数,可用于连续设置。
     */
    public Page<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始. 用于Mysql
     */
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }

    // -- 访问查询结果函数 --//

    /**
     * 获得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final Integer totalCount) {
        this.totalCount = totalCount;
        long totalPages = getTotalPages();
        if (totalPages < pageNo) {
            pageNo = new Long(totalPages).intValue();
        }
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public Integer getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }

        Integer count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public Integer getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public Integer getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 计算以当前页为中心的页面列表,如"首页,23,24,25,26,27,末页"
     * 
     * @param count 需要计算的列表大小
     * @return pageNo列表
     */
    public List<Integer> getSlider(Integer count) {
        int halfSize = count / 2;
        Integer totalPage = getTotalPages();

        Integer startPageNumber = Math.max(pageNo - halfSize, 1);
        Integer endPageNumber = Math.min(startPageNumber + count, totalPage);

        if (endPageNumber - startPageNumber < count) {
            startPageNumber = Math.max(endPageNumber - count, 1);
        }

        List<Integer> result = new ArrayList<Integer>();
        for (Integer i = startPageNumber; i <= endPageNumber; i++) {
            result.add(i);
        }
        return result;
    }

}
