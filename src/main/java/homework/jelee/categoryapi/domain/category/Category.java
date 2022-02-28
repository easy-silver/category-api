package homework.jelee.categoryapi.domain.category;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false, length = 100)
    private String name;

    //상위 카테고리
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Category parent;

    //하위 카테고리 리스트
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @Builder
    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    /**
     * 연관관계 편의 메서드
     *
     * 상위 카테고리 <-> 하위 카테고리 양방향 연관관계이므로
     * 현재 카테고리에 상위 클래스를 지정할 때 상위 클래스에도 현재 객체를 하위 카테고리 리스트에 추가한다.
     * (순수 객체 상태 고려)
     */
    public void changeParent(Category parent) {
        if (this.parent != null) {
            //기존에 설정된 상위 카테고리가 있다면 해당 카테고리의 하위 카테고리 리스트에서 현재 객체를 제거한다.
            this.parent.removeChild(this);
        }
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Category child) {
        this.children.add(child);
    }

    public void removeChild(Category child) {
        this.children.remove(child);
    }

    public void changeName(String name) {
        this.name = name;
    }
}
