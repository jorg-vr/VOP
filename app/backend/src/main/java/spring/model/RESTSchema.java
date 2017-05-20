package spring.model;

import util.URLUtil;
import spring.exceptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.lang.Integer.min;

/**
 * This represents a pagination object of generic type T
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
     * 1) the data field of the schema will be set to a copy of the collection
     * 2) schema.data will be equal to the size of the collection
     * 3) all the other fields will be null
     * <p>
     * if both page and limit are not null:
     * 1) schema.data will contain the sublist [page*limit, page*limit + limit[.
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
     * @param request    request of the HTTP request
     * @param comparator specifies how the elements should be sorted
     */
    public RESTSchema(Collection<T> collection, Integer page, Integer limit, HttpServletRequest request, Comparator<T> comparator) {
        this.data = collection;
        List<T> list = new ArrayList<>(collection);
        Collections.sort(list, comparator);
        this.setTotal(list.size());

        // queries are null => give back the full collection
        if (page == null || limit == null) {
            setData(list);
            return;
        }

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

        String relativeURL = URLUtil.getRelativeURL(request);

        this.first = URLUtil.replace(relativeURL, "page", 0);
        int lastPage = ((listSize - 1) / limit);
        this.last = URLUtil.replace(relativeURL, "page", lastPage);

        if (page > 0) {
            this.previous = URLUtil.replace(relativeURL, "page", page - 1);
        }
        if (page < lastPage) {
            this.next = URLUtil.replace(relativeURL, "page", page + 1);
        }
    }

    /**
     * See the constructor above for more information.
     * The elements will be sorted based on the hashcode to enforce the order of the elements.
     */
    public RESTSchema(Collection<T> collection, Integer page, Integer limit, HttpServletRequest request) {
       this(collection, page, limit, request, (a, b) -> a.hashCode() - b.hashCode());
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
