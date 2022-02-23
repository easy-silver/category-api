package homework.jelee.categoryapi.domain.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired CategoryRepository repository;

    @Test
    @DisplayName("최상위 카테고리 등록")
    void save_root_category() {
        //given
        Category category = Category.builder()
                .name("상의")
                .build();

        //when
        Category savedCategory = repository.save(category);

        //then
        assertThat(savedCategory).isSameAs(category);
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo(category.getName());
        assertThat(savedCategory.getParent()).isNull();
    }

    @Test
    @DisplayName("하위 카테고리 등록")
    void save_sub_category() {
        //given
        Category parentCategory = repository.save(Category.builder()
                .name("상의")
                .build());

        //when
        Category childCategory = repository.save(Category.builder()
                .name("반팔 티셔츠")
                .parent(parentCategory)
                .build());

        //then
        assertThat(childCategory.getParent()).isNotNull();
        assertThat(childCategory.getParent().getId()).isEqualTo(parentCategory.getId());
    }

    @Test
    @DisplayName("아이디로 조회")
    void findById() {
        //given
        Category savedCategory = repository.save(Category.builder()
                .name("상의")
                .build());

        //when
        Category findCategory = repository.findById(savedCategory.getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

        //then
        assertThat(findCategory.getName()).isEqualTo(savedCategory.getName());
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() {
        //given
        Category savedCategory = repository.save(Category.builder()
                .name("상의")
                .build());

        //when
        savedCategory.changeName("하의");

        //then
        Category findCategory = repository.findById(savedCategory.getId()).get();
        assertThat(findCategory.getName()).isEqualTo("하의");
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() {
        //given
        Category savedCategory = repository.save(Category.builder()
                .name("상의")
                .build());

        assertThat(repository.count()).isEqualTo(1);

        //when
        repository.delete(savedCategory);

        //then
        assertThat(repository.count()).isEqualTo(0);
    }

}