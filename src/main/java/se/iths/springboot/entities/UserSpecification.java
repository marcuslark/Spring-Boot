package se.iths.springboot.entities;

import se.iths.springboot.dtos.UserDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;

public class UserSpecification implements Specification <UserDto> {
    private SearchCriteria searchCriteria;

    //see https://www.baeldung.com/rest-api-search-language-spring-data-specifications
    public UserSpecification(SearchCriteria searchCriteria){
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<UserDto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
            if(root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(root.<String>get(searchCriteria.getKey()),
                        "%" + searchCriteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        }
        return null;
    }

}