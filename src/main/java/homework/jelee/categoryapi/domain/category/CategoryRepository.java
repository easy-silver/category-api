package homework.jelee.categoryapi.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 최상위 카테고리 조회
     */
    List<Category> findAllByParentIsNull();

    /**
     * 해당 카테고리의 하위 카테고리 조회
     */
    List<Category> findAllByParent(Category parent);

}
