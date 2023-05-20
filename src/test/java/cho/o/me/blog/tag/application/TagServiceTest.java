package cho.o.me.blog.tag.application;

import cho.o.me.blog.tag.domain.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(print = MockMvcPrint.SYSTEM_OUT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TagServiceTest {

    @Autowired
    TagService tagService;

    @DisplayName("태그를 여러개 저장 할 수 있다.")
    @Test
    public void saveAll(){
        //given
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        List<Tag> tagList = List.of(tag1, tag2);
        //when
        List<Tag> saveAll = tagService.saveAll(tagList);
        //then
        assertEquals(tagList.size(), saveAll.size());
        for (int i = 0; i < tagList.size(); i++) {
            assertEquals(tagList.get(i).getName(), saveAll.get(i).getName());
        }

    }

    @DisplayName("태그 이름들로 태그를 찾을 수 있다.")
    @Test
    public void findAllByNameIn(){
        //given
        List<String> tagnames = List.of("tag1","tag2","tag3");
        List<Tag> tagList = tagnames.stream().map(Tag::new).toList();
        tagService.saveAll(tagList);
        //when
        List<Tag> byNameIn = tagService.findAllByNameIn(tagnames);
        //then
        byNameIn.forEach(tag -> {
            System.out.println("Tag ID : " + tag.getId() + " | Tag Name : " + tag.getName());
        });
    }

}