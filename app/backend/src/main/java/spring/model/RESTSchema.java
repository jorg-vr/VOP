package spring.model;

import java.util.Collection;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Created by jorg on 3/13/17.
 */
public class RESTSchema<T> {
    private Collection<T> data;
    private Integer total;
    private Integer offset;
    private Integer limit;
    private String previous;
    private String next;
    private String first;
    private String last;

    public RESTSchema() {
    }

    public RESTSchema(Collection<T> data, Integer total, Integer page, Integer limit, String baseString) {
        this.data = data;
        this.total = total;
        this.offset = page*limit;
        this.limit = min(total,(page+1)*limit);
        this.previous = baseString + "page="+max(page-1,0)+"&limit="+limit;
        this.next = baseString + "page="+min(page+1,total/limit)+"&limit="+limit;
        this.first = baseString + "page="+0+"&limit="+limit;
        this.last = baseString + "page="+total/limit+"&limit="+limit;
    }

    public RESTSchema(Collection<T> data, Integer total, Integer offset, Integer limit, String previous, String next, String first, String last) {
        this.data = data;
        this.total = total;
        this.offset = offset;
        this.limit = limit;
        this.previous = previous;
        this.next = next;
        this.first = first;
        this.last = last;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
