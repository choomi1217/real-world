package cho.o.me.blog.article.application;

import cho.o.me.blog.article.domain.Article;
import cho.o.me.blog.article.repository.ArticleRepository;
import cho.o.me.blog.article.ui.request.ArticleRequest;
import cho.o.me.blog.article.ui.response.ArticleResponse;
import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.domain.Member;
import cho.o.me.blog.tag.application.TagService;
import cho.o.me.blog.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final MemberService memberService;

    public ArticleResponse create(String email, ArticleRequest request) {

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

        return articleResponse(articleRepository.save(article));
    }

    private ArticleResponse articleResponse(Article article) {
        return new ArticleResponse(
                article.getTitle(),
                article.getSlug(),
                article.getDescription(),
                article.getBody(),
                tagService.findAllById(article.getTagList())
        );
    }

    private List<UUID> tagIds(List<String> tagNames) {
        List<Tag> tagList = tagService.findAllByNameIn(tagNames);

        if(tagNames.size() == tagList.size()){
            return tagList.stream().map(Tag::getId).toList();
        }else{
            return newTagIds(tagNames, tagList);
        }

    }

    private List<UUID> newTagIds(List<String> tagNames, List<Tag> tagList) {
        List<UUID> tagListUUIDs = tagList.stream().map(Tag::getId).collect(Collectors.toList());
        Set<String> tagListNames = tagList.stream().map(Tag::getName).collect(Collectors.toSet());
        List<Tag> newTags = new ArrayList<>();

        tagNames.forEach(tagName -> {
            if(!tagListNames.contains(tagName)){
                Tag tag = new Tag(tagName);
                newTags.add(tag);
            }
        });

        tagService.saveAll(newTags).forEach(tag -> {
            tagListUUIDs.add(tag.getId());
        });

        return tagListUUIDs;
    }
}