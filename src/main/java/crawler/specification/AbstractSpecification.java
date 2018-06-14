//package crawler.specification;
//
//import org.hibernate.Criteria;
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Restrictions;
//
//public abstract class AbstractSpecification implements Specifiable {
//
//    public abstract Criterion toCriterion();
//
//    public Specifiable and(final Specifiable specifiable) {
//        return new AndSpecification(this, specifiable);
//    }
//
//    @Override
//    public Specifiable or(Specifiable specification) {
//        return new OrSpecification(this,specification);
//    }
//
//    @Override
//    public Specifiable not(Specifiable specification) {
//        return new NotSpecification(this);
//    }
//
//    @Override
//    public Criteria toCriteria(Criteria criteria) {
//        return criteria.add(toCriterion());
//    }
//
//    private class AndSpecification extends AbstractSpecification {
//        private Specifiable spec1;
//        private Specifiable spec2;
//
//        public AndSpecification(final Specifiable spec1, final Specifiable spec2) {
//            this.spec1 = spec1;
//            this.spec2 = spec2;
//        }
//
//        @Override
//        public Criterion toCriterion() {
//            return Restrictions.and(this.spec1.toCriterion(), this.spec2.toCriterion());
//        }
//    }
//
//    private class OrSpecification extends AbstractSpecification {
//        private Specifiable spec1;
//        private Specifiable spec2;
//
//        public OrSpecification(final Specifiable spec1, final Specifiable spec2) {
//            this.spec1 = spec1;
//            this.spec2 = spec2;
//        }
//
//        @Override
//        public Criterion toCriterion() {
//            return Restrictions.or(this.spec1.toCriterion(), this.spec2.toCriterion());
//        }
//    }
//
//    private class NotSpecification extends AbstractSpecification {
//        private Specifiable spec1;
//
//        public NotSpecification(final Specifiable spec1) {
//            this.spec1 = spec1;
//        }
//
//        @Override
//        public Criterion toCriterion() {
//            return Restrictions.not(this.spec1.toCriterion());
//        }
//    }
//}
