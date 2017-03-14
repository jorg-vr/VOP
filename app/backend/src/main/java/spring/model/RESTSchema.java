package spring.model;

import model.history.EditableObject;
import spring.Exceptions.InvalidInputException;

import java.util.*;

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

    /**
     * Creates a pagination object for the collection
     * <p>
     * if either page or limit is null:
     * 1) the data field of pagination will be set to a copy of the collection
     * 2) pagination.data will be equal to the size of the collection
     * 3) all the other fields will be null
     * <p>
     * if both page and limit are not null:
     * 1) pagination.data will contain the sublist [page*limit, page*limit + limit[.
     * This is a copy of the collection.
     * The collection will be sorted based on the UUID of the objects t
     * 2) pagination.previous/pagination.next will be null if there is no previous/next page
     * 2) All other attributes of pagination will contain valid values
     * <p>
     * The data will be sorted based on the hashcode
     *
     * @param collection the full collection that has to be paginated
     * @param page       if null data of the pagination will be set to collection
     * @param limit      if null data of the pastination will be set to collection
     * @return
     */
    public RESTSchema(Collection<T> collection, Integer page, Integer limit, String baseString) {
        this.data = collection;
        List<T> list = new ArrayList<>(collection);
        this.setTotal(list.size());

        // queries are 0 => give back the full collection
        if (page == null || limit == null) {
            setData(list);
            return;
        }

        // TODO sort
        Collections.sort(list, (a, b) -> a.hashCode() - b.hashCode());
        int start = page * limit;
        int end = min(start + limit, this.total);

        // Deals with empty collections
        int listSize = list.size();
        if (listSize == 0) {
            listSize += 1;
        }

        if (start < 0 || start >= listSize || page < 0 || limit <= 0) {
            throw new InvalidInputException();
        }

        this.data = new ArrayList<>(list.subList(start, end));

        this.limit = limit;
        this.offset = page * limit;

        this.first = makeLink(baseString, 0, limit);
        int lastPage = ((listSize - 1) / limit);
        this.last = makeLink(baseString, lastPage, limit);

        if (page > 0) {
            this.previous = makeLink(baseString, page - 1, limit);
        }
        if (page < lastPage) {
            this.next = makeLink(baseString, page + 1, limit);
        }
    }

    /**
     * Appends page and limit to the query. A ? will be appended to path if required
     *
     * @param path  the query so far
     * @param page  the page number, should not be null
     * @param limit the limit, should not be null
     * @return the path where limit and page have been appended to
     */
    private String makeLink(String path, Integer page, Integer limit) {
        if (path.charAt(path.length() - 1) != '?') {
            path += "?";
        }
        return path + "page=" + page + "&limit=" + limit;
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
