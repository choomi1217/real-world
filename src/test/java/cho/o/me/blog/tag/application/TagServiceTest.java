package cho.o.me.blog.tag.application;

import cho.o.me.blog.tag.domain.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceTest {

    @Autowired
    TagService tagService;

    @DisplayName("태그를 여러개 저장 할 수 있다.")
    @Test
    public void saveAll(){
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        List<Tag> tagList = List.of(tag1, tag2);
        List<Tag> saveAll = tagService.saveAll(tagList);

        assertEquals(tagList.size(), saveAll.size());

        for (int i = 0; i < tagList.size(); i++) {
            assertEquals(tagList.get(i).getName(), saveAll.get(i).getName());
        }

    }

    @DisplayName("태그 이름들로 태그를 찾을 수 있다.")
    @Test
    public void findAllByNameIn(){
        List<String> tagnames = List.of("tag1","tag2","tag3");
        List<Tag> tagList = tagnames.stream().map(Tag::new).toList();
        List<Tag> byNameIn = tagService.findAllByNameIn(tagnames);
        byNameIn.forEach(tag -> {
            System.out.println(tag.getId());
            System.out.println(tag.getName());
        });
    }

}