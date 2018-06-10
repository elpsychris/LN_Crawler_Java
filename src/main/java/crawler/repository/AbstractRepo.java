package crawler.repository;

import org.hibernate.Criteria;

import java.util.List;

public class AbstractRepo<T> implements IRepo<T> {
    @Override
    public void add(T item) {

    }

    @Override
    public void remove(T item) {

    }

    @Override
    public void remove(Criteria criteria) {

    }

    @Override
    public void update(Object item) {

    }

    @Override
    public List query(Criteria criteria) {
        return null;
    }
}
