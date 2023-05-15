package cho.o.me.blog.tag.application;

import cho.o.me.blog.tag.domain.Tag;
import cho.o.me.blog.tag.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> saveAll(List<Tag> tags){
        return tagRepository.saveAll(tags);
    }


    public List<Tag> findAllByNameIn(List<String> names) {
        return tagRepository.findAllByNameIn(names);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> findAllById(List<UUID> ids){
        return tagRepository.findAllByIdIn(ids);
    }
}
