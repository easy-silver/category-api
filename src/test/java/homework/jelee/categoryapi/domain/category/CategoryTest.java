package homework.jelee.categoryapi.domain.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 카테고리 연관관계 설정에 대한 순수 Java 단위 테스트
 */
class CategoryTest {

    @Test
    @DisplayName("상위 카테고리 지정 시 상위 카테고리에도 해당 카테고리가 하위 카테고리 리스트에 포함되어있는지 확인한다.")
    void setParent() {
        //given
        Category category = Category.builder()
                .name("티셔츠")
                .build();

        Category parent = category.getParent();
        assertThat(parent).isNull();

        Category newCategory = Category.builder()
                .name("상의")
                .build();
        //when
        category.changeParent(newCategory);

        //then
        assertThat(category.getParent()).isSameAs(newCategory);
        assertThat(newCategory.getChildren().contains(category)).isTrue();
    }

    @Test
    @DisplayName("상위 카테고리 변경 시 기존 상위 카테고리의 하위 카테고리 리스트에서 현재 카테고리를 제거하고, " +
            "새로운 상위 카테고리의 하위 카테고리 리스트에 현재 카테고리를 추가한다.")
    void changeParent() {
        //given
        Category oldParent = Category.builder()
                .name("바지")
                .build();

        Category childCategory = Category.builder()
                .name("슬랙스")
                .parent(oldParent)
                .build();

        assertThat(childCategory.getParent()).isSameAs(oldParent);
        assertThat(oldParent.getChildren().contains(childCategory)).isTrue();

        //when
        Category newParent = Category.builder()
                .name("하의")
                .build();

        childCategory.changeParent(newParent);

        //then
        assertThat(childCategory.getParent()).isSameAs(newParent);
        assertThat(newParent.getChildren().contains(childCategory)).isTrue();
        assertThat(oldParent.getChildren().contains(childCategory)).isFalse();
    }

}