package cho.o.me.blog.tag.application;

import cho.o.me.blog.tag.domain.Tag;
import cho.o.me.blog.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    public List<Tag> saveAll(List<Tag> tags){
        return tagRepository.saveAll(tags);
    }

}
