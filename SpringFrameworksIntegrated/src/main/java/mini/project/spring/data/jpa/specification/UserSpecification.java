package mini.project.spring.data.jpa.specification;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import mini.project.spring.data.jpa.entity.User;
import mini.project.spring.domain.exception.InvalidOperationException;
import mini.project.spring.domain.viewobject.SearchCriteria;

public class UserSpecification implements Specification<User> {

	private static final long serialVersionUID = 1043801004494912096L;

	private SearchCriteria criteria;

	public UserSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		switch (criteria.getOperation()) {
			case ">" :
				return builder.greaterThan(root.get(criteria.getKey()),
						criteria.getValue());
			case ">=" :
				return builder.greaterThanOrEqualTo(root.get(criteria.getKey()),
						criteria.getValue());
			case "<" :
				return builder.lessThan(root.get(criteria.getKey()),
						criteria.getValue());
			case "<=" :
				return builder.lessThanOrEqualTo(root.get(criteria.getKey()),
						criteria.getValue());
			case ":" :
				String pattern = String.join("",
						Arrays.asList("%", criteria.getValue(), "%"));
				if (root.get(criteria.getKey()).getJavaType() == String.class) {
					return builder.like(root.<String>get(criteria.getKey()),
							pattern);
				} else {
					return builder.equal(root.get(criteria.getKey()),
							criteria.getValue());
				}
			default :
				throw new InvalidOperationException("Invalid operation");
		}
	}

}
