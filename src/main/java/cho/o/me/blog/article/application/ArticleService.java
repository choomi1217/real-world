package cho.o.me.blog.article.application;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.repository.ArticleRepository;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.tag.application.TagService;
import cho.o.me.blog.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final MemberService memberService;

    public Article articles(String email, ArticleRequest request) {

        Member member = memberService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " is not found"));

        Article article = Article.builder()
                .slug(request.getTitle())
                .title(request.getTitle())
                .description(request.getDescription())
                .body(request.getBody())
                .tagList(tagIds(request.getTagList()))
                .createdAt(String.valueOf(Date.valueOf(LocalDate.now())))
                .author(member.getUsername())
                .build();

        return articleRepository.save(article);
    }

    private List<UUID> tagIds(List<String> tagNames) {
        List<Tag> tagList = tagService.findAllByNameIn(tagNames);
        return tagList.stream().map(tag -> {
            if(tagNames.contains(tag.getName())){
                return tag.getId();
            }else{
                Tag save = tagService.save(new Tag(tag.getName()));
                return save.getId();
            }
        }).collect(Collectors.toList());
    }
}

