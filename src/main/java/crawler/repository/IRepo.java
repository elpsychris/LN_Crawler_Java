package crawler.repository;

import org.hibernate.Criteria;

import java.util.List;

public interface IRepo<T> {
    void add(T item);
    void remove(T item);
    void update(T item);
    List<T> query(Criteria criteria);
}
